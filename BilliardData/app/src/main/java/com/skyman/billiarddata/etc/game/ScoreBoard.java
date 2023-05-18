package com.skyman.billiarddata.etc.game;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "ScoreBoard";

    private List<Integer> scoreList;

    public ScoreBoard() {
        this.scoreList = new ArrayList<>();
    }

    public List<Integer> getScoreList() {
        return scoreList;
    }

    private static int calculateScoreDifference(List<Integer> scoreList) {
        // 2인 경기 만
        return scoreList.get(0) - scoreList.get(1);
    }


    private static List<Integer> calculateScoreDifferenceList(List<ScoreBoard> scoreBoardList) {
        // 2인 경기 만
        List<Integer> scoreDifferenceList = new ArrayList<>();

        scoreBoardList.forEach(
                scoreBoard -> {
                    scoreDifferenceList.add(
                            scoreBoard.getScoreList().get(0) - scoreBoard.getScoreList().get(1)
                    );
                }
        );
        return scoreDifferenceList;
    }


    public static ScoreBoard winByMaxScoreDifference(List<ScoreBoard> scoreBoardList) throws NullPointerException {
        // 2인 경기 만
        List<Integer> scoreDifferenceList = calculateScoreDifferenceList(scoreBoardList);

        int max = -1;
        int maxIndex = -1;
        for (int index = 0; index < scoreDifferenceList.size(); index++) {
            if (scoreDifferenceList.get(index) > 0)
                if (max < scoreDifferenceList.get(index)) {
                    max = scoreDifferenceList.get(index);
                    maxIndex = index;
                }
        }
        if (max > -1 && maxIndex > -1) {
            return scoreBoardList.get(maxIndex);
        } else
            return null;
    }

    public static ScoreBoard lossByMaxScoreDifference(List<ScoreBoard> scoreBoardList) throws NullPointerException {
        // 2인 경기 만
        List<Integer> differenceList = calculateScoreDifferenceList(scoreBoardList);

        int min = 1;
        int minIndex = -1;
        for (int index = 0; index < differenceList.size(); index++) {
            if (differenceList.get(index) < 0)
                if (min > differenceList.get(index)) {
                    min = differenceList.get(index);
                    minIndex = index;
                }
        }
        if (min < 1 && minIndex > -1) {
            return scoreBoardList.get(minIndex);
        } else
            return null;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder scoreBoard = new StringBuilder();

        switch (scoreList.size()) {
            case 2:
                scoreBoard.append(this.scoreList.get(0));
                scoreBoard.append(" : ");
                scoreBoard.append(this.scoreList.get(1));
                break;
            case 3:
                scoreBoard.append(this.scoreList.get(0));
                scoreBoard.append(" : ");
                scoreBoard.append(this.scoreList.get(1));
                scoreBoard.append(" : ");
                scoreBoard.append(this.scoreList.get(2));
                break;
            case 4:
                scoreBoard.append(this.scoreList.get(0));
                scoreBoard.append(" : ");
                scoreBoard.append(this.scoreList.get(1));
                scoreBoard.append(" : ");
                scoreBoard.append(this.scoreList.get(2));
                scoreBoard.append(" : ");
                scoreBoard.append(this.scoreList.get(3));
                break;
        }
        return scoreBoard.toString();
    }
}
