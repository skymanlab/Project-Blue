package com.skyman.billiarddata.etc.statistics;

import android.util.Log;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    public static final String DATE_PATTERN = "yyyy년 MM월 dd일";
    private static final Display CLASS_LOG_SWITCH = Display.ON;
    private static final String CLASS_NAME = "Date";

    private int year;
    private int month;
    private int dayOfMonth;


    public Date() {
        year = 0;
        month = 0;
        dayOfMonth = 0;
    }

    private Date(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setDateByParsing(String parsedDate) {
        LocalDate localDate = LocalDate.parse(parsedDate, DateTimeFormatter.ofPattern(DATE_PATTERN));

        year = localDate.getYear();
        month = localDate.getMonthValue();
        dayOfMonth = localDate.getDayOfMonth();

    }

    public Date parse(String parsedDate) {
        LocalDate localDate = LocalDate.parse(parsedDate, DateTimeFormatter.ofPattern(DATE_PATTERN));
        return new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    @NonNull
    @Override
    public String toString() {

        if (year > 0 && month > 0 && dayOfMonth > 0) {
            // Date, Calendar -> LocalDate
            LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
            // localDate
            return localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        } else
            return year + "년 " + month +"월 " +dayOfMonth +"일";
    }

    public void printLog() {
        if (DeveloperManager.PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON) {
                Log.d(CLASS_NAME, "[Date 내용 확인]");
                Log.d(CLASS_NAME, "year : " + year);
                Log.d(CLASS_NAME, "month : " + month);
                Log.d(CLASS_NAME, "dayOfMonth : " + dayOfMonth);
            }
    }

}
