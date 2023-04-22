package com.foodiefinder.utils;

import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.util.List;

import static com.foodiefinder.DriverFactory.getDriver;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class WaitUtils {

    private static final int TIMEOUT = 30;
    private static final int LONG_TIMEOUT = 220;
    private static final int SHORT_TIMEOUT = 5;
    private static final Logger LOGGER = Logger.getLogger(WaitUtils.class);
    private static WebDriverWait wait;

    private WaitUtils() {
    }

    private static WebDriverWait getInstance(int timeout) {
        return (WebDriverWait) new WebDriverWait(getDriver(), timeout, 50).ignoring(StaleElementReferenceException.class);
    }

    public static WaitUtils getWaitUtils() {
        wait = getInstance(TIMEOUT);
        return new WaitUtils();
    }

    public static WaitUtils getLongWaitUtils() {
        wait = getInstance(LONG_TIMEOUT);
        return new WaitUtils();
    }

    public static WaitUtils getShortWaitUtils() {
        wait = getInstance(SHORT_TIMEOUT);
        return new WaitUtils();
    }

    public static void threadSleep(int sleepTime) {
        if (sleepTime > 5000) throw new IllegalArgumentException();
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ignored) {
            //ignored
        }
    }

    public MobileElement waitUntilElementAppear(MobileElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException | TimeoutException e) {
            throw new WebDriverException("The Element: " + element + " is not Appear");
        }
        return element;
    }

    /**
     * Wait until element attribute is changed to the expected value
     *
     * @param element
     * @param attribute
     * @param expectedValue
     */
    public void waitUntilElementAttributeIsChangedTo(MobileElement element, String attribute, String expectedValue) {
        try {
            wait.until(ExpectedConditions.attributeToBe(element, attribute, expectedValue));
        } catch (WebDriverException e) {
            LOGGER.error(e.getMessage());
            throw new WebDriverException("Expected attribute value is missing");
        }
    }

    /**
     * Checks whether the element attribute is changed to the expected value
     *
     * @param element
     * @param attribute
     * @param expectedValue
     */
    public boolean isElementAttributeChangedTo(MobileElement element, String attribute, String expectedValue) {
        try {
            wait.until(ExpectedConditions.attributeToBe(element, attribute, expectedValue));
            return true;
        } catch (WebDriverException e) {
            LOGGER.info(e.getMessage());
        }
        return false;
    }

    public MobileElement waitElementToBeClickable(MobileElement element) {
        return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilAppToBeReady() {
        new WebDriverWait(getDriver(), 1).until(new ExpectedCondition<Boolean>() {
            @Override
            public @Nullable Boolean apply(@Nullable WebDriver webDriver) {
                try {
                    return getDriver().getPageSource() != null;
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    /**
     * if element is not present this method will not throw timeoutException
     *
     * @param element
     */
    public void waitUntilElementDisappear(MobileElement element) {
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public @Nullable
            Boolean apply(@Nullable WebDriver webDriver) {
                try {
                    return !element.isDisplayed();
                } catch (Exception e) {
                    return true;
                }
            }
        });
    }

    /**
     * if index of mobileElements is not present this method will not throw timeoutException
     *
     * @param mobileElements
     */
    public void waitUntilElementDisappear(List<MobileElement> mobileElements, int index) {
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public @Nullable Boolean apply(@Nullable WebDriver webDriver) {
                try {
                    return !mobileElements.get(index).isDisplayed();
                } catch (Exception e) {
                    return true;
                }
            }
        });
    }

    public void waitUntilElementVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (WebDriverException webDriverException) {
            throw new TimeoutException("Element is not visible");

        }
    }

    public void waitForTextToAppear(String textToAppear, MobileElement element) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
    }

    public void waitForTextToAppear(String textToAppear, List<MobileElement> elements, int index) {
        wait.until(ExpectedConditions.textToBePresentInElement(elements.get(index), textToAppear));
    }
}
