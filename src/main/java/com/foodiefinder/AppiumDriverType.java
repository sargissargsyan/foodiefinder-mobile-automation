package com.foodiefinder;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobileOptions;
import lombok.SneakyThrows;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import static com.foodiefinder.config.ConfigUtils.*;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public enum AppiumDriverType implements DriverSetup {

    ANDROID {
        @SneakyThrows
        public AppiumDriverType createSetupObject(URL appiumServerURL, DesiredCapabilities desiredCapabilities) {
            app = new File(APP_PATH, apkName);
            capabilities = desiredCapabilities;

            capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
            capabilities.setCapability("appium:fullReset", false);
            capabilities.setCapability("appium:noReset", true);
            capabilities.setCapability("appium:newCommandTimeout", 220);
            capabilities.setCapability("appium:automationName", AutomationEngine.UiAutomator2);
            capabilities.setCapability("appium:appPackage", BUNDLE_ID_ANDROID);
            capabilities.setCapability("appium:appActivity", MAIN_ACTIVITY);
            capabilities.setCapability("appium:platformName", Platform.ANDROID);
            capabilities.setCapability("appium:autoGrantPermissions", true);

            serverLocation = appiumServerURL;

            return this;
        }

        @Override
        public AppiumDriver createDriver() {
            return new AndroidDriver<MobileElement>(serverLocation, capabilities);
        }
    },
    IOS {
        @SneakyThrows
        public AppiumDriverType createSetupObject(URL appiumServerURL, DesiredCapabilities desiredCapabilities) {
            capabilities = desiredCapabilities;
            return this;
        }

        @Override
        public AppiumDriver createDriver() {
            return new IOSDriver<MobileElement>(serverLocation, capabilities);
        }
    };

    DesiredCapabilities capabilities;
    URL serverLocation;
    File app;
    MobileOptions<?> mobileOptions;


}

enum AutomationEngine {UiAutomator2, XCUITest}

