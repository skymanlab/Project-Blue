package com.skyman.billiarddata.management.billiard.listview;

import android.widget.ListView;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

public class BilliardLvManager {

    // instance variable : list view adapter 객체 선언
    private BilliardLvAdapter billiardLvAdapter;

    // instance variable : target list view 객체 선언
    private ListView activityTargetListView;

    // constructor
    public BilliardLvManager(ListView activityTargetListView) {
        this.billiardLvAdapter = new BilliardLvAdapter();
        this.activityTargetListView = activityTargetListView;
    }


    /**
     * [method] adapter 를 통해 custom list view layout 과 모든 billiard 데이터와 연결하여 화면에 뿌려준다.
     *
     */
    public void displayListViewOfBilliardData(ArrayList<BilliardData> billiardDataArrayList) {
        DeveloperManager.displayLog("[LvM]_BilliardLvManager", "[setListViewToAllBilliardDat] The method is executing........");

        // [cycle 1] : billiardDataArrayList 의 size 만큼 돌며, billiardLvAdapter 의 addItem method 로
        for (int position = 0; position < billiardDataArrayList.size(); position++) {
            billiardLvAdapter.addItem(
                    billiardDataArrayList.get(position).getCount(),                // 0. count
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

        // [iv/C]ListView : billiardLvAdapter 를 list view 에 연결하기
        activityTargetListView.setAdapter(billiardLvAdapter);
        DeveloperManager.displayLog("[LvM]_BilliardDataManager", "[setListViewToAllBilliardDat] The method is complete.");

    } // End of method [displayListViewOfBilliardData]

}