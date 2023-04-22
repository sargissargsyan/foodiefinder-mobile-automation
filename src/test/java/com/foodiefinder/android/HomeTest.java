package com.foodiefinder.android;

import com.foodiefinder.base.AndroidTestBase;
import com.foodiefinder.screens.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class HomeTest extends AndroidTestBase {
    @Test
    public void homeButtonsVisibility() {
        HomeScreen homeScreen = new HomeScreen();
        assertTrue(homeScreen.isFindFoodNearMeButtonVisible(), "Find Food Near me option was not Displayed!");
        assertTrue(homeScreen.isFindAChefButtonVisible(), "Find A Chef option was not Displayed!");
        assertTrue(homeScreen.isRecipesButtonVisible(), "Recipes option was not Displayed!");
        assertTrue(homeScreen.isBuyBlackButtonVisible(), "Buy Black option was not Displayed!");
        assertTrue(homeScreen.isFoodieForumButtonVisible(), "Foodie Forum option was not Displayed!");
        assertTrue(homeScreen.isMoreButtonVisible(), "More option was not Displayed!");
    }
}