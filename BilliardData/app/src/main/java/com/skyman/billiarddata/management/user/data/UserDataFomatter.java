package com.skyman.billiarddata.management.user.data;

public class UserDataFomatter {
    public static String setFormatToGameRecord(int gameRecordWin, int gameRecordLoss){
        StringBuilder temp = new StringBuilder();
        temp.append(gameRecordWin+gameRecordLoss);
        temp.append("전 ");
        temp.append(gameRecordWin);
        temp.append("승 ");
        temp.append(gameRecordLoss);
        temp.append("패");
        return temp.toString();
    }
}
