package com.foodiefinder;

import com.google.common.collect.ImmutableMap;
import com.foodiefinder.config.ConfigUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.collections.Lists;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foodiefinder.DriverFactory.getDriver;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class AdbCommand {
    private static final Logger LOGGER = Logger.getLogger(AdbCommand.class);

    private enum Permission {
        READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA, READ_CONTACTS, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
    }

    /**
     * Execute Android adb shell command.
     *
     * @param commandMap Adb command and arguments map
     * @return Null if command is wrong.
     */
    private static String executeCommand(Map<String, Object> commandMap) {
        try {
            LOGGER.info("Executing Command : " + commandMap.toString());
            return getDriver().executeScript("mobile: shell", commandMap).toString();
        } catch (WebDriverException e) {
            LOGGER.info("Command is not Executed : " + commandMap);
        }
        return null;
    }


    public void removeApp(String appPackage) {
        Map<String, Object> args = new HashMap<>();
        args.put("command", "pm");
        args.put("args", Lists.newArrayList("uninstall", "-k", appPackage));
        executeCommand(args);
    }


    public <T> void sendKeyEvent(T event) {
        List<String> sendKeyEvent = Arrays.asList(
                "keyevent ", event.toString()
        );
        Map<String, Object> sendKeyEventCmd = ImmutableMap.of(
                "command", "input",
                "args", sendKeyEvent
        );

        executeCommand(sendKeyEventCmd);
    }

    public void typeMockPasscode(boolean withConfirmation) {
        int j = 6;
        if (withConfirmation) {
            j=12;
        }
        for (int i = 0; i <= j; i++) {
            sendKeyEvent(66);
        }
    }
    public void tapOnBack() {
        sendKeyEvent(4);
    }


    public void sendText(String text) {
        List<String> sendText = Arrays.asList(
                "text ", text
        );
        Map<String, Object> sendTextCommand = ImmutableMap.of(
                "command", "input",
                "args", sendText
        );

        executeCommand(sendTextCommand);
    }


    public static void takeScreenshot(String name) {
        Map<String, Object> args = new HashMap<>();
        args.put("command", "screencap");
        args.put("args", Lists.newArrayList("-p", "/sdcard/Pictures/" + name + ".png"));
        executeCommand(args);
    }


    public void forceStopApp() {
        Map<String, Object> args = new HashMap<>();
        args.put("command", "am");
        args.put("args", Lists.newArrayList("force-stop", ConfigUtils.BUNDLE_ID_ANDROID));
        executeCommand(args);

    }

    public static void grantPermission(String packageName, Permission permissionType) {
        Map<String, Object> permission = new HashMap<>();
        permission.put("command", "pm");
        permission.put("args", Lists.newArrayList("grant", packageName, "android.permission." + permissionType));
        executeCommand(permission);
    }

    public static void grantAllPermissions(String packageName) {
        Arrays.asList(Permission.values()).forEach(permission -> grantPermission(packageName, permission));
    }

    public static void grantAllPermissions() {
        grantAllPermissions(ConfigUtils.BUNDLE_ID_ANDROID);
    }

    public static void revokePermission(String packageName, Permission permissionType) {
        Map<String, Object> permission = new HashMap<>();
        permission.put("command", "pm");
        permission.put("args", Lists.newArrayList("revoke", packageName, "android.permission." + permissionType));
        executeCommand(permission);
    }

    public static void revokeStoragePermission() {
        revokePermission(ConfigUtils.BUNDLE_ID_ANDROID, Permission.READ_EXTERNAL_STORAGE);
        revokePermission(ConfigUtils.BUNDLE_ID_ANDROID, Permission.WRITE_EXTERNAL_STORAGE);
    }

    public static void forceRemoveApp(String packageId) {
        try {
            LOGGER.info("Removing app: " + packageId);
            Runtime.getRuntime().exec("adb -s " + DriverUtils.getDeviceId() + " uninstall " + packageId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void clearAppCache() {
        Map<String, Object> clearCache = new HashMap<>();
        clearCache.put("command", "pm");
        clearCache.put("args", Lists.newArrayList("clear", ConfigUtils.BUNDLE_ID_ANDROID));
        executeCommand(clearCache);
    }

}


