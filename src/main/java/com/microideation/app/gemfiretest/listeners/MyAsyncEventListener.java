package com.microideation.app.gemfiretest.listeners;

import com.gemstone.gemfire.cache.Operation;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.writers.CustomerRewardBalanceAsyncWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sandheepgr on 1/7/16.
 */
@Component
public class MyAsyncEventListener implements AsyncEventListener {

    // Log the information
    private static final Logger log = LoggerFactory.getLogger(MyAsyncEventListener.class);

    @Autowired
    private CustomerRewardBalanceAsyncWriter customerRewardBalanceAsyncWriter;

    @Override
    public boolean processEvents(List<AsyncEvent> asyncEvents) {

        // Iterate through the async events
        for( AsyncEvent asyncEvent: asyncEvents) {

            // Get the key
            Long key = (Long) asyncEvent.getKey();

            // Get the region
            Region region = asyncEvent.getRegion();

            // Get the object
            CustomerRewardBalance customerRewardBalance = (CustomerRewardBalance) asyncEvent.getDeserializedValue();

            // Check the operation
            if ( asyncEvent.getOperation().equals(Operation.CREATE) ||
                 asyncEvent.getOperation().equals(Operation.UPDATE)   ) {

                // call the method
                customerRewardBalanceAsyncWriter.update(customerRewardBalance);

            } else if (asyncEvent.getOperation().equals(Operation.DESTROY)) {

                // call the method
                customerRewardBalanceAsyncWriter.delete(customerRewardBalance);

            }

        }

        // Return true
        return true;

    }

    @Override
    public void close() {

    }
}
