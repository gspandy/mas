package com.letv.mas.caller.iptv.tvproxy.search.config;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.AddressProvider;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.pool.ConnectionPool;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.proxy.ThriftServiceClientProxyFactory;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.zookeeper.CuratorClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchThriftConfig {

    @Bean
    public SearchThriftBeanConfig getSearchThriftBeanConfig(){
        SearchThriftBeanConfig config = new SearchThriftBeanConfig();
        return config;
    }


    @Bean
    @Qualifier("searchZookeeper")
    public CuratorFramework getCuratorClientFactory(SearchThriftBeanConfig config){
        CuratorClientFactory factory = new CuratorClientFactory();
        factory.setZookeeperHosts(config.getZkServer());
        try {
            return factory.getObject();
        }catch (Exception e){

        }
        return null;
    }

    @Bean
    @Qualifier("searchAddressProvider")
    public AddressProvider getAddressProvider(CuratorFramework curatorFramework, SearchThriftBeanConfig config){
        try {
            return new AddressProvider(config.getServer(),curatorFramework,config.getZkPath());
        }catch (Exception e){

        }
        return null;
    }

    @Bean
    @Qualifier("searchConnectionPool")
    public ConnectionPool getConnectionPool(AddressProvider addressProvider){
        try {
            return  new ConnectionPool(addressProvider,"TTSocket",40,40,0,1000);
        }catch (Exception e){

        }
        return null;
    }

    @Bean
    @Qualifier("searchServing")
    public ThriftServiceClientProxyFactory getThriftServiceClient(AddressProvider addressProvider, ConnectionPool connectionPool){
        ThriftServiceClientProxyFactory factory = new ThriftServiceClientProxyFactory();
        factory.setAddressProvider(addressProvider);
        factory.setConnectionPool(connectionPool);
        factory.setService("serving.GenericServing");
        factory.setProtocol(1);
        return factory;
    }
}
