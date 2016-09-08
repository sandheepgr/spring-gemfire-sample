package com.microideation.app.gemfiretest.service;

import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;

/**
 * Created by sandheepgr on 30/6/16.
 */
public interface TestService {

    public CustomerRewardBalance saveRewardBalance(CustomerRewardBalance customerRewardBalance);
    public CustomerRewardBalance findCustomerRewardBalance(Long rwdId);

}
