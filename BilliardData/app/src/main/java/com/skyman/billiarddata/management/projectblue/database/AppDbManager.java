package com.skyman.billiarddata.management.projectblue.database;

import android.content.Context;

import com.skyman.billiarddata.management.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class AppDbManager {

    // instance variable
    private Context context;

    // instance variable
    private AppDbSetting2 appDbSetting2;

    // instance variable
    private BilliardDbManager2 billiardDbManager2;

    // instance variable
    private boolean shouldRequestUserDbConnection;
    private boolean shouldRequestFriendDbConnection;
    private boolean shouldRequestBilliardDbConnection;
    private boolean shouldRequestPlayerDbConnection;

    // constructor
    public AppDbManager(Context context) {
        this.context = context;
    }

    public void connectDb(boolean shouldRequestUserDbConnection,
                          boolean shouldRequestFriendDbConnection,
                          boolean shouldRequestBilliardDbConnection,
                          boolean shouldRequestPlayerDbConnection) {

        // AppDbSetting 을 통해서 SQLite Open Helper 열기
        appDbSetting2 = new AppDbSetting2(context);
        appDbSetting2.createOpenHelper();

        StringBuilder connectingList = new StringBuilder();

        // user
        if (shouldRequestUserDbConnection) {
            connectingList
                    .append("<")
                    .append(">");
        }

        // friend
        if (shouldRequestFriendDbConnection) {
            connectingList
                    .append("<")
                    .append(">");
        }

        // billiard
        if (shouldRequestBilliardDbConnection) {
            billiardDbManager2 = new BilliardDbManager2(appDbSetting2);
            connectingList
                    .append("<")
                    .append(BilliardDbManager2.class.getSimpleName())
                    .append(">");
        }

        // player
        if (shouldRequestPlayerDbConnection) {
            connectingList
                    .append("<")
                    .append(">");
        }

        // 연결 여부 설정
        this.shouldRequestUserDbConnection = shouldRequestUserDbConnection;
        this.shouldRequestFriendDbConnection = shouldRequestFriendDbConnection;
        this.shouldRequestBilliardDbConnection = shouldRequestBilliardDbConnection;
        this.shouldRequestPlayerDbConnection = shouldRequestPlayerDbConnection;

    }

    public void closeDb() {
        appDbSetting2.closeOpenHelper();
    }


    // ==================================================== Query Request ====================================================
    public void requestUserQuery(UserQueryRequestListener userQueryRequestListener) {

        if (shouldRequestUserDbConnection) {
//            userQueryRequestListener.requestQuery();
        } else {
            new RuntimeException("userDbManager 연결 요청을 하지 않았습니다.");
        }

    }

    public void requestFriendQuery(FriendQueryRequestListener friendQueryRequestListener) {

        if (shouldRequestFriendDbConnection) {
//            friendQueryRequestListener.requestQuery();
        } else {
            new RuntimeException("friendDbManager 연결 요청을 하지 않았습니다.");
        }

    }

    public void requestBilliardQuery(BilliardQueryRequestListener billiardQueryRequestListener) {

        if (shouldRequestBilliardDbConnection) {
            billiardQueryRequestListener.requestQuery(billiardDbManager2);
        } else {
            new RuntimeException("billiardDbManager 연결 요청을 하지 않았습니다.");
        }

    }

    public void requestPlayerQuery(PlayerQueryRequestListener playerQueryRequestListener) {

        if (shouldRequestPlayerDbConnection) {
//            playerQueryRequestListener.requestQuery();
        } else {
            new RuntimeException("playerDbManager 연결 요청을 하지 않았습니다.");
        }

    }

    public void requestQuery(QueryRequestListener queryRequestListener) {

        // 실행 순서가 user -> friend -> billiard -> player 이므로 주의한다.
        // 순서가 필요할 때는 각각을


        // userDbManger 연결요청을 했을 때만 수행
        if (shouldRequestUserDbConnection) {
//            queryRequestListener.requestUserQuery();
        } else {
            new RuntimeException("userDbManager 연결 요청을 하지 않았습니다.");
        }

        // friendDbManager 연결요청을 했을 때만 수행
        if (shouldRequestFriendDbConnection) {
//            queryRequestListener.requestFriendQuery();
        } else {
            new RuntimeException("friendDbManager 연결 요청을 하지 않았습니다.");
        }

        // billiardDbManager 연결요청을 했을 때만 수행
        if (shouldRequestBilliardDbConnection) {
            queryRequestListener.requestBilliardQuery(billiardDbManager2);
        } else {
            new RuntimeException("billiardDbManager 연결 요청을 하지 않았습니다.");
        }

        // playerDbManager 연결요청을 했을 때만 수행
        if (shouldRequestBilliardDbConnection) {
//            queryRequestListener.requestPlayerQuery();
        } else {
            new RuntimeException("playerDbManager 연결 요청을 하지 않았습니다.");
        }

    }


    // ==================================================== Listener ====================================================
    public interface QueryRequestListener {
        void requestUserQuery(UserDbManager userDbManager);

        void requestFriendQuery(FriendDbManager friendDbManager);

        void requestBilliardQuery(BilliardDbManager2 billiardDbManager);

        void requestPlayerQuery(PlayerDbManager playerDbManager);
    }

    public interface UserQueryRequestListener {
        void requestQuery(UserDbManager userDbManager);
    }

    public interface FriendQueryRequestListener {
        void requestQuery(FriendDbManager friendDbManager);
    }

    public interface BilliardQueryRequestListener {
        void requestQuery(BilliardDbManager2 billiardDbManager2);
    }

    public interface PlayerQueryRequestListener {
        void requestQuery(PlayerDbManager playerDbManager);
    }
}
