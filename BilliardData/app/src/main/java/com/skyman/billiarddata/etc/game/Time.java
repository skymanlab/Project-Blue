package com.skyman.billiarddata.etc.game;

import java.util.ArrayList;
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

    public void plus(int minute) {
        this.minute += minute;
    }

    public void setMinuteTo(int hour, int minute) {
        this.minute = (hour * 60) + minute;
    }

    public int calculateMinute(int hour, int minute) {
        return (hour * 60) + minute;
    }

    public static Time calculateAverageMinute(List<Time> timeArrayList) {
        int totalMinute = 0;

        for (int index = 0; index < timeArrayList.size(); index++) {
            totalMinute += timeArrayList.get(index).getMinute();
        }
        return new Time(totalMinute / timeArrayList.size());
    }

    public static Time maxTime(List<Time> timeArrayList) {
        return Collections.max(timeArrayList, ((time, t1) -> {
            if (time.getMinute() > t1.getMinute())
                return 1;
            else if (time.getMinute() < t1.getMinute())
                return -1;
            else
                return 0;
        }));
    }

    public static Time minTime(List<Time> timeArrayList) {
        return Collections.min(timeArrayList, new Comparator<Time>() {
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
            time.append("시");
        }
        time.append(this.minute % 60);
        time.append("분");

        return time.toString();
    }

    public static String toString(int minute) {

        StringBuilder time = new StringBuilder();

        if ((minute / 60) > 0) {
            time.append(minute / 60);
            time.append("시");
        }
        time.append(minute % 60);
        time.append("분");

        return time.toString();
    }
}
