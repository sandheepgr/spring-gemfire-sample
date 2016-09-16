package com.microideation.app.gemfiretest.service.impl;

import com.gemstone.gemfire.cache.Region;
import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.repository.CustomerRewardBalanceRepository;
import com.microideation.app.gemfiretest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by sandheepgr on 30/6/16.
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    @Qualifier("myRegion")
    Region<Long, CustomerRewardBalance> crbRegion;

    @Autowired
    private CustomerRewardBalanceRepository customerRewardBalanceRepository;


    @CachePut(cacheNames = "crb", key = "#result.rewardCurrencyId")
    public CustomerRewardBalance saveRewardBalance(CustomerRewardBalance customerRewardBalance) {

     /*   CustomerRewardBalance existingBalance = customerRewardBalanceRepository.findByRewardCurrencyId(customerRewardBalance.getRewardCurrencyId());

        if ( existingBalance == null ) {

            log.info("inserting the object " + customerRewardBalance);


        } else {

            existingBalance.setRewardBalance(customerRewardBalance.getRewardBalance());
            customerRewardBalance = existingBalance;
            log.info("updating the object " + customerRewardBalance);

        }

        customerRewardBalanceRepository.save(customerRewardBalance);*/
        /*crbRegion.put(customerRewardBalance.getRewardCurrencyId(),customerRewardBalance);*/
        //customerRewardBalanceRepository.save(customerRewardBalance);

        return customerRewardBalance;

    }

    @Cacheable("crb")
    public CustomerRewardBalance findCustomerRewardBalance(Long rewardCurrencyId) {

        return customerRewardBalanceRepository.findByRewardCurrencyId(rewardCurrencyId);

    }
}
