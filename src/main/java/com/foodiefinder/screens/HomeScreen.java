package com.foodiefinder.screens;

import com.foodiefinder.utils.WaitUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class HomeScreen extends ScreenBase {

    @AndroidFindBy(xpath = "//*[@class = 'android.view.View' and contains(@content-desc,'Find Food Near Me')]")
    @iOSXCUITFindBy
    private MobileElement findFoodNearMeButton;

    @AndroidFindBy(xpath = "//*[@class = 'android.view.View' and contains(@content-desc,'Find A Chef')]")
    @iOSXCUITFindBy
    private MobileElement findAChefButton;

    @AndroidFindBy(xpath = "//*[@class = 'android.view.View' and contains(@content-desc,'Recipes')]")
    @iOSXCUITFindBy
    private MobileElement recipesButton;

    @AndroidFindBy(xpath = "//*[@class = 'android.view.View' and contains(@content-desc,'Buy Black')]")
    @iOSXCUITFindBy
    private MobileElement buyBlackButton;

    @AndroidFindBy(xpath = "//*[@class = 'android.view.View' and contains(@content-desc,'Foodie Forum')]")
    @iOSXCUITFindBy
    private MobileElement foodieForumButton;

    @AndroidFindBy(xpath = "//*[@class = 'android.view.View' and contains(@content-desc,'More')]")
    @iOSXCUITFindBy
    private MobileElement moreButton;

    public FindChefScreen clickFindAChefButton() {
        WaitUtils.getWaitUtils().waitUntilElementAppear(findAChefButton).click();
        return new FindChefScreen();
    }



    @Override
    public String getDeepLink() {
        return null;
    }
    public HomeScreen open() {
        openScreen(getDeepLink());
        return new HomeScreen();
    }

    public boolean isFindFoodNearMeButtonVisible(){
        return isElementVisible(findFoodNearMeButton);
    }
    public boolean isFindAChefButtonVisible(){
        return isElementVisible(findAChefButton);
    }

    public boolean isRecipesButtonVisible(){
        return isElementVisible(recipesButton);
    }

    public boolean isBuyBlackButtonVisible(){
        return isElementVisible(buyBlackButton);
    }

    public boolean isFoodieForumButtonVisible(){
        return isElementVisible(foodieForumButton);
    }

    public boolean isMoreButtonVisible(){
        return isElementVisible(moreButton);
    }

}