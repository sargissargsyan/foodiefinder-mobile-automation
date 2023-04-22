package com.foodiefinder;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;
import org.openqa.selenium.net.UrlChecker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class AppiumServerHandler {
    private final static Logger LOGGER = Logger.getLogger(AppiumServerHandler.class);

    private static int appiumPort;
    private static String appiumHost;
    private AppiumDriverLocalService localService;
    private AppiumServiceBuilder serviceBuilder;

    private int getAppiumPort() {
        return appiumPort;
    }

    private String getAppiumHost() {
        return appiumHost;
    }

    protected URL startAppiumServer() {
        URL appiumUrl = startServer();

        appiumHost = appiumUrl.getHost();
        appiumPort = appiumUrl.getPort();

        LOGGER.info("Appium Host is: " + getAppiumHost());

        LOGGER.info("Appium Port is: " + getAppiumPort());


        if (!serverCheckStatusRequest(getAppiumHost(), String.valueOf(getAppiumPort()))) {
            throw new Error("it is not possible to run Appium server with host:" + getAppiumHost()
                    + " and port " + getAppiumPort());
        }
        return appiumUrl;
    }

    protected void stopAppiumLocalServer() {
        localService.stop();
    }

    protected boolean isLocalServerRunning() {
        if (localService != null) {
            return localService.isRunning();
        }
        return false;
    }

    private URL startServer() {
        //Build the Appium service
        serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                .usingAnyFreePort().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        //Start the server with the serviceBuilder
        localService = AppiumDriverLocalService.buildService(serviceBuilder);
        localService.start();
        return localService.getUrl();

    }

    private boolean serverCheckStatusRequest(String host, String port) {
        final String SERVER_URL = String.format("http://%s:%s/wd/hub", host, port);

        URL status = null;
        try {
            status = new URL(SERVER_URL + "/sessions");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            new UrlChecker().waitUntilAvailable(10, TimeUnit.SECONDS, status);
            return true;
        } catch (UrlChecker.TimeoutException e) {
            return false;
        }
    }
}
