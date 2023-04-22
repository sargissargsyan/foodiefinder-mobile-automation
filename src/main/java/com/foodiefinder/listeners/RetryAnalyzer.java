package com.foodiefinder.listeners;

import com.foodiefinder.config.ConfigUtils;
import lombok.extern.log4j.Log4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

@Log4j
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;

    public boolean retry(ITestResult result) {
        if (retryCount < ConfigUtils.RETRY_COUNT && !result.isSuccess()) {
            log.info("Retrying test " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            retryCount++;
            return true;
        }
        return false;
    }

    public String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1)
            resultName = "SUCCESS";
        if (status == 2)
            resultName = "FAILURE";
        if (status == 3)
            resultName = "SKIP";
        return resultName;
    }

}
