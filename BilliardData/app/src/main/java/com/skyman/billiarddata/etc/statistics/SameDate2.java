package com.skyman.billiarddata.etc.statistics;

import java.util.ArrayList;

public class SameDate2 {

    private Date gameDate;
    private Counter myGameCounter;
    private ArrayList<Record> myGameRecord;
    private ArrayList<Reference> referenceArrayList;

    private Cost totalCost;

    public SameDate2() {
        gameDate = new Date();
        myGameCounter = new Counter();
        myGameRecord = new ArrayList<>();
        totalCost = new Cost();
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

    public Cost getTotalCost() {
        return totalCost;
    }


    public ArrayList<Reference> getReferenceArrayList() {
        return referenceArrayList;
    }

}
