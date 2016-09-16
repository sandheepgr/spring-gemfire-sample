package com.microideation.app.gemfiretest.config;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionShortcut;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventQueue;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventQueueFactory;
import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.listeners.MyAsyncEventListener;
import com.microideation.app.gemfiretest.loaders.CustomerRewardBalanceLoader;
import com.microideation.app.gemfiretest.writers.CustomerRewardBalanceWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import java.util.Properties;

/**
 * Created by sandheepgr on 30/6/16.
 */
@Configuration
@ComponentScan(basePackages = {"com.microideation.app.gemfiretest.loaders", "com.microideation.app.gemfiretest.writers","com.microideation.app.gemfiretest.listeners"})
public class GemfireConfig {

    @Value("${gemfiretest.servername}")
    private String serverName;

    @Value("${gemfiretest.bindaddress}")
    private String bindAddress;

    @Value("${gemfiretest.locators}")
    private String locators;

    @Value("${gemfiretest.mcastport}")
    private String mcastPort;

    @Bean
    public Properties gemfireProperties() {
        Properties gemfireProperties = new Properties();
        gemfireProperties.setProperty("name", serverName);
        gemfireProperties.setProperty("mcast-port", mcastPort);
        gemfireProperties.setProperty("log-level", "config");
        gemfireProperties.setProperty("conflate-events","true");
        gemfireProperties.setProperty("bind-address",bindAddress);
        gemfireProperties.setProperty("locators",locators);
        return gemfireProperties;
    }


    @Bean
    public CacheFactoryBean gemfireCache() {

        CacheFactoryBean gemfireCache = new CacheFactoryBean();
        gemfireCache.setClose(true);
        gemfireCache.setProperties(gemfireProperties());
        return gemfireCache;
    }

    @Bean
    public AsyncEventQueue gemfireQueue(CacheFactoryBean gemfireCache ,MyAsyncEventListener myAsyncEventListener)  throws Exception {

        AsyncEventQueueFactory factory = gemfireCache.getObject().createAsyncEventQueueFactory();
        factory.setPersistent(false);
        factory.setParallel(false);
        factory.setBatchConflationEnabled(true);
        factory.setBatchSize(100);
        AsyncEventQueue asyncQueue = factory.create("sampleQueue", myAsyncEventListener);
        return asyncQueue;

    }

    @Bean
    public Region<Long,CustomerRewardBalance> myRegion(ReplicatedRegionFactoryBean<Long, CustomerRewardBalance> crbRegion) throws Exception {
        
        return crbRegion.getObject();

    }

    @Bean
    public ReplicatedRegionFactoryBean<Long, CustomerRewardBalance> crbRegion(GemFireCache cache,CustomerRewardBalanceLoader customerRewardBalanceLoader, CustomerRewardBalanceWriter customerRewardBalanceWriter,AsyncEventQueue gemfireQueue) {
        ReplicatedRegionFactoryBean<Long, CustomerRewardBalance> crbRegion = new ReplicatedRegionFactoryBean<Long,CustomerRewardBalance>();
        crbRegion.setShortcut(RegionShortcut.REPLICATE);
        crbRegion.setCache(cache);
        crbRegion.setClose(false);
        crbRegion.setName("crb");
        crbRegion.setAsyncEventQueues(new AsyncEventQueue[]{gemfireQueue});
        crbRegion.setCacheLoader(customerRewardBalanceLoader);
        /*crbRegion.setCacheWriter(customerRewardBalanceWriter);*/
        crbRegion.setPersistent(false);
        return crbRegion;

    }

    @Bean
    public GemfireCacheManager cacheManager(Cache gemfireCache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
        cacheManager.setCache(gemfireCache);
        return cacheManager;
    }
}
