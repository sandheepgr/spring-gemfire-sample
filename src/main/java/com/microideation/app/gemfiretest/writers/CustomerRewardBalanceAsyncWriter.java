package com.microideation.app.gemfiretest.writers;

import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.repository.CustomerRewardBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 16/9/16.
 */
@Component
public class CustomerRewardBalanceAsyncWriter implements AsyncEventWriter<CustomerRewardBalance> {

    @Autowired
    private CustomerRewardBalanceRepository customerRewardBalanceRepository;

    // Create the writer
    private static final Logger log = LoggerFactory.getLogger(CustomerRewardBalanceAsyncWriter.class);

    @Override
    public CustomerRewardBalance update(CustomerRewardBalance obj) {

        // Get the existing reward balance
        CustomerRewardBalance existingBalance = customerRewardBalanceRepository.findByRewardCurrencyId(obj.getRewardCurrencyId());

        // Check if the balance is existing
        if ( existingBalance == null ) {

            // Set the balance to obj
            existingBalance = obj;

            // Log the information
            log.info("inserting the object " + existingBalance);


        } else {

            // Set the balance in the field
            existingBalance.setRewardBalance(obj.getRewardBalance());

            // Set the object
            log.info("updating the object " + existingBalance);

        }

        // Save the object
        customerRewardBalanceRepository.save(existingBalance);

        // Return the object
        return existingBalance;

    }

    @Override
    public CustomerRewardBalance delete(CustomerRewardBalance obj) {

        // Log the information
        log.info("Delete the customer reward balance : " + obj);

        // Delete the object
        customerRewardBalanceRepository.delete(obj);

        // Return the customerRewardBalance object
        return obj;

    }
}
