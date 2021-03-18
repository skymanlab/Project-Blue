package com.skyman.billiarddata.management;

import android.app.Activity;

import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class SectionManager {

    // instance variable
    private Activity activity;

    // instance variable
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private BilliardDbManager billiardDbManager;
    private PlayerDbManager playerDbManager;
    private boolean shouldRequestUserDbConnection;
    private boolean shouldRequestFriendDbConnection;
    private boolean shouldRequestBilliardDbConnection;
    private boolean shouldRequestPlayerDbConnection;

    // constructor
    public SectionManager(Activity activity) {
        this.activity = activity;
    }

    // getter
    public UserDbManager getUserDbManager() {
        return userDbManager;
    }

    public FriendDbManager getFriendDbManager() {
        return friendDbManager;
    }

    public BilliardDbManager getBilliardDbManager() {
        return billiardDbManager;
    }

    public PlayerDbManager getPlayerDbManager() {
        return playerDbManager;
    }

    // db connect
    public void connectDb(boolean shouldRequestUserDbConnection, boolean shouldRequestFriendDbConnection, boolean shouldRequestBilliardDbConnection, boolean shouldRequestPlayerDbConnection) {

        // userDbManager
        if (shouldRequestUserDbConnection) {
            this.userDbManager = new UserDbManager(activity);
            this.userDbManager.initDb();
        }

        // friendDbManager
        if (shouldRequestFriendDbConnection) {
            this.friendDbManager =new FriendDbManager(activity);
            this.friendDbManager.initDb();
        }

        // billiardDbManager
        if (shouldRequestBilliardDbConnection ){
            this.billiardDbManager = new BilliardDbManager(activity);
            this.billiardDbManager.initDb();
        }

        // playerDbManager
        if (shouldRequestPlayerDbConnection) {
            this.playerDbManager = new PlayerDbManager(activity);
            this.playerDbManager.initDb();
        }

        // 연결 요청 여부
        this.shouldRequestUserDbConnection = shouldRequestUserDbConnection;
        this.shouldRequestFriendDbConnection = shouldRequestFriendDbConnection;
        this.shouldRequestBilliardDbConnection = shouldRequestBilliardDbConnection;
        this.shouldRequestPlayerDbConnection = shouldRequestPlayerDbConnection;

    }

    // db close
    public void closeDb() {

        // userDbManager 객체가 있고, 연결을 요청하였으면 종료를 실행한다.
        if (userDbManager != null && shouldRequestUserDbConnection) {
            userDbManager.closeDb();
        }

        // friendDbManager 객체가 있고, 연결을 요청하였으면 종료를 실행한다.
        if (friendDbManager != null && shouldRequestFriendDbConnection) {
            friendDbManager.closeDb();
        }

        // billiardDbManager 객체가 있고, 연결을 요청하였으면 종료를 실행한다.
        if (billiardDbManager != null && shouldRequestBilliardDbConnection) {
            billiardDbManager.closeDb();
        }

        // playerDbManager 객체가 있고, 연결을 요청하였으면 종료를 실행한다.
        if (playerDbManager != null && shouldRequestPlayerDbConnection) {
            playerDbManager.closeDb();
        }
    }

    public void requestDbQuery(DbQueryRequestListener dbQueryRequestListener){

        // 실행 순서가 user -> friend -> billiard -> player 이므로 주의한다.
        
        // userDbManger 연결요청을 했을 때만 수행
        if (shouldRequestUserDbConnection) {
            dbQueryRequestListener.requestUserDb(userDbManager);
        } else {
            new RuntimeException("userDbManager 연결 요청을 하지 않았습니다.");
        }

        // friendDbManager 연결요청을 했을 때만 수행
        if (shouldRequestFriendDbConnection) {
            dbQueryRequestListener.requestFriendDb(friendDbManager);
        }else {
            new RuntimeException("friendDbManager 연결 요청을 하지 않았습니다.");
        }

        // billiardDbManager 연결요청을 했을 때만 수행
        if (shouldRequestBilliardDbConnection) {
            dbQueryRequestListener.requestBilliardDb(billiardDbManager);
        }else {
            new RuntimeException("billiardDbManager 연결 요청을 하지 않았습니다.");
        }

        // playerDbManager 연결요청을 했을 때만 수행
        if (shouldRequestBilliardDbConnection) {
            dbQueryRequestListener.requestPlayerDb(playerDbManager);
        }else {
            new RuntimeException("playerDbManager 연결 요청을 하지 않았습니다.");
        }

    }


    /**
     * interface : 각가의 DbManager 에게 쿼리를 요청하는 리스너이다.
     */
    public interface DbQueryRequestListener{
        void requestUserDb(UserDbManager userDbManager);
        void requestFriendDb(FriendDbManager friendDbManager);
        void requestBilliardDb(BilliardDbManager billiardDbManager);
        void requestPlayerDb(PlayerDbManager playerDbManager);

    }

    public interface Initializable {
        void initSectionManager();
        void connectWidget();
        void initWidget();
    }
}
