package com.skyman.billiarddata.etc.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelativeRecord {
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.ON;
    private static final String CLASS_NAME = "RelativeRecord";


    private String name;
    private Record record;
    private Cost cost;
    private List<Cost> costList;
    private Time time;
    private List<Time> timeList;
    private List<Record.Type> typeList;
    private Map<GameMode, ByGameMode> byGameModeList;

    public RelativeRecord() {
        this.name = null;
        this.record = new Record();
        this.cost = new Cost();
        this.time = new Time();
        this.typeList = new ArrayList<>();
        this.byGameModeList = new HashMap<>();
    }

    public RelativeRecord(String name, Record record, Cost cost, Time time) {
        this.name = name;
        this.record = record;
        this.cost = cost;
        this.costList = new ArrayList<>();
        this.costList.add(cost);
        this.time = time;
        this.timeList= new ArrayList<>();
        this.timeList.add(time);
        this.typeList = new ArrayList<>();
        this.byGameModeList = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Record getRecord() {
        return record;
    }

    public Cost getCost() {
        return cost;
    }

    public Time getTime() {
        return time;
    }

    public List<Cost> getCostList() {
        return costList;
    }

    public List<Time> getTimeList() {
        return timeList;
    }

    public List<Record.Type> getTypeList() {
        return typeList;
    }

    public Map<GameMode, ByGameMode> getByGameModeList() {
        return byGameModeList;
    }

    public void updateTo(@NonNull GameMode gameMode, @NonNull Record record, @NonNull Cost cost, @NonNull Time time, @NonNull Record.Type type) {
        this.record.getWinCounter().plus(record.getWinCounter().getValue());
        this.record.getLossCounter().plus(record.getLossCounter().getValue());
        this.cost.plus(cost.getCost());
        this.costList.add(cost);
        this.time.plus(time.getMinute());
        this.timeList.add(time);
        this.typeList.add(type);

        setByGameModeList(gameMode, record, cost, time, type);
    }

    public void setByGameModeList(@NonNull GameMode gameMode, @NonNull Record record, @NonNull Cost cost, @NonNull Time time, @NonNull Record.Type type) {

        if (byGameModeList.containsKey(gameMode)) {

            byGameModeList.get(gameMode).getRecord().getWinCounter().plus(record.getWinCounter().getValue());
            byGameModeList.get(gameMode).getRecord().getLossCounter().plus(record.getLossCounter().getValue());
            byGameModeList.get(gameMode).getCost().plus(cost.getCost());
            byGameModeList.get(gameMode).getTime().plus(time.getMinute());
            byGameModeList.get(gameMode).getTypeList().add(type);

        } else {

            ByGameMode byGameMode = new ByGameMode(gameMode);
            // Record
            byGameMode.getRecord().getWinCounter().plus(record.getWinCounter().getValue());
            byGameMode.getRecord().getLossCounter().plus(record.getLossCounter().getValue());
            // Cost
            byGameMode.getCost().plus(cost.getCost());
            // Time
            byGameMode.getTime().plus(time.getMinute());
            // Record.Type
            byGameMode.getTypeList().add(type);

            byGameModeList.put(gameMode, byGameMode);
        }
    }

    public void printLog() {
        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {

                Log.d(CLASS_NAME, "[RelativeRecord 내용 확인]");

                Log.d(CLASS_NAME, "이름 : " + name);
                Log.d(CLASS_NAME, "전적 : " + record.toString());
                Log.d(CLASS_NAME, "비용 : " + cost.toString());
                Log.d(CLASS_NAME, "시간 : " + time.toString());

                StringBuilder typeString = new StringBuilder();
                typeString.append("승패 여부 : ");
                typeList.forEach(
                        type -> {
                            typeString.append("[");
                            typeString.append(type);
                            typeString.append("]");
                        }
                );
                Log.d(CLASS_NAME, typeString.toString());

                byGameModeList.forEach(
                        (gameMode, byGameMode) -> {
                            byGameMode.printLog();
                        }
                );
            }

    }

    static class ByGameMode {
        private GameMode gameMode;
        private Record record;
        private Cost cost;
        private List<Cost> costList;
        private Time time;
        private List<Time> timeList;
        private List<Record.Type> typeList;

        public ByGameMode(GameMode gameMode) {
            this.gameMode = gameMode;
            this.record = new Record();
            this.cost = new Cost();
            this.time = new Time();
            this.typeList = new ArrayList<>();
        }

        public GameMode getGameMode() {
            return gameMode;
        }

        public Record getRecord() {
            return record;
        }

        public Cost getCost() {
            return cost;
        }

        public Time getTime() {
            return time;
        }

        public List<Cost> getCostList() {
            return costList;
        }

        public List<Time> getTimeList() {
            return timeList;
        }

        public List<Record.Type> getTypeList() {
            return typeList;
        }

        public void printLog() {
            if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
                if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                    Log.d(CLASS_NAME, "==== gameMode : " + gameMode.toString() + " ====");
                    Log.d(CLASS_NAME, "전적 : " + record.toString());
                    Log.d(CLASS_NAME, "비용 : " + cost.toString());
                    Log.d(CLASS_NAME, "시간 : " + time.toString());

                    StringBuilder typeString = new StringBuilder();
                    typeString.append("승패 여부 : ");
                    typeList.forEach(
                            type -> {
                                typeString.append("[");
                                typeString.append(type);
                                typeString.append("]");
                            }
                    );
                    Log.d(CLASS_NAME, typeString.toString());
                }
        }
    }

}
