package com.skyman.billiarddata.listview.billiard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.database.billiard.BilliardDataManager;

import java.util.ArrayList;

public class BilliardDataAdapter extends BaseAdapter {

    private ArrayList<BilliardDataItem> billiardDataItemArrayList = new ArrayList<>();

    @Override
    public int getCount() {
        return billiardDataItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return billiardDataItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /* 'activity_select_item' Layout을 inflate 하여 converView 참조 획득*/
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_select_item, parent, false);
        }

        /* 'activity_select_item' 에 정의된 위젯에 대한 참조 획득
        * 1. id
        * 2. date
        * 3. target score
        * 4. play time
        * 5. victoree
        * 6. score
        * 7. cost
        * */
        TextView id = (TextView) convertView.findViewById(R.id.selectitem_id);
        TextView date = (TextView) convertView.findViewById(R.id.selectitem_date);

        TextView target_score = (TextView) convertView.findViewById(R.id.selectitem_target_score);
        TextView speciality = (TextView) convertView.findViewById(R.id.selectitem_speciality);
        TextView play_time = (TextView) convertView.findViewById(R.id.selectitem_play_time);

        TextView victoree = (TextView) convertView.findViewById(R.id.selectitem_victoree);
        TextView score = (TextView) convertView.findViewById(R.id.selectitem_score);
        TextView cost = (TextView) convertView.findViewById(R.id.selectitem_cost);


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 DataListItem 재활용 */
        BilliardDataItem billiardDataItem = (BilliardDataItem) getItem(position);


        /* 각 위젯에 셋팅된 아이템을 뿌려준다. */
        id.setText(billiardDataItem.getId() +"");
        date.setText(billiardDataItem.getDate());

        target_score.setText(billiardDataItem.getTarget_score());
        speciality.setText(billiardDataItem.getSpeciality());
        play_time.setText(BilliardDataManager.setFormatToPlayTime(billiardDataItem.getPaly_time()));

        victoree.setText(billiardDataItem.getVictoree());
        score.setText(billiardDataItem.getScore());
        cost.setText(BilliardDataManager.setFormatToCost(billiardDataItem.getCost()));

        return convertView;
    }

    //
    public void setWidget(TextView id, TextView date, TextView target_score, TextView speciality, TextView play_time, TextView victoree, TextView score, TextView cost){

    }

    // db에서 읽어온 내용을 ArrayList에 추가하기 위한 method
    public void addItem(long id, String date, String target_score, String speciality, String play_time, String victoree, String score, String cost){
        BilliardDataItem billiardDataItem = new BilliardDataItem();

        /* DataListItem 에 아이템을 setting 한다.*/
        billiardDataItem.setId(id);
        billiardDataItem.setDate(date);

        billiardDataItem.setTarget_score(target_score);
        billiardDataItem.setSpeciality(speciality);
        billiardDataItem.setPaly_time(play_time);

        billiardDataItem.setVictoree(victoree);
        billiardDataItem.setScore(score);
        billiardDataItem.setCost(cost);

        /* items 에 dataListItem 을 추가한다.*/
        billiardDataItemArrayList.add(billiardDataItem);
    }
}
