package com.skyman.billiarddata.etc.calendar;

import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Date;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;
import com.skyman.billiarddata.table.billiard.data.BilliardData;

import java.util.ArrayList;

public class SameDateGame {

    private Date date;
    private Record record;
    private Cost totalCost;
    private ArrayList<Record.Type> recordTypeArrayList;
    private ArrayList<Reference> referenceArrayList;

    public SameDateGame() {
        this.date = new Date();
        this.record = new Record();
        this.totalCost = new Cost();
        this.recordTypeArrayList = new ArrayList<>();
        this.referenceArrayList = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public Record getRecord() {
        return record;
    }

    public Cost getTotalCost() {
        return totalCost;
    }

    public ArrayList<Record.Type> getRecordTypeArrayList() {
        return recordTypeArrayList;
    }

    public ArrayList<Reference> getReferenceArrayList() {
        return referenceArrayList;
    }
}
