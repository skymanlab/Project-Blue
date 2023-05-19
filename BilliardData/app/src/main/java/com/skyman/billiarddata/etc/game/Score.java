package com.skyman.billiarddata.etc.game;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private List<Point> pointList;      // 한 게임에서 모든 참가자의 점수를 표시

    public Score() {
        this.pointList = new ArrayList<>();
    }

    public List<Point> getPointList() {
        return pointList;
    }

    private static List<Integer> calculateScoreDifference(List<Score> scoreList) {
        // 2인 경기 만
        List<Integer> scoreDifferenceList = new ArrayList<>();

        scoreList.forEach(
                score -> {
                    scoreDifferenceList.add(
                            score.getPointList().get(0).getEarnedPoints() - score.getPointList().get(1).getEarnedPoints()
                    );
                }
        );
        return scoreDifferenceList;
    }

    public static Score winByMaxScoreDifference(List<Score> scoreList) throws ScoreException {
        // 2인 경기 만
        List<Integer> scoreDifferenceList = calculateScoreDifference(scoreList);

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
            return scoreList.get(maxIndex);
        } else
            throw new ScoreException("이긴 경기가 없습니다.");
    }

    public static Score lossByMaxScoreDifference(List<Score> scoreList) throws ScoreException {
        if (scoreList.size() == 0)
            return null;
        // 2인 경기 만
        List<Integer> differenceList = calculateScoreDifference(scoreList);

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
            return scoreList.get(minIndex);
        } else {
            throw new ScoreException("진 경기가 없습니다.");
        }
    }

    @NonNull
    @Override
    public String toString() {
        if (pointList.size() == 0)
            return null;

        // 2인 경기만
        StringBuilder scoreString = new StringBuilder();
        scoreString.append(pointList.get(0).getEarnedPoints());
        scoreString.append(":");
        scoreString.append(pointList.get(1).getEarnedPoints());
        return scoreString.toString();
    }

    public static class ScoreException extends Exception {
        public ScoreException(String msg){
            super(msg);
        }
    }
}
