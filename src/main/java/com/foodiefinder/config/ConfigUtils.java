package com.foodiefinder.config;
import com.foodiefinder.enums.PlatformType;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class ConfigUtils {
    private static final Logger LOGGER = Logger.getLogger(ConfigUtils.class);
    private static String os;

    public static String getSuitePlatformConfig() {
        return os;
    }

    public static void setSuitePlatformConfig(String os) {
        ConfigUtils.os = os;
    }

    public static final int DRIVER_RETRY_COUNT;
    public static final String APPIUM_URL;
    public static final boolean IS_APPIUM_REMOTE_DRIVER;
    public static final boolean IS_SIMULATOR;
    public static final String APP_PATH;
    public static final String BUNDLE_ID_ANDROID;
    public static final String MAIN_ACTIVITY;
    public static final int RETRY_COUNT;
    public static PlatformType PLATFORM_TYPE;
    private static Properties configs;
    public static String apkName = "foodiefinder.apk";

    /**
     * Read property file only once
     */
    static {
        configs = readFromFile("/config.properties");
        DRIVER_RETRY_COUNT = Integer.parseInt(getProperty("driver.retry.count"));
        IS_APPIUM_REMOTE_DRIVER = parseBoolean(getProperty("appium.remote.driver"));
        IS_SIMULATOR = parseBoolean(getProperty("appium.simulator"));
        APPIUM_URL = getProperty("appium.url");
        APP_PATH = getProperty("app.path");
        RETRY_COUNT = parseInt(getProperty("test.retry.count"));
        BUNDLE_ID_ANDROID = getProperty("bundle.id.android");
        MAIN_ACTIVITY = getProperty("app.main.activity");

    }

    private ConfigUtils() {
    }

    /**
     * Trying to get property from the System by key
     * If in System there is no, getting from am.acba.digital.config.property file
     *
     * @param key of property
     * @return value of the property
     */
    public static String getProperty(String key) {
        if (System.getProperty(key) == null || System.getProperty(key).isEmpty()) {
            String property = configs.getProperty(key);
            LOGGER.info("Getting property " + key + ": " + property);
            return property;
        } else {
            String property = System.getProperty(key);
            LOGGER.info("Getting property " + key + ": " + property);
            return property;
        }
    }

    public static boolean isValueExistingInPropertyList(String value) {
        return configs.containsValue(value);
    }

    static Properties readFromFile(String path) {
        Properties properties = new Properties();
        try {
            properties.load(ConfigUtils.class.getResourceAsStream(path));
        } catch (IOException e) {

        }
        return properties;
    }

}
