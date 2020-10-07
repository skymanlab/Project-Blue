package com.skyman.billiarddata.management.billiard.listview;

import android.widget.ListView;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;

import java.util.ArrayList;

public class BilliardLvManager {

    // instance variable
    private BilliardLvAdapter billiardLvAdapter;
    private ListView targetListView;

    // constructor
    public BilliardLvManager(ListView targetListView) {
        this.billiardLvAdapter = new BilliardLvAdapter();
        this.targetListView = targetListView;
    }


    /**
     * [method] adapter 를 통해 custom list view layout 과 모든 billiard 데이터와 연결하여 화면에 뿌려준다.
     */
    public void setListViewOfBilliardData(ArrayList<BilliardData> billiardDataArrayList) {
        DeveloperManager.displayLog("[LvM]_BilliardLvManager", "[setListViewToAllBilliardDat] The method is executing........");

        // [cycle 1] : billiardDataArrayList 의 size 만큼 돌며, billiardLvAdapter 의 addItem method 로
        for (int position = 0; position < billiardDataArrayList.size(); position++) {

            // [iv/C]BilliardLvAdapter : 매개변수로 받은 값을 adapter 의 billiardDataArrayList 와 mapping 하기
            this.billiardLvAdapter.addItem(
//                    billiardDataArrayList.get(position).getCount(),                // 0. count
//                    billiardDataArrayList.get(position).getDate(),              // 1. date
//                    billiardDataArrayList.get(position).getTargetScore(),       // 3. target score
//                    billiardDataArrayList.get(position).getSpeciality(),        // 4. speciality
//                    billiardDataArrayList.get(position).getPlayTime(),          // 5. play time
//                    billiardDataArrayList.get(position).getWinner(),            // 6. winner
//                    billiardDataArrayList.get(position).getScore(),             // 7. score
//                    billiardDataArrayList.get(position).getCost()               // 8. cost
                    billiardDataArrayList.get(position)
            );
        }

        // [iv/C]ListView : billiardLvAdapter 를 list view 에 연결하기
        this.targetListView.setAdapter(this.billiardLvAdapter);
        DeveloperManager.displayLog("[LvM]_BilliardDataManager", "[setListViewToAllBilliardDat] The method is complete.");

    } // End of method [setListViewOfBilliardData]

}