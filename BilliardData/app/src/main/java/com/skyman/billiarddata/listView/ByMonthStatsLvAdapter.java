package com.skyman.billiarddata.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.etc.calendar.SameDateGame;

import java.util.ArrayList;
import java.util.List;

public class ByMonthStatsLvAdapter extends BaseAdapter {

    // instance variable
    private ArrayList<SameDateGame> sameMonthGameList;

    // constructor
    public ByMonthStatsLvAdapter(ArrayList<SameDateGame> sameMonthGameList) {
        this.sameMonthGameList = sameMonthGameList;
    }

    @Override
    public int getCount() {
        return sameMonthGameList.size();
    }

    @Override
    public Object getItem(int position) {
        return sameMonthGameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // inflater
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // view
        convertView = inflater.inflate(R.layout.custom_lv_by_month_stats, parent, false);

        // sameDateGame
        SameDateGame sameMonthGame = (SameDateGame) getItem(position);

        // widget
        MaterialCardView contentWrapper = convertView.findViewById(R.id.clv_byMonthStats_contentWrapper);
        MaterialTextView year = convertView.findViewById(R.id.clv_byMonthStats_year);
        MaterialTextView month = convertView.findViewById(R.id.clv_byMonthStats_month);
        MaterialTextView record = convertView.findViewById(R.id.clv_byMonthStats_record);

        // setup widget
        year.setText(sameMonthGame.getDate().getYear() + "년");
        month.setText(sameMonthGame.getDate().getMonth() + "월");
        record.setText(
                sameMonthGame.getRecord().toString()
        );

        // 해당 월을 클릭 했을 때 sameDateGame 에 대한 통계 자료 분석 시작과 결과를 Dialog로 표시
        contentWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );
        return convertView;
    }

}
