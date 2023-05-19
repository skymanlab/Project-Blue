package com.skyman.billiarddata.etc.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "Date";

    public static final String DATE_PATTERN = "yyyy년 MM월 dd일";

    private int year;
    private int month;
    private int dayOfMonth;

    public Date() {
        year = 0;
        month = 0;
        dayOfMonth = 0;
    }

    public Date(int year, int month, int dayOfMonth) {
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

    public static Date createByParsing(String parsedDate) {
        LocalDate localDate = LocalDate.parse(parsedDate, DateTimeFormatter.ofPattern(DATE_PATTERN));
        return new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());

    }

    public void setYearByParsing(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));

        this.year = localDate.getYear();
    }

    public void setMonthByParsing(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));

        this.month = localDate.getMonthValue();
    }

    public void setDateByParsing(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));

        this.year = localDate.getYear();
        this.month = localDate.getMonthValue();
        this.dayOfMonth = localDate.getDayOfMonth();

    }
    public boolean equals(String date) {
        if (toString().equals(date))
            return true;
        else
            return false;
    }

    public boolean equalYear(int year) {
        if (this.year == year)
            return true;
        else
            return false;
    }


    public boolean equalMonth(int month) {
        if (this.month == month)
            return true;
        else
            return false;
    }

    public boolean equalDayOfMonth(int dayOfMonth) {
        if (this.dayOfMonth == dayOfMonth)
            return true;
        else
            return false;
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
            return year + "년 " + month + "월 " + dayOfMonth + "일";
    }

    public void printLog() {
        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                Log.d(CLASS_NAME, "[Date 내용 확인]");
                Log.d(CLASS_NAME, "year : " + year);
                Log.d(CLASS_NAME, "month : " + month);
                Log.d(CLASS_NAME, "dayOfMonth : " + dayOfMonth);
            }
    }

}
