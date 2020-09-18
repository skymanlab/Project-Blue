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

    private ArrayList<DataListItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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

        /* 'activity_select_item' 에 정의된 위젯에 대한 참조 획득*/
        TextView id = (TextView) convertView.findViewById(R.id.selectitem_id);
        TextView date = (TextView) convertView.findViewById(R.id.selectitem_date);
        TextView victoree = (TextView) convertView.findViewById(R.id.selectitem_victoree);
        TextView score = (TextView) convertView.findViewById(R.id.selectitem_score);
        TextView cost = (TextView) convertView.findViewById(R.id.selectitem_cost);
        TextView play_time = (TextView) convertView.findViewById(R.id.selectitem_play_time);


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 DataListItem 재활용 */
        DataListItem dataListIte = (DataListItem) getItem(position);

        /* 각 위젯에 셋팅된 아이템을 뿌려준다. */
        id.setText(dataListIte.getId() +"");
        date.setText(dataListIte.getDate());
        victoree.setText(dataListIte.getVictoree());
        score.setText(dataListIte.getScore());
        cost.setText(BilliardDataManager.setFormatToCost(dataListIte.getCost()));
        play_time.setText(BilliardDataManager.setFormatToPlayTime(dataListIte.getPaly_time()));

        return convertView;
    }

    public void addItem(long id, String date, String victoree, String score, String cost, String play_time){
        DataListItem dataListItem = new DataListItem();

        /* DataListItem 에 아이템을 setting 한다.*/
        dataListItem.setId(id);
        dataListItem.setDate(date);
        dataListItem.setVictoree(victoree);
        dataListItem.setScore(score);
        dataListItem.setCost(cost);
        dataListItem.setPaly_time(play_time);

        /* items 에 dataListItem 을 추가한다.*/
        items.add(dataListItem);
    }
}
