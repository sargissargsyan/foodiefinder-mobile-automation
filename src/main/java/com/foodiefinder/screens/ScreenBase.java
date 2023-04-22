package com.foodiefinder.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;


import static com.foodiefinder.DriverFactory.getDriver;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

@Log4j
public abstract class ScreenBase<T extends LoadableComponent<T>> extends LoadableComponent<T> {

    protected final String BUNDLE_ID = "com.app.blackfoodiefinder";
    protected AppiumDriver driver;
    protected WebDriverWait webDriverWait;
    protected TouchAction touchAction;
    public ScreenBase() {
        this.driver = getDriver();
        this.webDriverWait = new WebDriverWait(driver, 5, 250);
        this.touchAction = new TouchAction(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    protected void init() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected void tapOn(MobileElement mobileElement) {
        try {
            if (mobileElement.isEnabled()) {
                mobileElement.click();
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Unable to locate the Element using: " + mobileElement.toString());
        }
    }

    protected void setValue(MobileElement mobileElement, String setValue) {
        try {
            mobileElement.sendKeys(setValue);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Unable to locate the Element using: " + mobileElement.toString());
        }
    }

    protected void openScreen(String deepLink) {
        driver.get(deepLink);
    }

    public abstract String getDeepLink();

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isEnabled(MobileElement mobileElement){
        return mobileElement.isEnabled();
    }


    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {

    }

    public boolean isElementPresent(MobileElement mobileElement) {
        try {
            mobileElement.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public boolean isElementVisible(MobileElement mobileElement) {
        boolean isElementDisplayed;
        try {
            isElementDisplayed = mobileElement.isDisplayed();
        } catch (NoSuchElementException exception) {
            isElementDisplayed = false;
        }
        return isElementDisplayed;
    }

    public boolean waitToElementIsVisible(By by) {
        this.webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return false;
    }
    public String getText(MobileElement mobileElement) {
        return mobileElement.getText();
    }
    public void sendText(MobileElement mobileElement, String text){
        mobileElement.sendKeys(text);
    }
    public void sendText(MobileElement mobileElement, int text){
        mobileElement.sendKeys(Integer.toString(text));
    }
    public void click(MobileElement mobileElement) {
        log.info("Clicking on Element " + mobileElement.getText());
        mobileElement.click();
    }

    public MobileElement find(By location) {
        return getDriver().findElement(location);
    }
    public MobileElement find(MobileBy location) {
        return getDriver().findElement(location);
    }
    public MobileElement find(String name) {
        return getDriver().findElementByName(name);
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
}
