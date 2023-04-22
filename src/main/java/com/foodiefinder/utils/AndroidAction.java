package com.foodiefinder.utils;

import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import java.time.Duration;

import static com.foodiefinder.DriverFactory.getAndroidDriver;
import static com.foodiefinder.DriverFactory.getDriver;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class AndroidAction {

    public void enableAndroidWiFi() {
        getAndroidDriver().setConnection(
                new ConnectionStateBuilder(getAndroidDriver().getConnection())
                        .withWiFiEnabled()
                        .withDataEnabled()
                        .build());
    }

    public void disableAndroidWiFi() {
        getAndroidDriver().setConnection(
                new ConnectionStateBuilder(getAndroidDriver().getConnection())
                        .withWiFiDisabled()
                        .withDataDisabled()
                        .build());
    }

    /**
     * Check if wifi on/off
     *
     * @return True if wifi on otherwise False.
     */
    public boolean isWiFiEnabled() {
        return getAndroidDriver().getConnection().isWiFiEnabled();
    }


    public void pressAndroidBack() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.BACK));
    }


    public void sendKeys(String text) {
        getAndroidDriver().getKeyboard().sendKeys(text);
    }


    public void pressAndroidHome() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.HOME));
    }

    /**
     * Press android device switch button
     */
    public void pressAndroidAppSwitch() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
        getAndroidDriver().currentActivity();
    }

    public String getCurrentActivity() {
        return getAndroidDriver().currentActivity();
    }

    /**
     * Lock android device
     *
     * @param duration
     */
    public void lockAndroidDevice(int duration) {
        getAndroidDriver().lockDevice(Duration.ofSeconds(duration));
    }

    /**
     * Unlock android device
     */
    public void unLockAndroidDevice() {
        getAndroidDriver().unlockDevice();
    }

    /**
     * hide device keyboard on android and ios
     * on appium version 1.12.1 and below does not work on ios, but in the future it will work on ios
     */
    public void hideKeyboard() {
        getDriver().hideKeyboard();
    }
}
