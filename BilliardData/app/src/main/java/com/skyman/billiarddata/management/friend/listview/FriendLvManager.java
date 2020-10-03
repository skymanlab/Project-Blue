package com.skyman.billiarddata.management.friend.listview;

import android.widget.ListView;

import com.skyman.billiarddata.management.friend.data.FriendData;

import java.util.ArrayList;

public class FriendLvManager {

    // variable : ListView 와 연결하기 위한 Adapter
    private FriendLvAdapter friendLvAdapter;

    // variable : 내용을 뿌려주기 위한 ListView
    private ListView activityTargetListView;

    // constructor
    public FriendLvManager(ListView activityTargetListView){
        this.friendLvAdapter = new FriendLvAdapter();
        this.activityTargetListView = activityTargetListView;
    }

    // method : friendLvAdapter 의 friendDataArrayList 에 내용 입력하기
    public void setListViewToAllFriendData(ArrayList<FriendData> friendDataArrayList){

        // cycle : friendDataArrayList 의 내용을 friendLvAdapter 의 friendDataArrayList 에 입력
        for(int position=0; position<friendDataArrayList.size(); position++){
            // FriendLvAdapter : 내용 추가
            friendLvAdapter.addItem(
                    friendDataArrayList.get(position).getId(),
                    friendDataArrayList.get(position).getUserId(),
                    friendDataArrayList.get(position).getName(),
                    friendDataArrayList.get(position).getGameRecordWin(),
                    friendDataArrayList.get(position).getGameRecordLoss(),
                    friendDataArrayList.get(position).getRecentPlayDate(),
                    friendDataArrayList.get(position).getTotalPlayTime(),
                    friendDataArrayList.get(position).getTotalCost()
            );

            // ListView : activityTargetListView 에 연결하기
            activityTargetListView.setAdapter(friendLvAdapter);
        }
    }

}
