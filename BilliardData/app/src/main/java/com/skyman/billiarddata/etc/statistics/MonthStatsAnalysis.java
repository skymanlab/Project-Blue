package com.skyman.billiarddata.etc.statistics;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.calendar.SameDateGame;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Counter;
import com.skyman.billiarddata.etc.game.GameMode;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;
import com.skyman.billiarddata.etc.game.RelativeRecord;
import com.skyman.billiarddata.etc.game.ScoreBoard;
import com.skyman.billiarddata.etc.game.Time;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MonthStatsAnalysis {

    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.ON;
    private static final String CLASS_NAME = "MonthStatsAnalysis";

    private Record totalRecord;         // 총 전적
    private Cost totalCost;             // 총 비용
    private List<Cost> costList;        // 비용 리스트
    private Time totalTime;             // 총 시간
    private List<Time> timeList;        // 시간 리스트
    private Map<Integer, Counter> playerCounterList;            // 참가자 수 별 게임 횟수
    private Map<GameMode, Counter> gameModeCounterList;         // 게임 모드 별 게임 횟수
    private List<ScoreBoard> scoreBoardList;                    // 각 게임의 스코어 리스트
    private Map<String, RelativeRecord> relativeRecordList;     // 상대 전적 리스트

    private AppDbManager appDbManager;

    public MonthStatsAnalysis() {

        this.appDbManager = appDbManager;
        this.totalRecord = new Record();
        this.totalCost = new Cost();
        this.costList = new ArrayList<>();
        this.totalTime = new Time();
        this.timeList = new ArrayList<>();
        this.playerCounterList = new HashMap<>();
        this.gameModeCounterList = new HashMap<>();
        this.scoreBoardList = new ArrayList<>();
        this.relativeRecordList = new LinkedHashMap<>();
    }

    public MonthStatsAnalysis(AppDbManager appDbManager) {

        this.appDbManager = appDbManager;
        this.totalRecord = new Record();
        this.totalCost = new Cost();
        this.costList = new ArrayList<>();
        this.totalTime = new Time();
        this.timeList = new ArrayList<>();
        this.playerCounterList = new HashMap<>();
        this.gameModeCounterList = new HashMap<>();
        this.scoreBoardList = new ArrayList<>();
        this.relativeRecordList = new LinkedHashMap<>();
    }

    public void analyze(SameDateGame sameDateGame, ArrayList<BilliardData> billiardDataArrayList) {
        if (sameDateGame == null)
            return;

        // 월 - 총 전적, 총 비용
        totalRecord.setWinCounter(sameDateGame.getRecord().getWinCounter());
        totalRecord.setLossCounter(sameDateGame.getRecord().getLossCounter());
        totalCost.setCost(sameDateGame.getTotalCost().getCost());

        ArrayList<Reference> referenceArrayList = sameDateGame.getReferenceArrayList();
        for (int index = 0; index < referenceArrayList.size(); index++) {
            Reference r = referenceArrayList.get(index);

            // 월 - 총 시간
            totalTime.plus(billiardDataArrayList.get(r.getIndex()).getPlayTime());
            // 월 - 시간 리스트에 추가
            timeList.add(new Time(billiardDataArrayList.get(r.getIndex()).getPlayTime()));
            // 월 - 게임 모드별 카운터
            updateGameModeCounterList(billiardDataArrayList.get(r.getIndex()).getGameMode());
            // 월 - 비용 리스트에 추가
            costList.add(new Cost(billiardDataArrayList.get(r.getIndex()).getCost()));
            // 월 - 플레이어 수 마다 추가
            updatePlayerCounterList(billiardDataArrayList.get(r.getIndex()).getPlayerCount());

            // only 2인 경기일 때만,
            if (billiardDataArrayList.get(r.getIndex()).getPlayerCount() == 2) {

                // 상대 전적 /
                final int tIndex = index;
                appDbManager.requestPlayerQuery(new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {
                        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();
                        playerDataArrayList.addAll(playerDbManager2.loadAllContentByBilliardCount(r.getCount()));

                        ScoreBoard scoreBoard = new ScoreBoard();

                        for (int pIndex = 0; pIndex < playerDataArrayList.size(); pIndex++) {

                            if (pIndex == 0) {
                                scoreBoard.getScoreList().add(playerDataArrayList.get(pIndex).getScore());
                            } else {
                                // 데이터 추출
                                String name = playerDataArrayList.get(pIndex).getPlayerName();                          // name
                                Cost cost = new Cost(billiardDataArrayList.get(r.getIndex()).getCost());                // cost
                                Time time = new Time(billiardDataArrayList.get(r.getIndex()).getPlayTime());            // time
                                GameMode gameMode = GameMode.of(billiardDataArrayList.get(r.getIndex()).getGameMode()); // game mode
                                Record record = new Record();                                                           // record, record.type
                                Record.Type type = null;                                                                // record's type
                                if (sameDateGame.getRecordTypeArrayList().get(tIndex).equals(Record.Type.WIN)) {
                                    record.getWinCounter().plusOne();
                                    type = Record.Type.WIN;
                                } else if (sameDateGame.getRecordTypeArrayList().get(tIndex).equals(Record.Type.LOSS)) {
                                    record.getLossCounter().plusOne();
                                    type = Record.Type.LOSS;
                                }

                                scoreBoard.getScoreList().add(playerDataArrayList.get(pIndex).getScore());              // score

                                // 상대 전적 - 위에서 추출한 데이터로 셋팅
                                if (relativeRecordList.containsKey(name)) {
                                    relativeRecordList.get(name).updateTo(gameMode, record, cost, time, type);
                                } else {
                                    RelativeRecord relativeRecord = new RelativeRecord(name, record, cost, time);
                                    relativeRecord.getTypeList().add(type);
                                    relativeRecord.setByGameModeList(gameMode, record, cost, time, type);
                                    relativeRecordList.put(name, relativeRecord);
                                }
                            }
                        } // for
                        scoreBoardList.add(scoreBoard);
                    }
                });

            }
        } // for
        printLog();

        relativeRecordList.forEach(
                (s, relativeRecord) -> {
                    relativeRecord.printLog();
                }
        );

    }

    /**
     * 매개변수로 받은 게임 모드 문자열을 GameMode 객체로 변환한 후 해당 게임 모드에 해당하는 Counter 객체의 값을 하나씩 올리는 메소드
     *
     * @param gameMode 게임 모드 문자열
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

    private void updatePlayerCounterList(int numberOfPlayers) {
        if (playerCounterList.containsKey(numberOfPlayers)) {
            playerCounterList.get(numberOfPlayers).plusOne();
        } else {
            Counter counter = new Counter();
            counter.plusOne();
            playerCounterList.put(Integer.valueOf(numberOfPlayers), counter);
        }
    }

    private void printLog() {
        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {
                Log.d(CLASS_NAME, "[MonthStatsAnalysis 분석 결과 확인]");

                Log.d(CLASS_NAME, "총 전적 : " + totalRecord);
                Log.d(CLASS_NAME, "총 비용 : " + totalCost);
                Log.d(CLASS_NAME, "평균 비용 : " + Cost.calculateAverageCost(costList));
                Log.d(CLASS_NAME, "최대 비용 : " + Cost.maxCost(costList));
                Log.d(CLASS_NAME, "최소 비용 : " + Cost.minCost(costList));
                Log.d(CLASS_NAME, "총 시간 : " + totalTime);
                Log.d(CLASS_NAME, "평균 시간 : " + Time.calculateAverageMinute(timeList));
                Log.d(CLASS_NAME, "최대 게임 시간 : " + Time.maxTime(timeList));
                Log.d(CLASS_NAME, "최소 게임 시간 : " + Time.minTime(timeList));

                Log.d(CLASS_NAME, "스코어 -> ");
                scoreBoardList.forEach(
                        scoreBoard -> {
                            Log.d(CLASS_NAME, "스코어 : (" + scoreBoard +")");
                        }
                );

                try {
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "최대 점수차로 이긴 경기의 스코어 : (" + ScoreBoard.winByMaxScoreDifference(scoreBoardList).toString() + ")");
//                    ScoreBoard.lossByMaxScoreDifference(scoreBoardList);
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "최대 점수차로 진 경기의 스코어 : (" + ScoreBoard.lossByMaxScoreDifference(scoreBoardList).toString() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d(CLASS_NAME, "종목 별 게임 수 -> ");
                gameModeCounterList.forEach(
                        (gameMode, integer) -> {
                            Log.d(CLASS_NAME, "종목 : " + gameMode + ", 게임 수 : " + integer.getValue());
                        }
                );

                Log.d(CLASS_NAME, "참가 수 -> ");
                playerCounterList.forEach(
                        (integer, counter) -> {
                            Log.d(CLASS_NAME, "참가자 수 : " + integer + ", 횟수 : " + counter.getValue());
                        }
                );

            }
    }
}
