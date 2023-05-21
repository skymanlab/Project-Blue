package com.skyman.billiarddata.etc.calendar;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Date;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SameDateGame implements Serializable {
    private Date date;                          // 날짜
    private Record record;                      // 전적
    private Cost totalCost;                     // 가격
    private List<Record.Type> recordTypeList;   // 승패 유형 리스트
    private List<Reference> referenceList;      // 참고 리스트

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

    public void printLog(LogSwitch CLASS_LOG_SWITCH, String CLASS_NAME) {
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
