package com.skyman.billiarddata.management.billiard.listview;

import android.widget.ListView;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

public class BilliardLvManager {

    // value : list view adapter 객체 선언
    private BilliardLvAdapter billiardLvAdapter;

    // value : target list view 객체 선언
    private ListView activityTargetListView;

    public BilliardLvManager(ListView activityTargetListView) {
        this.billiardLvAdapter = new BilliardLvAdapter();
        this.activityTargetListView = activityTargetListView;
    }

    /* method : list view adapter, 받아온 내용을 adapter를 통해 dataList 에 뿌려 준다.*/
    public void setListViewToAllBilliardData(ArrayList<BilliardData> billiardDataArrayList) {

        DeveloperManager.displayLog("BilliardDataManager", "setListViewToBilliardData 실행");

        // cycle : Array List add, dataListAdapter 객체의 dataListItems 객체에 DB에서 읽어온 내용 넣기
        for (int position = 0; position < billiardDataArrayList.size(); position++) {
            billiardLvAdapter.addItem(
                    billiardDataArrayList.get(position).getId(),                // 0. id
                    billiardDataArrayList.get(position).getDate(),              // 1. date
                    billiardDataArrayList.get(position).getTargetScore(),       // 3. target score
                    billiardDataArrayList.get(position).getSpeciality(),        // 4. speciality
                    billiardDataArrayList.get(position).getPlayTime(),          // 5. play time
                    billiardDataArrayList.get(position).getWinner(),            // 6. winner
                    billiardDataArrayList.get(position).getScore(),             // 7. score
                    billiardDataArrayList.get(position).getCost()               // 8. cost
            );
        }
        // set : set adapter, 읽어온 내용을 다 넣고, adapter를 dataList(ListView)에 연결 시키기
        activityTargetListView.setAdapter(billiardLvAdapter);
        DeveloperManager.displayLog("BilliardDataManager", "setListViewToBilliardData 실행 완료");
    }
}