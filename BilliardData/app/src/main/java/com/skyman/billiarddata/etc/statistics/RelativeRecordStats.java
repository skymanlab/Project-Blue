package com.skyman.billiarddata.etc.statistics;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.GameMode;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Score;
import com.skyman.billiarddata.etc.game.Time;

import java.util.HashMap;
import java.util.Map;

public class RelativeRecordStats extends Stats {
    private String name;                                    // 상대의 이름
    private Map<GameMode, ByGameMode> byGameModeList;       // 게임 모드 별로 상대 전적을 저장하는 이너 클래스

    public RelativeRecordStats(String name) {
        super();
        this.name = name;
        this.byGameModeList = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<GameMode, ByGameMode> getByGameModeList() {
        return byGameModeList;
    }

    /**
     * 매개변수로 받은 객체로 현재 객체의 내용들을 업데이트하는 메소드(record, costList, timeList, typeList, scoreList, byGameModeList 업데이트)
     *
     * @param gameMode   게임 모드
     * @param record1    전적
     * @param cost       비용
     * @param time       시간
     * @param recordType 승패 유형
     * @param score      스코어
     */
    public void updateAll(GameMode gameMode, Record record1, Cost cost, Time time, Record.Type recordType, Score score) {
        // record
        super.record.plusByType(record1);
        // 각 리스트에 객체 추가
        super.add(cost, time, recordType, score);

        // byGameModeList
        if (byGameModeList.containsKey(gameMode)) {
            updateByGameMode(gameMode, record1, cost, time, recordType, score);
        } else {
            putByGameMode(gameMode, record1, cost, time, recordType, score);
        }
    }

    /**
     * 매개변수로 받은 객체로 byGameModeList 에 기존에 있는 byGameMode 객체의 내용을 업데이트 하는 메소드
     *
     * @param gameMode   게임 모드
     * @param record     전적
     * @param cost       비용
     * @param time       시간
     * @param recordType 승패 유형
     * @param score      스코어
     */
    public void updateByGameMode(GameMode gameMode, Record record, Cost cost, Time time, Record.Type recordType, Score score) {
        // record
        byGameModeList.get(gameMode).getRecord().plusByType(record);

        // 각 리스트에 객체 추가
        byGameModeList.get(gameMode).add(cost, time, recordType, score);
    }

    /**
     * 매개변수로 받은 객체의 토대로 새로운 ByGameMode 객체를 생성하여, 이를 byGameModeList 에 추가 하는 메소드
     *
     * @param gameMode   게임 모드
     * @param record     전적
     * @param cost       비용
     * @param time       시간
     * @param recordType 승패 유형
     * @param score      스코어
     */
    public void putByGameMode(GameMode gameMode, Record record, Cost cost, Time time, Record.Type recordType, Score score) {
        ByGameMode byGameMode = new ByGameMode(gameMode);
        // record
        byGameMode.getRecord().plusByType(record);
        // 각 리스트에 객체 추가
        byGameMode.add(cost, time, recordType, score);

        // 새로 생성한 객체를 byGameModeList 에 put
        byGameModeList.put(gameMode, byGameMode);
    }

    @Override
    public void printLog(LogSwitch CLASS_LOG_SWITCH, String CLASS_NAME) {
        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                Log.d(CLASS_NAME, "[RelativeRecordStats 내용 확인");
                Log.d(CLASS_NAME, "==== name : " + name + " ====");

                // record
                Log.d(CLASS_NAME, "| 1. 전적 : " + record);
                // cost
                StringBuilder costListString = new StringBuilder();
                costListString.append("| 2. 비용 리스트 : ");
                costList.forEach(
                        cost1 -> {
                            costListString.append("[");
                            costListString.append(cost1);
                            costListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, costListString.toString());
                Log.d(CLASS_NAME, "| - 총 비용 : " + Cost.totalCost(costList));
                Log.d(CLASS_NAME, "| - 평균 비용 : " + Cost.averageCost(costList));
                Log.d(CLASS_NAME, "| - 최대 비용 : " + Cost.maxCost(costList));
                Log.d(CLASS_NAME, "| - 최소 비용 : " + Cost.minCost(costList));


                // time
                StringBuilder timeListString = new StringBuilder();
                timeListString.append("| 3. 시간 리스트 : ");
                timeList.forEach(
                        time1 -> {
                            timeListString.append("[");
                            timeListString.append(time1);
                            timeListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, timeListString.toString());
                Log.d(CLASS_NAME, "| - 총 시간 : " + Time.totalTime(timeList));
                Log.d(CLASS_NAME, "| - 평균 시간 : " + Time.averageMinute(timeList));
                Log.d(CLASS_NAME, "| - 최대 시간 : " + Time.maxTime(timeList));
                Log.d(CLASS_NAME, "| - 최소 시간 : " + Time.minTime(timeList));

                // score
                StringBuilder scoreListString = new StringBuilder();
                scoreListString.append("| 4. 스코어 리스트 : ");
                scoreList.forEach(
                        score -> {
                            scoreListString.append("[");
                            scoreListString.append(score.toScoreString());
                            scoreListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, scoreListString.toString());
                try {
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "| - 최대 점수차로 이긴 경기의 스코어 : (" + Score.winByMaxScoreDifference(scoreList).toString() + ")");
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "| - 최대 점수차로 진 경기의 스코어 : (" + Score.lossByMaxScoreDifference(scoreList).toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // record type
                StringBuilder recordTypeString = new StringBuilder();
                recordTypeString.append("| 5. 승패 여부 리스트 : ");
                recordTypeList.forEach(
                        type -> {
                            recordTypeString.append("[");
                            recordTypeString.append(type);
                            recordTypeString.append("]");
                        }
                );
                Log.d(CLASS_NAME, recordTypeString.toString());

                Log.d(CLASS_NAME, "[byGameMode 내용 확인]");
                byGameModeList.forEach(
                        (gameMode, byGameMode) -> {
                            byGameMode.printLog(CLASS_LOG_SWITCH, CLASS_NAME);
                        }
                );
            }
    }

    public class ByGameMode extends Stats {
        private GameMode gameMode;          //     게임 모드

        public ByGameMode(GameMode gameMode) {
            super();
            this.gameMode = gameMode;
        }

        public GameMode getGameMode() {
            return gameMode;
        }

        @Override
        public void printLog(LogSwitch CLASS_LOG_SWITCH, String CLASS_NAME) {
            if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
                if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                    Log.d(CLASS_NAME, "==== game mode : " + gameMode.toString() + " ====");

                    // record
                    Log.d(CLASS_NAME, "|| 1. 전적 : " + record);
                    // cost
                    StringBuilder costListString = new StringBuilder();
                    costListString.append("|| 2. 비용 리스트 : ");
                    costList.forEach(
                            cost1 -> {
                                costListString.append("[");
                                costListString.append(cost1);
                                costListString.append("]");
                            }
                    );
                    Log.d(CLASS_NAME, costListString.toString());
                    Log.d(CLASS_NAME, "|| - 총 비용 : " + Cost.totalCost(costList));
                    Log.d(CLASS_NAME, "|| - 평균 비용 : " + Cost.averageCost(costList));
                    Log.d(CLASS_NAME, "|| - 최대 비용 : " + Cost.maxCost(costList));
                    Log.d(CLASS_NAME, "|| - 최소 비용 : " + Cost.minCost(costList));


                    // time
                    StringBuilder timeListString = new StringBuilder();
                    timeListString.append("|| 3. 시간 리스트 : ");
                    timeList.forEach(
                            time1 -> {
                                timeListString.append("[");
                                timeListString.append(time1);
                                timeListString.append("]");
                            }
                    );
                    Log.d(CLASS_NAME, timeListString.toString());
                    Log.d(CLASS_NAME, "|| - 총 시간 : " + Time.totalTime(timeList));
                    Log.d(CLASS_NAME, "|| - 평균 시간 : " + Time.averageMinute(timeList));
                    Log.d(CLASS_NAME, "|| - 최대 시간 : " + Time.maxTime(timeList));
                    Log.d(CLASS_NAME, "|| - 최소 시간 : " + Time.minTime(timeList));

                    // score
                    StringBuilder scoreListString = new StringBuilder();
                    scoreListString.append("|| 4. 스코어 리스트 : ");
                    scoreList.forEach(
                            score -> {
                                scoreListString.append("[");
                                scoreListString.append(score.toScoreString());
                                scoreListString.append("]");
                            }
                    );
                    Log.d(CLASS_NAME, scoreListString.toString());
                    try {
                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "|| - 최대 점수차로 이긴 경기의 스코어 : (" + Score.winByMaxScoreDifference(scoreList).toString() + ")");
                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "|| - 최대 점수차로 진 경기의 스코어 : (" + Score.lossByMaxScoreDifference(scoreList).toString() + ")");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // record type
                    StringBuilder recordTypeString = new StringBuilder();
                    recordTypeString.append("|| 5. 승패 여부 리스트 : ");
                    recordTypeList.forEach(
                            type -> {
                                recordTypeString.append("[");
                                recordTypeString.append(type);
                                recordTypeString.append("]");
                            }
                    );
                    Log.d(CLASS_NAME, recordTypeString.toString());


                }
        }

    }
}
