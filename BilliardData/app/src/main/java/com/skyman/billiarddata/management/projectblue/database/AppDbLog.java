package com.skyman.billiarddata.management.projectblue.database;

import android.util.Log;

import com.skyman.billiarddata.developer.Display;

public class AppDbLog {

    private static final Display POWER = Display.ON;

    public static void printFormatErrorLog(String className, String objectName) {

        if (POWER.equals(Display.ON)) {
            Log.d(className, objectName + " 객체는 형식에 맞지 않습니다.");
        }
    }

    public static void printDatabaseErrorLog(String className, String tableName) {
        if (POWER.equals(Display.ON)) {
            Log.d(className, "");
        }
    }
}
