package com.letv.mas.caller.iptv.tvproxy.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IpAddrUtil {

    private static final Logger log = LoggerFactory.getLogger(IpAddrUtil.class);
    private static SkipEntity head;
    private final static int per = 100; // 跳表每N个抽一个
    private static volatile String localIP = null;

//    static {
//        initIPLibrary();
//        localIP = getIPAddr();
//    }

    /**
     * 获得本机IP地址
     * @return
     */
    public static String getIPAddr() {
        String ret = null;
        try {
            if (null == localIP) {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface nif = netInterfaces.nextElement();
                    Enumeration<InetAddress> iparray = nif.getInetAddresses();
                    while (iparray.hasMoreElements()) {

                        String tempIP = iparray.nextElement().getHostAddress();
                        if (isIP(tempIP)) {
                            localIP = tempIP;
                        }
                    }
                }
            }
            ret = localIP;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ret;
    }

    /**
     * 校验是否合法IP
     * @param addr
     * @return
     */
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr) || "127.0.0.1".equalsIgnoreCase(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        return addr.matches(rexp);
    }

    /**
     * 从Request请求中取出用户的ip地址
     * @param request
     * @return
     */
    public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据IP查询属性信息
     * 根据IP段做跳表查找
     * @param ip
     * @return
     */
    public static String getIPInfo(String ip) {
        String ipInfo = null;

        initIPLibrary();

        if (head != null && isIP(ip)) {
            long target = ipToLong(ip);
            SkipEntity newHead = head;
            SkipEntity next = null;
            SkipEntity down = null;
            while (newHead != null) {
                if (newHead.getKey() == target) {
                    return newHead.getValue();
                } else if (newHead.getKey() < target) {
                    next = newHead.getNext();
                    if (next != null) {
                        if (next.getKey() == target) {
                            return next.getValue();
                        } else if (next.getKey() < target) {
                            newHead = next;
                        } else {
                            down = newHead.getDown();
                            if (down == null) {
                                return newHead.getValue();
                            } else {
                                newHead = down;
                            }
                        }
                    } else {
                        down = newHead.getDown();
                        if (down == null) {
                            return newHead.getValue();
                        } else {
                            newHead = down;
                        }
                    }
                } else {
                    return newHead.getValue();
                }
            }
        }

        return ipInfo;
    }

    /**
     * 获得ip所在城市级别
     * @param ip
     * @return
     */
    public static Integer getCityLevel(String ip) {
        Integer cityLevel = 0;
        String ipInfo = getIPInfo(ip);
        if (StringUtils.isNotBlank(ipInfo)) {
            String[] infoArray = ipInfo.split("_");
            if (infoArray != null && infoArray.length >= 4) {
                try {
                    cityLevel = Integer.parseInt(infoArray[3]);
                } catch (Exception e) {
                    cityLevel = 0;
                }
            }
        }
        return cityLevel;
    }

    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    private static void initIPLibrary() {
        BufferedReader brReader = null;

        if (null != head) {
            return;
        }

        try {
            String dir = System.getProperty("conf.dir");
            if (!dir.endsWith("/")) {
                dir += "/";
            }
            brReader = new BufferedReader(new FileReader(new File(dir + "IPLibrary.txt")));
            String line = null;
            String[] strs = null;
            long ipLong;
            StringBuilder ipBuilder;
            List<SkipEntity> ipList = new LinkedList<SkipEntity>();
            SkipEntity tmp = null;
            SkipEntity last = null;
            while ((line = brReader.readLine()) != null) {
                strs = line.split(",");
                if (strs.length < 7 || !isIP(strs[0])) {
                    continue;
                }
                ipLong = ipToLong(strs[0]);
                ipBuilder = new StringBuilder();
                tmp = new SkipEntity();
                tmp.setKey(ipLong);
                tmp.setValue(ipBuilder.append(strs[2]).append("_").append(strs[3]).append("_").append(strs[4])
                        .append("_").append(strs[5]).toString());
                if (last != null) {
                    last.setNext(tmp);
                    tmp.setLast(last);
                }
                last = tmp;

                ipList.add(tmp);
            }
            Collections.sort(ipList);
            head = createSkipTable(ipList);
        } catch (FileNotFoundException e) {
            log.error("init ip Library file not found", e);
        } catch (IOException e2) {
            log.error("init ip Library error", e2);
        } finally {
            if (brReader != null) {
                try {
                    brReader.close();
                } catch (IOException e) {
                    log.error("init ip Library error", e);
                }
            }
        }
    }

    public static boolean matchIP(List ips, String ip) {
        if (ips.contains(ip)) {
            return true;
        }

        for (int i = 0; i < ips.size(); i++) {
            List lip = Arrays.asList(ips.get(i).toString().split("\\."));
            String re = "^";
            for (int j = 0; j < lip.size(); j++) {
                String num = lip.get(j).toString();
                if (!"*".equals(num)) {
                    re += num + ".";
                } else {
                    re += "\\d{0,3}.";
                }
                if (j == lip.size()) {
                    re = re.substring(0, re.length() - 1).toString() + "\\$";
                }
            }

            Pattern pattern = Pattern.compile(re);
            Matcher matcher = pattern.matcher(ip);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public static void printSkipTable(SkipEntity head) {
        initIPLibrary();
        if (head == null) {
            System.out.println("empty table");
            return;
        }

        System.out.print("level" + head.getLevel() + ": " + head.getKey());
        System.out.println();
        SkipEntity newHead = head.getDown();
        while (newHead != null) {
            System.out.print("level" + newHead.getLevel() + ": ");
            SkipEntity tmp = newHead;
            while (tmp.getNext() != null) {
                System.out.print(tmp.getKey() + " ");
                tmp = tmp.getNext();
            }
            System.out.println();
            newHead = newHead.getDown();
        }

    }

    public static SkipEntity createSkipTable(List<SkipEntity> targetList) {
        if (targetList != null && targetList.size() > 0) {
            int index = 0;
            List<SkipEntity> newList = new LinkedList<SkipEntity>(); // 每层表的临时数据
            List<SkipEntity> oldList = targetList; // 上层表数据
            int size = oldList.size();
            SkipEntity newEntity = null;
            SkipEntity oldEntity = null;
            while (true) {
                if ((index * per) < size) {
                    try {
                        oldEntity = oldList.get((index * per));
                        newEntity = (SkipEntity) oldEntity.clone();
                        newEntity.setLevel(oldEntity.getLevel() + 1);
                        newEntity.setDown(oldEntity);
                        oldEntity.setUp(newEntity);
                        if (newList.size() > 0) {
                            newList.get(newList.size() - 1).setNext(newEntity);
                            newEntity.setLast(newList.get(newList.size() - 1));
                        }
                        newList.add(newEntity);
                        index++;
                    } catch (CloneNotSupportedException e) {
                        log.error("create skip table clone error", e);
                        continue;
                    }
                } else {
                    index = 0;
                    size = newList.size();
                    oldList = newList;
                    newList = new LinkedList<SkipEntity>();
                }

                if (oldList.size() == 1) {
                    return oldList.get(0);
                }

            }
        }
        return null;
    }

    /**
     * 跳表的基础结构类
     * 由于IP查找的特殊性，需要进行双向链表
     */
    static class SkipEntity implements Cloneable, Comparable<SkipEntity> {
        private long key;
        private String value;
        private SkipEntity next; // 下一个元素
        private SkipEntity down; // 下一层的元素
        private SkipEntity last; // 上一个元素
        private SkipEntity up; // 上一层元素
        private int level;

        public SkipEntity(long key, String value) {
            this.key = key;
            this.value = value;
        }

        public SkipEntity() {
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            SkipEntity clone = new SkipEntity();
            clone.key = this.key;
            clone.value = this.value;
            return clone;
        }

        @Override
        public int compareTo(SkipEntity o) {
            if (o != null) {
                return (int) (this.key - o.getKey());
            }

            return 0;
        }

        public SkipEntity getLast() {
            return last;
        }

        public void setLast(SkipEntity last) {
            this.last = last;
        }

        public SkipEntity getUp() {
            return up;
        }

        public void setUp(SkipEntity up) {
            this.up = up;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public long getKey() {
            return key;
        }

        public void setKey(long key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public SkipEntity getNext() {
            return next;
        }

        public void setNext(SkipEntity next) {
            this.next = next;
        }

        public SkipEntity getDown() {
            return down;
        }

        public void setDown(SkipEntity down) {
            this.down = down;
        }
    }
}
