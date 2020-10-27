package com.skyman.billiarddata.management.friend.data;

import java.io.Serializable;

public class FriendData implements Serializable {

    // variable : desc
    private long id;                        // 0. id
    private long userId;                    // 1. user id
    private String name;                    // 2. name
    private int gameRecordWin;              // 3. game record win
    private int gameRecordLoss;             // 4. game record loss
    private long recentGameBilliardCount;   // 5. recent game billiard count
    private int totalPlayTime;              // 6. total play time
    private int totalCost;                  // 7. total cost

    // method : getter, setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameRecordWin() {
        return gameRecordWin;
    }

    public void setGameRecordWin(int gameRecordWin) {
        this.gameRecordWin = gameRecordWin;
    }

    public int getGameRecordLoss() {
        return gameRecordLoss;
    }

    public void setGameRecordLoss(int gameRecordLoss) {
        this.gameRecordLoss = gameRecordLoss;
    }

    public long getRecentGameBilliardCount() {
        return recentGameBilliardCount;
    }

    public void setRecentGameBilliardCount(long recentGameBilliardCount) {
        this.recentGameBilliardCount = recentGameBilliardCount;
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(int totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
