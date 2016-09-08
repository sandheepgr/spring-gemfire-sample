package com.microideation.app.gemfiretest.repository;

import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sandheepgr on 30/6/16.
 */
public interface CustomerRewardBalanceRepository  extends JpaRepository<CustomerRewardBalance,Long> {

    public CustomerRewardBalance findByRewardCurrencyId(Long rewardCurrencyId);

}
