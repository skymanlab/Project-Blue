package com.skyman.billiarddata.management.billiard.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.data.BilliardDataFormatter;

import java.util.ArrayList;

/**
 * [class] billiard 테이블의 모든 데이터를 billiardDataArrayList 에 추가 하여 custom list view 에 뿌려주기 위한 작업을 하는 Adapter 이다.
 */
public class BilliardLvAdapter extends BaseAdapter {

    // instance variable
    private ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

    @Override
    public int getCount() {
        return billiardDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return billiardDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // [lv/C]Context : context 가져오기
        Context context = parent.getContext();

        // [check 1] : convertView 가 있을 때
        if(convertView == null){

            // [lv/C]LayoutInflater : ????
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // [lv/C]View : custom_lv_billiard_data layout 을 가져오기
            convertView = inflater.inflate(R.layout.custom_lv_billiard_data, parent, false);

        } // [check 1]

        // [lv/C]TextView : count, data, targetScore, speciality, playTime, winner, score, cost mapping
        TextView count = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_count);                            // 0. count
        TextView date = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_date);                              // 1. date
        TextView targetScore = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_target_score);               // 2. target score
        TextView speciality = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_speciality);                  // 3. speciality
        TextView playTime = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_play_time);                     // 4. play time
        TextView winner = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_winner);                          // 5. winner
        TextView score = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_score);                            // 6. score
        TextView cost = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_cost);                              // 7. cost

        // [lv/C]BilliardData : 각 리스트에 뿌려줄 아이템을 받아오는데 BilliardData 재활용
        BilliardData billiardData = (BilliardData) getItem(position);

        // [lv/C]TextView : 매핑된 count, data, targetScore, speciality, playTime, winner, score, cost 값 셋팅
        count.setText(billiardData.getCount() +"");                                                                 // 0. count
        date.setText(billiardData.getDate());                                                                       // 1. date
        targetScore.setText(billiardData.getTargetScore()+"");                                                      // 2. target score
        speciality.setText(billiardData.getSpeciality());                                                           // 3. speciality
        playTime.setText(BilliardDataFormatter.getFormatOfPlayTime(billiardData.getPlayTime()));                    // 4. play time
        winner.setText(billiardData.getWinner());                                                                   // 5. winner
        score.setText(billiardData.getScore());                                                                     // 6. score
        cost.setText(BilliardDataFormatter.getFormatOfCost(billiardData.getCost()));                                // 7. cost

        return convertView;
    }

    /**
     * [method] 매개 변수로 받은 값을 BilliardData 로 만들어 billiardDataArrayList 에 추가한다.
     *
     * @param cost [0] count : primary key
     * @param date [1] 날짜
     * @param targetScore [2] 수지
     * @param speciality [3] 종목
     * @param playTime [4] 게임 시간
     * @param winner [5] 승리자 이름
     * @param score [6] 스코어
     * @param cost [7] 비용
     *
     */
    public void addItem(long count, String date, int targetScore, String speciality, int playTime, String winner, String score, int cost){

        // [lv/C]BilliardData : 매개변수로 받은 데이터를 BilliardData 담는다.
        BilliardData billiardData = new BilliardData();
        billiardData.setCount(count);
        billiardData.setDate(date);
        billiardData.setTargetScore(targetScore);
        billiardData.setSpeciality(speciality);
        billiardData.setPlayTime(playTime);
        billiardData.setWinner(winner);
        billiardData.setScore(score);
        billiardData.setCost(cost);

        // [iv/C]ArrayList<BilliardData> : 위 에서 만든 데이터를 배열에 담는다.
        this.billiardDataArrayList.add(billiardData);

    } // End of method [addItem]


    /**
     * [method] BilliardData 를 매개변수로 받아 billiardDataArrayList 에 추가한다.
     *
     * @param billiardData billiard 데이터가 담긴
     */
    public void addItem(BilliardData billiardData) {

        // [iv/C]ArrayList<BilliardData> : 입력받은 BilliardData 가 배열형태로 담길 객체
        this.billiardDataArrayList.add(billiardData);

    } // End of method [addItem]
}
