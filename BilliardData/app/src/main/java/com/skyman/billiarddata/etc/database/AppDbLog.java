package com.skyman.billiarddata.etc.database;

import android.util.Log;

import com.skyman.billiarddata.developer.LogSwitch;

public class AppDbLog {

    private static final LogSwitch LOG_SWITCH = LogSwitch.OFF;

    public static void printFormatErrorLog(String className, String objectName) {
        if (LOG_SWITCH.equals(LogSwitch.ON)) {
            Log.d(className, objectName + " 객체는 형식에 맞지 않습니다.");
        }
    }

    public static void printDatabaseErrorLog(String className, String tableName) {
        if (LOG_SWITCH.equals(LogSwitch.ON)) {
            Log.d(className, "");
        }
    }
}
