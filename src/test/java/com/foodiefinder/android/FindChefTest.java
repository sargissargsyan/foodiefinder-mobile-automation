package com.foodiefinder.android;

import com.foodiefinder.base.AndroidTestBase;
import com.foodiefinder.screens.FindChefScreen;
import com.foodiefinder.screens.HomeScreen;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class FindChefTest extends AndroidTestBase {

    @Test
    public void chefSearch() {
        HomeScreen homeScreen = new HomeScreen();
        FindChefScreen findChefScreen = homeScreen.clickFindAChefButton();
        findChefScreen.typeInSearchName("Arshotit");
        findChefScreen.clickSearchButton();

        assertFalse(findChefScreen.isChefArshotitVisible(), "Chef Arshotit should be in the list!" );
    }
    @Test
    public void emptySearch() {
        HomeScreen homeScreen = new HomeScreen();
        FindChefScreen findChefScreen = homeScreen.clickFindAChefButton();
        findChefScreen.clickSearchButton();

        assertTrue(findChefScreen.isChefAnthonyVisible(), "Chef Anthony Thomas should be in the list!" );
        assertTrue(findChefScreen.isChefArshotitVisible(), "Chef Arshotit should be in the list!" );
    }
}