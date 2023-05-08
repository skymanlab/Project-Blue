package com.skyman.billiarddata.etc.calendar;

import java.util.ArrayList;

public class SameDate2 {

    private Date gameDate;
    private Counter myGameCounter;
    private ArrayList<Record> myGameRecord;
    private ArrayList<Reference> referenceArrayList;

    public SameDate2() {
        gameDate = new Date();
        myGameCounter = new Counter();
        myGameRecord = new ArrayList<>();
        referenceArrayList = new ArrayList<>();
    }


    ///////////////////////////////////////////////////
    // method /////////////////////////////////////////
    ///////////////////////////////////////////////////
    // method
    public Date getGameDate() {
        return gameDate;
    }

    public Counter getMyGameCounter() {
        return myGameCounter;
    }

    public ArrayList<Record> getMyGameRecord() {
        return myGameRecord;
    }

    public ArrayList<Reference> getReferenceArrayList() {
        return referenceArrayList;
    }


    ///////////////////////////////////////////////////
    // inner class ////////////////////////////////////
    ///////////////////////////////////////////////////
    public static class Date {
        /**
         * 참고 : 이너 클래스를 외부에서 생성하기 위해서는 static class 로 선언해야 한다.
         */
        private int year;
        private int month;
        private int day;
        private String date;

        public Date() {
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

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class Counter {
        private int winCounter;
        private int lossCounter;

        public Counter() {
            winCounter = 0;
            lossCounter = 0;
        }

        public int getWinCounter() {
            return winCounter;
        }

        public int getLossCounter() {
            return lossCounter;
        }


        public void plusOneWinCounter() {
            this.winCounter += 1;
        }

        public void plusOneLossCounter() {
            this.lossCounter += 1;
        }

        public int getTotalGameCounter() {
            return winCounter + lossCounter;
        }
    }

    public static class Reference {
        private int count;                  // billiardData의 count 맴버변수(billiard 테이블의 count 항목)
        private int index;                  // billiardDataArrayList의 index(같은 날짜의 billiardData가 billiardDataArrayList에 있는 위치)

        public Reference(int count, int index) {
            this.count = count;
            this.index = index;
        }

        public int getCount() {
            return count;
        }

        public int getIndex() {
            return index;
        }
    }


}
