package com.skyman.billiarddata.management.projectblue.database;

import android.content.Context;

import com.skyman.billiarddata.management.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.management.friend.database.FriendDbManager2;
import com.skyman.billiarddata.management.player.database.PlayerDbManager2;
import com.skyman.billiarddata.management.user.database.UserDbManager2;

public class AppDbManager {

    // instance variable
    private Context context;

    // instance variable
    private AppDbSetting2 appDbSetting2;

    // instance variable
    private UserDbManager2 userDbManager2;
    private FriendDbManager2 friendDbManager2;
    private BilliardDbManager2 billiardDbManager2;
    private PlayerDbManager2 playerDbManager2;

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
            userDbManager2 = new UserDbManager2(appDbSetting2);
            connectingList
                    .append("<")
                    .append(UserDbManager2.class.getSimpleName())
                    .append(">");
        }

        // friend
        if (shouldRequestFriendDbConnection) {
            friendDbManager2 = new FriendDbManager2(appDbSetting2);
            connectingList
                    .append("<")
                    .append(FriendDbManager2.class.getSimpleName())
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
            playerDbManager2 = new PlayerDbManager2(appDbSetting2);
            connectingList
                    .append("<")
                    .append(PlayerDbManager2.class.getSimpleName())
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
            userQueryRequestListener.requestQuery(userDbManager2);
        } else {
            new RuntimeException("userDbManager 연결 요청을 하지 않았습니다.");
        }

    }

    public void requestFriendQuery(FriendQueryRequestListener friendQueryRequestListener) {

        if (shouldRequestFriendDbConnection) {
            friendQueryRequestListener.requestQuery(friendDbManager2);
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
            playerQueryRequestListener.requestQuery(playerDbManager2);
        } else {
            new RuntimeException("playerDbManager 연결 요청을 하지 않았습니다.");
        }

    }

    public void requestQuery(QueryRequestListener queryRequestListener) {

        // 실행 순서가 user -> friend -> billiard -> player 이므로 주의한다.
        // 순서가 필요할 때는 각각을


        // userDbManger 연결요청을 했을 때만 수행
        if (shouldRequestUserDbConnection) {
            queryRequestListener.requestUserQuery(userDbManager2);
        } else {
            new RuntimeException("userDbManager 연결 요청을 하지 않았습니다.");
        }

        // friendDbManager 연결요청을 했을 때만 수행
        if (shouldRequestFriendDbConnection) {
            queryRequestListener.requestFriendQuery(friendDbManager2);
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
            queryRequestListener.requestPlayerQuery(playerDbManager2);
        } else {
            new RuntimeException("playerDbManager 연결 요청을 하지 않았습니다.");
        }

    }


    // ==================================================== Listener ====================================================
    public interface QueryRequestListener {
        void requestUserQuery(UserDbManager2 userDbManager2);

        void requestFriendQuery(FriendDbManager2 friendDbManager2);

        void requestBilliardQuery(BilliardDbManager2 billiardDbManager2);

        void requestPlayerQuery(PlayerDbManager2 playerDbManager2);
    }

    public interface UserQueryRequestListener {
        void requestQuery(UserDbManager2 userDbManager2);
    }

    public interface FriendQueryRequestListener {
        void requestQuery(FriendDbManager2 friendDbManager2);
    }

    public interface BilliardQueryRequestListener {
        void requestQuery(BilliardDbManager2 billiardDbManager2);
    }

    public interface PlayerQueryRequestListener {
        void requestQuery(PlayerDbManager2 playerDbManager2);
    }
}
