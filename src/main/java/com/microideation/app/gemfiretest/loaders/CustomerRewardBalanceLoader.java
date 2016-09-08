package com.microideation.app.gemfiretest.loaders;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.LoaderHelper;
import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.repository.CustomerRewardBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 30/6/16.
 */
@Component
public class CustomerRewardBalanceLoader implements CacheLoader<Long,CustomerRewardBalance> {

    @Autowired
    CustomerRewardBalanceRepository customerRewardBalanceRepository;

    private static final Logger log = LoggerFactory.getLogger(CustomerRewardBalanceLoader.class);



    @Override
    public CustomerRewardBalance load(LoaderHelper<Long, CustomerRewardBalance> longCustomerRewardBalanceLoaderHelper) throws CacheLoaderException {

        // Get the reward currency id
        Long rewardCurrencyId = longCustomerRewardBalanceLoaderHelper.getKey();

        // Get the data
        log.info("Getting the data for " + rewardCurrencyId + " from the database");

        // Return the reward currency
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceRepository.findByRewardCurrencyId(rewardCurrencyId);

        // Return the object
        return customerRewardBalance;

    }

    @Override
    public void close() {

    }
}
