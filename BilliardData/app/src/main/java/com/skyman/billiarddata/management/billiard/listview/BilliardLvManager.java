package com.skyman.billiarddata.management.billiard.listview;

import android.widget.ListView;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class BilliardLvManager {

    // constant
    private final String CLASS_NAME_LOG = "[LvM]_BilliardLvAdapter";

    // instance variable
    private BilliardLvAdapter billiardLvAdapter;
    private ListView targetListView;

    // instance variable
    private BilliardDbManager billiardDbManager;
    private UserDbManager userDbManager;
    private PlayerDbManager playerDbManager;
    private FriendDbManager friendDbManager;

    // constructor
    public BilliardLvManager(ListView targetListView, BilliardDbManager billiardDbManager, UserDbManager userDbManager, PlayerDbManager playerDbManager, FriendDbManager friendDbManager) {
        this.billiardLvAdapter = new BilliardLvAdapter(billiardDbManager, userDbManager, playerDbManager, friendDbManager, targetListView);
        this.targetListView = targetListView;
    }


    /**
     * [method] BilliardData 를 add 하기
     *
     */
    public void addData(ArrayList<BilliardData> billiardDataArrayList, UserData userData) {

        // [lv/C]String : method name constant
        final String METHOD_NAME= "[addData] ";

        // [iv/C]BilliardLvArrayList : 해당 billiardDataArrayList 을 넣기
        this.billiardLvAdapter.setBilliardDataArrayList(billiardDataArrayList);

        // [iv/C]BilliardLvArrayList : 해당 userData 을 넣기
        this.billiardLvAdapter.setUserData(userData);

    } // End of method [addBilliardData]


    /**
     * [method] targetListView 에 billiardLvAdapter 를 연결하기
     *
     */
    public void setListViewToAdapter() {
        this.targetListView.setAdapter(this.billiardLvAdapter);
    }
}