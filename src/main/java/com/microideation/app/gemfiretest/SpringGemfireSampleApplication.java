package com.microideation.app.gemfiretest;

import com.microideation.app.gemfiretest.domain.CustomerRewardBalance;
import com.microideation.app.gemfiretest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ThreadPoolExecutor;


@EnableCaching
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@SpringBootApplication
public class SpringGemfireSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGemfireSampleApplication.class, args);
	}


    @Bean(name="workExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setQueueCapacity(10000);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}


@org.springframework.web.bind.annotation.RestController
class RestController {


    @Autowired
    private TestService testService;

    @RequestMapping(path = "/add/{rwdId}/{rwdBalance}" , method = RequestMethod.GET)
    public CustomerRewardBalance addBalance(@PathVariable(value = "rwdId") Long rwdId,
                           @PathVariable(value = "rwdBalance") Double balance) {

        CustomerRewardBalance customerRewardBalance = new CustomerRewardBalance();
        customerRewardBalance.setRewardBalance(balance);
        customerRewardBalance.setRewardCurrencyId(rwdId);

        testService.saveRewardBalance(customerRewardBalance);

        return customerRewardBalance;

    }


    @RequestMapping(path = "/get/{rwdId}" , method = RequestMethod.GET)
    @ResponseBody
    public CustomerRewardBalance addBalance(@PathVariable(value = "rwdId") Long rwdId) {

        return testService.findCustomerRewardBalance(rwdId);

    }

}