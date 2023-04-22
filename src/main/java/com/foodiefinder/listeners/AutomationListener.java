package com.foodiefinder.listeners;

import com.foodiefinder.DriverUtils;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.testng.*;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

@Log4j
public class AutomationListener implements ITestListener, IInvokedMethodListener, IHookable {


    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("----------------------------------------------------------------------");
        log.info("Starting to run " + iTestResult.getMethod().getQualifiedName() + " test method");
        log.info("----------------------------------------------------------------------");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("----------------------------------------------------------------------");
        log.info("PASSED " + iTestResult.getMethod().getQualifiedName() + " test method");
        log.info("----------------------------------------------------------------------");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info("---->Checking if Internet connected: " + isInternetConnected());
        log.info("----------------------------------------------------------------------");
        log.info("FAILED " + iTestResult.getMethod().getQualifiedName() + " test method");
        log.info("----------------------------------------------------------------------");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.info("---->Checking if Internet connected: " + isInternetConnected());
        log.info("----------------------------------------------------------------------");
        log.info("SKIPPED " + iTestResult.getMethod().getQualifiedName() + " test method");
        log.info("----------------------------------------------------------------------");
    }

    @SneakyThrows
    @Override
    @Attachment
    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        iHookCallBack.runTestMethod(iTestResult);
        if (iTestResult.getThrowable() != null) {
            DriverUtils.takeScreenShot(iTestResult.getMethod().getQualifiedName());
        }
    }

    public static boolean isInternetConnected() {
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
