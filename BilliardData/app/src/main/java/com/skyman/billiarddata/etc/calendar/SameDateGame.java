package com.skyman.billiarddata.etc.calendar;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Date;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;

import java.util.ArrayList;
import java.util.List;

public class SameDateGame {
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.ON;
    private static final String CLASS_NAME = "SameDateGame";
    private Date date;
    private Record record;
    private Cost totalCost;
    private List<Record.Type> recordTypeList;
    private List<Reference> referenceList;

    public SameDateGame() {
        this.date = new Date();
        this.record = new Record();
        this.totalCost = new Cost();
        this.recordTypeList = new ArrayList<>();
        this.referenceList = new ArrayList<>();
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

    public List<Record.Type> getRecordTypeList() {
        return recordTypeList;
    }

    public List<Reference> getReferenceList() {
        return referenceList;
    }

    public void printLog() {
        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                Log.d(CLASS_NAME, "[sameDateGame 내용 확인]");
                Log.d(CLASS_NAME, "|| 1. Date : " + date);
                Log.d(CLASS_NAME, "|| 2. record : " + record);
                Log.d(CLASS_NAME, "|| 3. totalCost : " + totalCost);
                // recordTypeList
                StringBuilder recordTypeString = new StringBuilder();
                recordTypeString.append("|| 4. record type list :");
                recordTypeList.forEach(
                        type -> {
                            recordTypeString.append("[");
                            recordTypeString.append(type);
                            recordTypeString.append("]");
                        }
                );
                Log.d(CLASS_NAME, recordTypeString.toString());

                // referenceList
                Log.d(CLASS_NAME, "|| 5. reference list -> ");
                StringBuilder countString = new StringBuilder();
                StringBuilder indexString = new StringBuilder();
                countString.append("||  - count : ");
                indexString.append("||  - index : ");

                referenceList.forEach(
                        reference -> {
                            // count
                            countString.append("[");
                            countString.append(reference.getCount());
                            countString.append("]");
                            // index
                            indexString.append("[");
                            indexString.append(reference.getIndex());
                            indexString.append("]");

                        }
                );
                Log.d(CLASS_NAME, countString.toString());
                Log.d(CLASS_NAME, indexString.toString());

            }
    }
}
