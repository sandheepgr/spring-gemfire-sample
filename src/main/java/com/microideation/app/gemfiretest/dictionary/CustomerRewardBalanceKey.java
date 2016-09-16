package com.microideation.app.gemfiretest.dictionary;

import java.io.Serializable;

/**
 * Created by sandheepgr on 16/9/16.
 */

public class CustomerRewardBalanceKey  implements Serializable{

    private String rewardCurrencyId;

    public String getRewardCurrencyId() {
        return rewardCurrencyId;
    }

    public void setRewardCurrencyId(String rewardCurrencyId) {
        this.rewardCurrencyId = rewardCurrencyId;
    }


    @Override
    public String toString() {
        return "CustomerRewardBalanceKey{" +
                "rewardCurrencyId='" + rewardCurrencyId + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerRewardBalanceKey that = (CustomerRewardBalanceKey) o;

        if (!rewardCurrencyId.equals(that.rewardCurrencyId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rewardCurrencyId.hashCode();
    }
}
