package com.foodiefinder;

import com.foodiefinder.config.ConfigUtils;
import com.foodiefinder.enums.PlatformType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.Semaphore;

import static com.foodiefinder.AppiumDriverType.IOS;
import static io.appium.java_client.events.EventFiringWebDriverFactory.getEventFiringWebDriver;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class DriverFactory {
    private static final Semaphore SEMAPHORE = new Semaphore(2);

    private static PlatformType PLATFORM_TYPE;
    private static Logger log = Logger.getLogger(DriverFactory.class);


    public static void closeDriverObjects() {
        try {
            if (getDriver() != null) {
                getDriver().close();
                driverFactoryThread.remove();
            }
            if (driverFactoryThread.get() != null) {
                driverFactoryThread.remove();
            }
        } catch (Exception ignore) {
            //ignore
        }
    }

    private static final ThreadLocal<AppiumDriver<MobileElement>> driverFactoryThread = new ThreadLocal<>();

    public static void initAndroidDriver(URL appiumUrl, DesiredCapabilities capabilities) {
        PLATFORM_TYPE = PlatformType.ANDROID;
        AppiumDriverType driverType = AppiumDriverType.ANDROID;
        setDriverFactoryThread(appiumUrl, capabilities, driverType);
    }


    public static void initIOSDriver(URL appiumUrl, DesiredCapabilities capabilities) {
        PLATFORM_TYPE = PlatformType.IOS;
        AppiumDriverType driverType = IOS;
        setDriverFactoryThread(appiumUrl, capabilities, driverType);
    }

    @SneakyThrows
    private static void setDriverFactoryThread(URL appiumUrl, DesiredCapabilities capabilities, AppiumDriverType driverType) {
        int retry = ConfigUtils.DRIVER_RETRY_COUNT;
        do {
            try {
                driverFactoryThread.set(getEventFiringWebDriver(driverType.createSetupObject(appiumUrl, capabilities).createDriver()));
            } catch (RuntimeException exception) {
                exception.printStackTrace();
                log.info("Trying to Driver " + retry + " time(s)");
                Thread.sleep(5000);
                retry -= 1;
            }

        } while (driverFactoryThread.get() == null && retry > 0);
    }

    private DriverFactory() {
    }

    public static AppiumDriver<MobileElement> getDriver() {
        return driverFactoryThread.get();

    }

    public static IOSDriver<MobileElement> getIOSDriver() {
        if (getDriver() == null)
            throw new RuntimeException("Ios Driver is null please check your test suit lifecycle");
        return (IOSDriver<MobileElement>) getDriver();
    }

    public static AndroidDriver<MobileElement> getAndroidDriver() {
        if (getDriver() == null)
            throw new RuntimeException("Android Driver is null please check your test suit lifecycle");
        return (AndroidDriver<MobileElement>) getDriver();
    }


    public static void launchApp() {
        getDriver().runAppInBackground(Duration.ofMillis(-1));
        getDriver().launchApp();
    }

    public static void resetApp() {
        getDriver().runAppInBackground(Duration.ofMillis(-1));
        getDriver().resetApp();
    }

    public static void closeApp() {
        getDriver().runAppInBackground(Duration.ofMillis(-1));
        getDriver().closeApp();
    }

    public static void terminateApp() {
        DriverFactory.getDriver().runAppInBackground(Duration.ofMillis(-1));
        DriverFactory.getDriver().terminateApp(ConfigUtils.BUNDLE_ID_ANDROID);
    }

    public static void removeApp() {
        DriverFactory.getDriver().runAppInBackground(Duration.ofMillis(-1));
        DriverFactory.getDriver().removeApp(ConfigUtils.BUNDLE_ID_ANDROID);

    }
}