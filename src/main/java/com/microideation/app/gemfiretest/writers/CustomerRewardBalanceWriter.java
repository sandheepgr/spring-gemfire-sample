package com.microideation.app.gemfiretest.writers;

import com.gemstone.gemfire.cache.CacheWriter;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.RegionEvent;
import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.repository.CustomerRewardBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 30/6/16.
 */
@Component
public class CustomerRewardBalanceWriter implements CacheWriter<Long,CustomerRewardBalance> {

    @Autowired
    private CustomerRewardBalanceRepository customerRewardBalanceRepository;

    private static final Logger log = LoggerFactory.getLogger(CustomerRewardBalanceWriter.class);

    @Override
    @Async("workExecutor")
    public void beforeUpdate(EntryEvent<Long, CustomerRewardBalance> longCustomerRewardBalanceEntryEvent) throws CacheWriterException {

        update(longCustomerRewardBalanceEntryEvent.getNewValue());


    }

    @Override
    @Async("workExecutor")
    public void beforeCreate(EntryEvent<Long, CustomerRewardBalance> longCustomerRewardBalanceEntryEvent) throws CacheWriterException {


        //if ( customerRewardBalanceRepository.findByRewardCurrencyId(longCustomerRewardBalanceEntryEvent.getKey()) == null ) {

            update(longCustomerRewardBalanceEntryEvent.getNewValue());

        //}

    }

    @Override
    public void beforeDestroy(EntryEvent<Long, CustomerRewardBalance> longCustomerRewardBalanceEntryEvent) throws CacheWriterException {

    }

    @Override
    public void beforeRegionDestroy(RegionEvent<Long, CustomerRewardBalance> longCustomerRewardBalanceRegionEvent) throws CacheWriterException {

    }

    @Override
    public void beforeRegionClear(RegionEvent<Long, CustomerRewardBalance> longCustomerRewardBalanceRegionEvent) throws CacheWriterException {

    }

    @Override
    public void close() {

    }


    private void update(CustomerRewardBalance customerRewardBalance) {

        CustomerRewardBalance existingBalance = customerRewardBalanceRepository.findByRewardCurrencyId(customerRewardBalance.getRewardCurrencyId());

        if ( existingBalance == null ) {

            log.info("inserting the object " + customerRewardBalance);


        } else {

            existingBalance.setRewardBalance(customerRewardBalance.getRewardBalance());
            customerRewardBalance = existingBalance;
            log.info("updating the object " + customerRewardBalance);

        }

        customerRewardBalanceRepository.save(customerRewardBalance);

    }



}
