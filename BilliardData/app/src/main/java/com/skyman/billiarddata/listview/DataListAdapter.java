package com.skyman.billiarddata.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.database.billiard.BilliardDataManager;

import java.util.ArrayList;

public class DataListAdapter extends BaseAdapter {

    private ArrayList<DataListItem> dataListItems = new ArrayList<>();

    @Override
    public int getCount() {
        return dataListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dataListItems.get(position);
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
            convertView = inflater.inflate(R.layout.activity_select_item, parent, false);
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
        DataListItem dataListItem = (DataListItem) getItem(position);


        /* 각 위젯에 셋팅된 아이템을 뿌려준다. */
        id.setText(dataListItem.getId() +"");
        date.setText(dataListItem.getDate());

        target_score.setText(dataListItem.getTarget_score());
        speciality.setText(dataListItem.getSpeciality());
        play_time.setText(BilliardDataManager.setFormatToPlayTime(dataListItem.getPaly_time()));

        victoree.setText(dataListItem.getVictoree());
        score.setText(dataListItem.getScore());
        cost.setText(BilliardDataManager.setFormatToCost(dataListItem.getCost()));

        return convertView;
    }

    //
    public void setWidget(TextView id, TextView date, TextView target_score, TextView speciality, TextView play_time, TextView victoree, TextView score, TextView cost){

    }

    // db에서 읽어온 내용을 ArrayList에 추가하기 위한 method
    public void addItem(long id, String date, String target_score, String speciality, String play_time, String victoree, String score, String cost){
        DataListItem dataListItem = new DataListItem();

        /* DataListItem 에 아이템을 setting 한다.*/
        dataListItem.setId(id);
        dataListItem.setDate(date);

        dataListItem.setTarget_score(target_score);
        dataListItem.setSpeciality(speciality);
        dataListItem.setPaly_time(play_time);

        dataListItem.setVictoree(victoree);
        dataListItem.setScore(score);
        dataListItem.setCost(cost);

        /* items 에 dataListItem 을 추가한다.*/
        dataListItems.add(dataListItem);
    }
}
