package com.skyman.billiarddata.management.friend.listview;

import android.hardware.SensorDirectChannel;
import android.widget.ListView;

import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;

import java.util.ArrayList;

public class FriendLvManager {

    // instance variable
    private FriendLvAdapter friendLvAdapter;
    private ListView targetListView;
    private FriendDbManager friendDbManager;

    // constructor
    public FriendLvManager(ListView targetListView, FriendDbManager friendDbManager) {
        this.friendLvAdapter = new FriendLvAdapter(friendDbManager);
        this.targetListView = targetListView;
        this.friendDbManager = friendDbManager;
    }


    /**
     * [method] FriendDbManager 를 생성 및 초기화 한다.
     *
     */
    public void createLvAdapter () {

        // [iv/C]FriendLvManager : adapter 생성 및 초기화
        this.friendLvAdapter = new FriendLvAdapter(this.friendDbManager);

    } // End of method [createLvAdapter]


    /**
     * [method]  adapter 를 통해 custom list view layout 과 모든 friend 데이터와 연결하여 화면에 뿌려준다.
     */
    public void setListViewOfFriendData(ArrayList<FriendData> friendDataArrayList) {

        // [cycle 1] : friendDataArrayList 의 size 만큼 돌며, friendLvAdapter 의 addItem method 로
        for (int position = 0; position < friendDataArrayList.size(); position++) {

            // [iv/C]FriendLvAdapter : 매개변수로 받은 값을 adapter 의 friendDataArrayList 와 mapping 하기
            this.friendLvAdapter.addItem(
//                    friendDataArrayList.get(position).getId(),
//                    friendDataArrayList.get(position).getUserId(),
//                    friendDataArrayList.get(position).getName(),
//                    friendDataArrayList.get(position).getGameRecordWin(),
//                    friendDataArrayList.get(position).getGameRecordLoss(),
//                    friendDataArrayList.get(position).getRecentPlayDate(),
//                    friendDataArrayList.get(position).getTotalPlayTime(),
//                    friendDataArrayList.get(position).getTotalCost()
                    friendDataArrayList.get(position)
            );

            // ListView : activityTargetListView 에 연결하기
            this.targetListView.setAdapter(this.friendLvAdapter);
        }
    }

}
