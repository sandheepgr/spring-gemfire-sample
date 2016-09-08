package com.microideation.app.gemfiretest.domain;


import javax.persistence.*;

/**
 * Created by sandheepgr on 15/6/16.
 */
@Entity
@Table( name = "CUSTOMER_REWARD_BALANCE")
public class CustomerRewardBalance extends BaseEntity {

    @Column(name = "REWARD_CURRENCY_ID", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rewardCurrencyId;

    @Column(name = "REWARD_BALANCE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double rewardBalance;

    public Long getRewardCurrencyId() {
        return rewardCurrencyId;
    }

    public void setRewardCurrencyId(Long rewardCurrencyId) {
        this.rewardCurrencyId = rewardCurrencyId;
    }

    public Double getRewardBalance() {
        return rewardBalance;
    }

    public void setRewardBalance(Double rewardBalance) {
        this.rewardBalance = rewardBalance;
    }


    @Override
    public String toString() {
        return "CustomerRewardBalance{" +
                "rewardCurrencyId=" + rewardCurrencyId +
                ", rewardBalance=" + rewardBalance +
                '}';
    }
}
