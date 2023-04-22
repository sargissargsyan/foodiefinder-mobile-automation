package com.foodiefinder;

import com.foodiefinder.enums.PlatformType;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import io.qameta.allure.*;

import static com.foodiefinder.DriverFactory.getDriver;
import static com.foodiefinder.config.ConfigUtils.PLATFORM_TYPE;
import static com.foodiefinder.config.ConfigUtils.getSuitePlatformConfig;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */
@Log4j
public class DriverUtils {

    public static String getDeviceId() {
        return getDriver().getCapabilities().getCapability("udid").toString();
    }

    public static String getPlatformVersion() {
        try {
            return getDriver().getCapabilities().getCapability("platformVersion").toString();
        } catch (Exception e) {
            return "inaccessible";
        }
    }

    @Attachment(value = "Failure in method {0}", type = "image/png")
    public static byte[] takeScreenShot(String methodName) {
        log.info("Taking screenshot on failure");
        return getDriver().getScreenshotAs(OutputType.BYTES);
    }

    public static String getDeviceManufacturer() {
        if (isAndroidPlatform())
            return getDriver().getCapabilities().getCapability("deviceManufacturer").toString();
        else
            return "IOS";
    }

    public static String getDeviceScreenSize() {
        if (isAndroidPlatform())
            return getDriver().getCapabilities().getCapability("deviceScreenSize").toString();
        else
            return "inaccessible";
    }

    public static boolean isAndroidPlatform() {
        return getSuitePlatformConfig().equalsIgnoreCase(Platform.ANDROID.toString());
    }

    public static boolean isIOSPlatform() {
        return getSuitePlatformConfig().equalsIgnoreCase(Platform.IOS.toString());
    }


    public static PlatformType getPlatformType() {
        return PLATFORM_TYPE;
    }
}
