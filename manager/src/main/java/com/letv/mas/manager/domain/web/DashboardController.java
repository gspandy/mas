package com.letv.mas.manager.domain.web;

import com.netflix.appinfo.AmazonInfo;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Pair;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl;
import com.netflix.eureka.resources.StatusResource;
import com.netflix.eureka.util.StatusInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

@Controller
@RequestMapping("/")
public class DashboardController {

    @Value("/")
    private String dashboardPath = "";


    public DashboardController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String status(HttpServletRequest request, Map<String, Object> model, HttpServletResponse response) {
        populateBase(request, model);
        populateApps(model);
        StatusInfo statusInfo;
        try {
            statusInfo = new StatusResource().getStatusInfo();
        } catch (Exception e) {
            statusInfo = StatusInfo.Builder.newBuilder().isHealthy(false).build();
        }
        model.put("statusInfo", statusInfo);
        populateInstanceInfo(model, statusInfo);
        filterReplicas(model, statusInfo);
        response.setHeader("X-Frame-Options","ALLOW-FROM");
        return "eureka/sstatus";
    }

    @RequestMapping(value = "/lastn", method = RequestMethod.GET)
    public String lastn(HttpServletRequest request, Map<String, Object> model, HttpServletResponse response) {
        populateBase(request, model);
        PeerAwareInstanceRegistryImpl registry = (PeerAwareInstanceRegistryImpl) getRegistry();
        ArrayList<Map<String, Object>> lastNCanceled = new ArrayList<>();
        List<Pair<Long, String>> list = registry.getLastNCanceledInstances();
        for (Pair<Long, String> entry : list) {
            lastNCanceled.add(registeredInstance(entry.second(), entry.first()));
        }
        model.put("lastNCanceled", lastNCanceled);
        list = registry.getLastNRegisteredInstances();
        ArrayList<Map<String, Object>> lastNRegistered = new ArrayList<>();
        for (Pair<Long, String> entry : list) {
            lastNRegistered.add(registeredInstance(entry.second(), entry.first()));
        }
        model.put("lastNRegistered", lastNRegistered);
        response.setHeader("X-Frame-Options","ALLOW-FROM");
        return "eureka/lastn";
    }

    private Map<String, Object> registeredInstance(String id, long date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("date", new Date(date));
        return map;
    }

    protected void populateBase(HttpServletRequest request, Map<String, Object> model) {
        model.put("time", new Date());
        model.put("basePath", "/");
        model.put("dashboardPath", this.dashboardPath.equals("/") ? ""
                : this.dashboardPath);
        populateHeader(model);
        populateNavbar(request, model);
    }

    private void populateHeader(Map<String, Object> model) {
        model.put("currentTime", StatusResource.getCurrentTimeAsString());
        model.put("upTime", StatusInfo.getUpTime());
        model.put("environment", ConfigurationManager.getDeploymentContext()
                .getDeploymentEnvironment());
        model.put("datacenter", ConfigurationManager.getDeploymentContext()
                .getDeploymentDatacenter());
        PeerAwareInstanceRegistry registry = getRegistry();
        model.put("registry", registry);
        model.put("isBelowRenewThresold", registry.isBelowRenewThresold() == 1);
    }

    private PeerAwareInstanceRegistry getRegistry() {
        return getServerContext().getRegistry();
    }

    private EurekaServerContext getServerContext() {
        return EurekaServerContextHolder.getInstance().getServerContext();
    }

    private void populateNavbar(HttpServletRequest request, Map<String, Object> model) {
        Map<String, String> replicas = new LinkedHashMap<>();
        List<PeerEurekaNode> list = getServerContext().getPeerEurekaNodes().getPeerNodesView();
        for (PeerEurekaNode node : list) {
            try {
                URI uri = new URI(node.getServiceUrl());
                String href = scrubBasicAuth(node.getServiceUrl());
                replicas.put(uri.getHost() + ":" + uri.getPort(), href);
            } catch (Exception ex) {
                // ignore?
            }
        }
        model.put("replicas", replicas.entrySet());
    }

    private void populateApps(Map<String, Object> model) {
        List<Application> sortedApplications = getRegistry().getSortedApplications();
        ArrayList<Map<String, Object>> apps = new ArrayList<>();
        for (Application app : sortedApplications) {
            LinkedHashMap<String, Object> appData = new LinkedHashMap<>();
            apps.add(appData);
            appData.put("name", app.getName());
            appData.put("configUrl", "");
            Map<String, Integer> amiCounts = new HashMap<>();
            Map<InstanceInfo.InstanceStatus, List<Pair<String, String>>> instancesByStatus = new HashMap<>();
            Map<String, Integer> zoneCounts = new HashMap<>();
            Map<String, InstanceInfo> map = new HashMap<>();
            for (InstanceInfo info : app.getInstances()) {
                String id = info.getId();
                map.put(id, info);
                String url = info.getStatusPageUrl();
                InstanceInfo.InstanceStatus status = info.getStatus();
                String ami = "n/a";
                String zone = info.getMetadata().get("zone");
                if (zone == null) {
                    zone = "";
                }
                if (info.getDataCenterInfo().getName() == DataCenterInfo.Name.Amazon) {
                    AmazonInfo dcInfo = (AmazonInfo) info.getDataCenterInfo();
                    ami = dcInfo.get(AmazonInfo.MetaDataKey.amiId);
                    zone = dcInfo.get(AmazonInfo.MetaDataKey.availabilityZone);
                }
                Integer count = amiCounts.get(ami);
                if (count != null) {
                    amiCounts.put(ami, count + 1);
                } else {
                    amiCounts.put(ami, 1);
                }
                count = zoneCounts.get(zone);
                if (count != null) {
                    zoneCounts.put(zone, count + 1);
                } else {
                    zoneCounts.put(zone, 1);
                }
                List<Pair<String, String>> list = instancesByStatus.get(status);
                if (list == null) {
                    list = new ArrayList<>();
                    instancesByStatus.put(status, list);
                }
                list.add(new Pair<>(id, url));
            }
            appData.put("amiCounts", amiCounts.entrySet());
            appData.put("zoneCounts", zoneCounts.entrySet());
            ArrayList<Map<String, Object>> instanceInfos = new ArrayList<>();
            appData.put("instanceInfos", instanceInfos);
            for (Iterator<Map.Entry<InstanceInfo.InstanceStatus, List<Pair<String, String>>>> iter = instancesByStatus
                    .entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry<InstanceInfo.InstanceStatus, List<Pair<String, String>>> entry = iter
                        .next();
                List<Pair<String, String>> value = entry.getValue();
                InstanceInfo.InstanceStatus status = entry.getKey();
                LinkedHashMap<String, Object> instanceData = new LinkedHashMap<>();
                instanceInfos.add(instanceData);
                instanceData.put("status", entry.getKey());
                ArrayList<Map<String, Object>> instances = new ArrayList<>();
                instanceData.put("instances", instances);
                instanceData.put("isNotUp", status != InstanceInfo.InstanceStatus.UP);

                // TODO

                /*
                 * if(status != InstanceInfo.InstanceStatus.UP){
                 * buf.append("<font color=red size=+1><b>"); }
                 * buf.append("<b>").append(status
                 * .name()).append("</b> (").append(value.size()).append(") - ");
                 * if(status != InstanceInfo.InstanceStatus.UP){
                 * buf.append("</font></b>"); }
                 */

                for (Pair<String, String> p : value) {
                    LinkedHashMap<String, Object> instance = new LinkedHashMap<>();
                    instances.add(instance);
                    instance.put("id", p.first());
                    InstanceInfo temp = map.get(p.first());
                    if (temp != null) {
                        String zone = temp.getMetadata().get("zone");
                        String index = temp.getMetadata().get("index");
                        String profiles = temp.getMetadata().get("profiles");
                        String weight = temp.getMetadata().get("weight");
                        instance.put("zone", zone);
                        instance.put("weight", weight == null ? "1" : weight);
                        instance.put("index", index == null ? "" : index);
                        instance.put("profiles", profiles == null ? "" : profiles);
                    }
                    String url = p.second();
                    instance.put("url", url);
                    boolean isHref = url != null && url.startsWith("http");
                    instance.put("isHref", isHref);
                    /*
                     * String id = p.first(); String url = p.second(); if(url != null &&
                     * url.startsWith("http")){
                     * buf.append("<a href=\"").append(url).append("\">"); }else { url =
                     * null; } buf.append(id); if(url != null){ buf.append("</a>"); }
                     * buf.append(", ");
                     */
                }
            }
            for (InstanceInfo info : app.getInstances()) {

            }
            // out.println("<td>" + buf.toString() + "</td></tr>");
        }
        model.put("apps", apps);
    }

    private void populateInstanceInfo(Map<String, Object> model, StatusInfo statusInfo) {
        InstanceInfo instanceInfo = statusInfo.getInstanceInfo();
        Map<String, String> instanceMap = new HashMap<>();
        instanceMap.put("ipAddr", instanceInfo.getIPAddr());
        instanceMap.put("status", instanceInfo.getStatus().toString());
        if (instanceInfo.getDataCenterInfo().getName() == DataCenterInfo.Name.Amazon) {
            AmazonInfo info = (AmazonInfo) instanceInfo.getDataCenterInfo();
            instanceMap.put("availability-zone",
                    info.get(AmazonInfo.MetaDataKey.availabilityZone));
            instanceMap.put("public-ipv4", info.get(AmazonInfo.MetaDataKey.publicIpv4));
            instanceMap.put("instance-id", info.get(AmazonInfo.MetaDataKey.instanceId));
            instanceMap.put("public-hostname",
                    info.get(AmazonInfo.MetaDataKey.publicHostname));
            instanceMap.put("ami-id", info.get(AmazonInfo.MetaDataKey.amiId));
            instanceMap.put("instance-type",
                    info.get(AmazonInfo.MetaDataKey.instanceType));
        }
        model.put("instanceInfo", instanceMap);
    }

    protected void filterReplicas(Map<String, Object> model, StatusInfo statusInfo) {
        Map<String, String> applicationStats = statusInfo.getApplicationStats();
        if (applicationStats.get("registered-replicas").contains("@")) {
            applicationStats.put("registered-replicas", scrubBasicAuth(applicationStats.get("registered-replicas")));
        }
        if (applicationStats.get("unavailable-replicas").contains("@")) {
            applicationStats.put("unavailable-replicas", scrubBasicAuth(applicationStats.get("unavailable-replicas")));
        }
        if (applicationStats.get("available-replicas").contains("@")) {
            applicationStats.put("available-replicas", scrubBasicAuth(applicationStats.get("available-replicas")));
        }
        model.put("applicationStats", applicationStats);
    }

    private String scrubBasicAuth(String urlList) {
        String[] urls = urlList.split(",");
        StringBuilder filteredUrls = new StringBuilder();
        for (String u : urls) {
            if (u.contains("@")) {
                filteredUrls.append(u.substring(0, u.indexOf("//") + 2)).append(u.substring(u.indexOf("@") + 1, u.indexOf("/eureka"))).append(",");
            } else {
                filteredUrls.append(u).append(",");
            }
        }
        return filteredUrls.substring(0, filteredUrls.length() - 1);
    }
}