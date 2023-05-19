package com.skyman.billiarddata.etc.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Time {

    private int minute;

    public Time() {

    }

    public Time(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setMinuteTo(int hour, int minute) {
        this.minute = (hour * 60) + minute;
    }

    public void plus(int minute) {
        this.minute += minute;
    }

    public Time countInMinutes(int hour, int minute) {
        return new Time((hour * 60) + minute);
    }

    public static Time totalTime(List<Time> timeList) {
        Time totalTime = new Time();
        timeList.forEach(
                time -> {
                    totalTime.plus(time.getMinute());
                }
        );
        return totalTime;
    }

    public static Time averageMinute(List<Time> timeList) {
        int totalMinute = 0;

        for (int index = 0; index < timeList.size(); index++) {
            totalMinute += timeList.get(index).getMinute();
        }
        return new Time(totalMinute / timeList.size());
    }

    public static Time maxTime(List<Time> timeList) {
        return Collections.max(timeList, ((time, t1) -> {
            if (time.getMinute() > t1.getMinute())
                return 1;
            else if (time.getMinute() < t1.getMinute())
                return -1;
            else
                return 0;
        }));
    }

    public static Time minTime(List<Time> timeList) {
        return Collections.min(timeList, new Comparator<Time>() {
            @Override
            public int compare(Time time, Time t1) {
                if (time.getMinute() > t1.getMinute())
                    return 1;
                else if (time.getMinute() < t1.getMinute())
                    return -1;
                else
                    return 0;
            }
        });
    }

    public String toString() {
        StringBuilder time = new StringBuilder();

        if ((this.minute / 60) > 0) {
            time.append(this.minute / 60);
            time.append("시 ");
        }
        time.append(this.minute % 60);
        time.append("분");

        return time.toString();
    }

    public static String toString(int minute) {

        StringBuilder time = new StringBuilder();

        if ((minute / 60) > 0) {
            time.append(minute / 60);
            time.append("시 ");
        }
        time.append(minute % 60);
        time.append("분");

        return time.toString();
    }
}
