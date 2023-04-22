package com.foodiefinder.screens;

import com.foodiefinder.utils.WaitUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class FindChefScreen extends ScreenBase {

    @AndroidFindBy(xpath = "//*[@class = 'android.widget.EditText' and contains(@hint, 'Name or Keyword')]")
    @iOSXCUITFindBy
    private MobileElement searchNameInputField;

    @AndroidFindBy(xpath = "//*[@class = 'android.widget.EditText' and contains(@hint, 'City or Post Code')]")
    @iOSXCUITFindBy
    private MobileElement cityInputFiled;
    @AndroidFindBy(xpath = "//*[@class = 'android.widget.Button' and contains(@text, 'Search')]")
    @iOSXCUITFindBy
    private MobileElement searchButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Anthony Thomas ']/android.widget.TextView")
    @iOSXCUITFindBy
    private MobileElement chefAnthonyItem;
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Arshotit ']/android.widget.TextView")
    @iOSXCUITFindBy
    private MobileElement chefArshotitItem;

    public void typeInSearchName(String text) {
        WaitUtils.getWaitUtils().waitUntilElementAppear(searchNameInputField).sendKeys(text);
    }

    public void typeInSearchCity(String text) {
        WaitUtils.getWaitUtils().waitUntilElementAppear(cityInputFiled).sendKeys(text);
    }
    public void clickSearchButton() {
        WaitUtils.getWaitUtils().waitUntilElementAppear(searchButton).click();
    }

    public boolean isChefAnthonyVisible(){
        return isElementVisible(chefAnthonyItem);
    }

    public boolean isChefArshotitVisible(){
        return isElementVisible(chefArshotitItem);
    }

    @Override
    public String getDeepLink() {
        return null;
    }
}