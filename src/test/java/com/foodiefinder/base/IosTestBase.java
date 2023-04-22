package com.foodiefinder.base;

import com.foodiefinder.AppiumServerHandler;
import com.foodiefinder.DriverFactory;
import com.foodiefinder.utils.WaitUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.foodiefinder.config.ConfigUtils.*;
import static com.foodiefinder.DriverFactory.*;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public abstract class IosTestBase extends AppiumServerHandler {
    private static final Logger LOGGER = Logger.getLogger(IosTestBase.class);
    static URL appiumUrl;

    public String approvePin;
    protected boolean autoAccept = true;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        if (IS_APPIUM_REMOTE_DRIVER) {
            try {
                appiumUrl = new URL(APPIUM_URL);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            appiumUrl = startAppiumServer();
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void updateServerIOSSetting() {
        setSuitePlatformConfig("ios");
    }

    @BeforeClass(alwaysRun = true)
    public void startDriver() {
        initDriver();
        WaitUtils.threadSleep(1000);
        launchApp();
    }
    @BeforeMethod(alwaysRun = true)
    public void resetAppBeforeMethod() {
        terminateApp();
        launchApp();
    }


    @AfterClass(alwaysRun = true)
    public void killDriver() {
        try {
            removeApp();
            WaitUtils.threadSleep(1000);
            closeDriverObjects();
        } catch (Exception exception) {
            try {
                closeDriverObjects();
            } catch (Exception e) {
                LOGGER.info(exception.getMessage());
            }
        }
    }

    public Platform getPlatform() {
        return Platform.IOS;
    }

    public DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        return capabilities;
    }

    protected void initDriver() {
        DriverFactory.initIOSDriver(appiumUrl, getCapabilities());
        getIOSDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   }

}
