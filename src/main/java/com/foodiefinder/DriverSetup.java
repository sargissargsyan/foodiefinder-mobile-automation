package com.foodiefinder;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public interface DriverSetup {
    DriverSetup createSetupObject(URL appiumServerLocation, DesiredCapabilities capabilities);

    AppiumDriver createDriver();
}
