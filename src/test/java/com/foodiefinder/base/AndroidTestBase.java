package com.foodiefinder.base;

import com.foodiefinder.AdbCommand;
import com.foodiefinder.AppiumServerHandler;
import com.foodiefinder.listeners.AutomationListener;
import com.foodiefinder.utils.AndroidAction;
import com.foodiefinder.utils.WaitUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.foodiefinder.config.ConfigUtils.*;
import static com.foodiefinder.DriverFactory.*;
import static org.openqa.selenium.net.PortProber.findFreePort;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

@Listeners(AutomationListener.class)
public abstract class AndroidTestBase extends AppiumServerHandler {

    public final AdbCommand getAdbCommand() {
        return new AdbCommand();
    }

    public final AndroidAction getActions() {
        return new AndroidAction();
    }

    public SoftAssert softAssert;
    static URL appiumUrl;

    @BeforeSuite
    public void updateServerAndroidSetting() {
        setSuitePlatformConfig(getPlatform().toString());
    }

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

    @BeforeMethod(alwaysRun = true)
    public void startDriver() {
        initDriver();
        WaitUtils.threadSleep(1000);
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void killDriver() {
        try {
            AdbCommand.clearAppCache();
            getDriver().removeApp(BUNDLE_ID_ANDROID);
        } catch (Exception ignored) {
            //ignored
        }
        closeDriverObjects();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownTest() {
        if (!IS_APPIUM_REMOTE_DRIVER) {
            stopAppiumLocalServer();
        }
    }

    public Platform getPlatform() {
        return Platform.ANDROID;
    }

    public DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:systemPort", findFreePort());
        return capabilities;
    }

    protected void initDriver() {
        initAndroidDriver(appiumUrl, getCapabilities());
        getAndroidDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}