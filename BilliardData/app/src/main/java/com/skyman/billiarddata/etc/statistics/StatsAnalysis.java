package com.skyman.billiarddata.etc.statistics;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.calendar.SameDateGame;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Counter;
import com.skyman.billiarddata.etc.game.GameMode;
import com.skyman.billiarddata.etc.game.Point;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;
import com.skyman.billiarddata.etc.game.Score;
import com.skyman.billiarddata.etc.game.Time;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsAnalysis extends Stats {

    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.ON;
    private static final String CLASS_NAME = "StatsAnalysis";


    // instance variable : information
    protected Map<Integer, Counter> playerCounterList;                  // 모든 게임의 참가자 수 별 게임 횟수 : <참가자 수(2인, 3인, 4인), 게임 횟수>
    protected Map<GameMode, Counter> gameModeCounterList;               // 모든 게임의 게임 모드 별 게임 횟수 : <게임 모드(3구, 4구, 포켓볼), 게임 횟수>
    protected Map<String, RelativeRecordStats> relativeRecordStatsList;  // 모든 게임의 상대 전적 리스트 : <상대 이름, 전적>


    // instance variable : DB
    private AppDbManager appDbManager;


    public StatsAnalysis(AppDbManager appDbManager) {
        super();

        this.appDbManager = appDbManager;

        this.playerCounterList = new HashMap<>();
        this.gameModeCounterList = new HashMap<>();
        this.relativeRecordStatsList = new HashMap<>();
    }


    public Map<Integer, Counter> getPlayerCounterList() {
        return playerCounterList;
    }

    public Map<GameMode, Counter> getGameModeCounterList() {
        return gameModeCounterList;
    }

    public Map<String, RelativeRecordStats> getRelativeRecordStatsList() {
        return relativeRecordStatsList;
    }


    /**
     * 매개변수로 SameDataGame 객체와 ArrayList<BilliardData> 객체를 받아서, 분석한 뒤 필요한 정보를 해당 변수에 저장하는 메소드
     *
     * @param sameDateGame
     * @param billiardDataList
     */
    public void analyze(SameDateGame sameDateGame, List<BilliardData> billiardDataList) {
        if (billiardDataList == null || billiardDataList.size() == 0)
            return;
        if (sameDateGame == null)
            return;

        // 월 - 총 전적, 총 비용
        record.plusByType(sameDateGame.getRecord());
        recordTypeList.addAll(sameDateGame.getRecordTypeList());

        List<Reference> referenceArrayList = sameDateGame.getReferenceList();
        for (int index = 0; index < referenceArrayList.size(); index++) {
            Reference r = referenceArrayList.get(index);

            // 월 - 시간 리스트에 추가
            timeList.add(new Time(billiardDataList.get(r.getIndex()).getPlayTime()));
            // 월 - 비용 리스트에 추가
            costList.add(new Cost(billiardDataList.get(r.getIndex()).getCost()));
            // 월 - 게임 모드별 카운터
            updateGameModeCounterList(billiardDataList.get(r.getIndex()).getGameMode());
            // 월 - 플레이어 수 마다 추가
            updatePlayerCounterList(billiardDataList.get(r.getIndex()).getPlayerCount());

            // only 2인 경기일 때만,
            if (billiardDataList.get(r.getIndex()).getPlayerCount() == 2) {

                // 상대 전적 /
                final int tIndex = index;
                appDbManager.requestPlayerQuery(new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {
                        ArrayList<PlayerData> playerDataList = new ArrayList<>();
                        playerDataList.addAll(playerDbManager2.loadAllContentByBilliardCount(r.getCount()));

                        Score score = new Score();

                        for (int pIndex = 0; pIndex < playerDataList.size(); pIndex++) {

                            if (pIndex == 0) {
                                score.getPointList().add(
                                        new Point(
                                                playerDataList.get(pIndex).getPlayerName(),
                                                playerDataList.get(pIndex).getTargetScore(),
                                                playerDataList.get(pIndex).getScore()
                                        )
                                );
                            } else {
                                // 데이터 추출
                                String name = playerDataList.get(pIndex).getPlayerName();                               // name
                                GameMode gameMode = GameMode.of(billiardDataList.get(r.getIndex()).getGameMode());      // game mode
                                Cost cost = new Cost(billiardDataList.get(r.getIndex()).getCost());                     // cost
                                Time time = new Time(billiardDataList.get(r.getIndex()).getPlayTime());                 // time
                                Record record = new Record();                                                           // record
                                Record.Type type = null;                                                                // record's type
                                if (recordTypeList.get(tIndex).equals(Record.Type.WIN)) {
                                    record.getWinCounter().plusOne();
                                    type = Record.Type.WIN;
                                } else if (recordTypeList.get(tIndex).equals(Record.Type.LOSS)) {
                                    record.getLossCounter().plusOne();
                                    type = Record.Type.LOSS;
                                }
                                score.getPointList().add(                                                               // score
                                        new Point(
                                                playerDataList.get(pIndex).getPlayerName(),
                                                playerDataList.get(pIndex).getTargetScore(),
                                                playerDataList.get(pIndex).getScore()
                                        )
                                );
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "name : " + name);
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "cost : " + cost);
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "time : " + time);
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "game mode : " + gameMode);
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "record : " + record);
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "record's type : " + type);
//                                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "score : " + score);


                                // 상대 전적 - 위에서 추출한 데이터로 셋팅
                                if (relativeRecordStatsList.containsKey(name)) {
                                    relativeRecordStatsList.get(name).updateAll(gameMode, record, cost, time, type, score);
                                } else {
                                    RelativeRecordStats relativeRecord = new RelativeRecordStats(name);
                                    relativeRecord.updateAll(gameMode, record, cost, time, type, score);
                                    relativeRecordStatsList.put(name, relativeRecord);
                                }
                            }
                        } // for
                        scoreList.add(score);
                    }
                });

            }
        } // for
        printLog();
    }


    /**
     * gameModeCounterList 에서 매개변수로 받은 게임모드(key, String or GameMode)와 같은 key에 해당하는 Counter에 +1 하는 메소드
     *
     * @param gameMode
     */
    private void updateGameModeCounterList(String gameMode) {
        GameMode convertedGameMode = GameMode.of(gameMode);
        if (gameModeCounterList.containsKey(convertedGameMode)) {
            gameModeCounterList.get(convertedGameMode).plusOne();
        } else {
            Counter counter = new Counter();
            counter.plusOne();
            gameModeCounterList.put(convertedGameMode, counter);
        }
    }


    /**
     * playerCounterList 에서 매개변수로 받은 참가자수(numberOfPlayers)와 같은 key에 해당하는 Counter에 +1 하는 메소드
     *
     * @param numberOfPlayers
     */
    private void updatePlayerCounterList(int numberOfPlayers) {
        if (playerCounterList.containsKey(numberOfPlayers)) {
            playerCounterList.get(numberOfPlayers).plusOne();
        } else {
            Counter counter = new Counter();
            counter.plusOne();
            playerCounterList.put(Integer.valueOf(numberOfPlayers), counter);
        }
    }

    public void printLog() {
        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                Log.d(CLASS_NAME, "[StatsAnalysis 분석 결과 확인]");

                // record
                Log.d(CLASS_NAME, "1. 총 전적 : " + record);

                // cost
                StringBuilder costListString = new StringBuilder();
                costListString.append("2. 비용 리스트 : ");
                costList.forEach(
                        cost1 -> {
                            costListString.append("[");
                            costListString.append(cost1);
                            costListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, costListString.toString());
                Log.d(CLASS_NAME, "- 총 비용 : " + Cost.totalCost(costList));
                Log.d(CLASS_NAME, "- 평균 비용 : " + Cost.averageCost(costList));
                Log.d(CLASS_NAME, "- 최대 비용 : " + Cost.maxCost(costList));
                Log.d(CLASS_NAME, "- 최소 비용 : " + Cost.minCost(costList));

                // time
                StringBuilder timeListString = new StringBuilder();
                timeListString.append("3. 시간 리스트 : ");
                timeList.forEach(
                        time1 -> {
                            timeListString.append("[");
                            timeListString.append(time1);
                            timeListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, timeListString.toString());
                Log.d(CLASS_NAME, "- 총 시간 : " + Time.totalTime(timeList));
                Log.d(CLASS_NAME, "- 평균 시간 : " + Time.averageMinute(timeList));
                Log.d(CLASS_NAME, "- 최대 시간 : " + Time.maxTime(timeList));
                Log.d(CLASS_NAME, "- 최소 시간 : " + Time.minTime(timeList));

                // score
                StringBuilder scoreListString = new StringBuilder();
                scoreListString.append("4. 스코어 리스트 : ");
                scoreList.forEach(
                        score -> {
                            scoreListString.append("[");
                            scoreListString.append(score.toScoreString());
                            scoreListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, scoreListString.toString());
                try {
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "- 최대 점수차로 이긴 경기의 스코어 : (" + Score.winByMaxScoreDifference(scoreList).toString() + ")");
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "- 최대 점수차로 진 경기의 스코어 : (" + Score.lossByMaxScoreDifference(scoreList).toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // record type
                StringBuilder recordTypeString = new StringBuilder();
                recordTypeString.append("5. 승패 여부 리스트 : ");
                recordTypeList.forEach(
                        type -> {
                            recordTypeString.append("[");
                            recordTypeString.append(type);
                            recordTypeString.append("]");
                        }
                );
                Log.d(CLASS_NAME, recordTypeString.toString());

                // game mode counter
                StringBuilder gameModeCounterListString = new StringBuilder();
                gameModeCounterListString.append("6. 종목 별 게임 수 리스트 : ");
                gameModeCounterList.forEach(
                        (gameMode, counter) -> {
                            gameModeCounterListString.append("[종목:");
                            gameModeCounterListString.append(gameMode);
                            gameModeCounterListString.append(",횟수:");
                            gameModeCounterListString.append(counter.getValue());
                            gameModeCounterListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, gameModeCounterListString.toString());

                // player counter
                StringBuilder playCounterListString = new StringBuilder();
                playCounterListString.append("7. 참가자 수 리스트 : ");
                playerCounterList.forEach(
                        (integer, counter) -> {
                            playCounterListString.append("[참가자 수:");
                            playCounterListString.append(integer);
                            playCounterListString.append(",횟수:");
                            playCounterListString.append(counter.getValue());
                            playCounterListString.append("]");
                        }
                );
                Log.d(CLASS_NAME, playCounterListString.toString());

                // relative record
                relativeRecordStatsList.forEach(
                        (s, relativeRecord) -> {
                            relativeRecord.printLog(CLASS_LOG_SWITCH, CLASS_NAME);
                        }
                );
            }
    }
}
