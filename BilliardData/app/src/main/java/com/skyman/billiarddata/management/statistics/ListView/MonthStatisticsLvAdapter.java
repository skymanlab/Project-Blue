package com.skyman.billiarddata.management.statistics.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.textview.MaterialTextView;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;
import com.skyman.billiarddata.management.statistics.MonthStatisticsData;

import java.util.ArrayList;

public class MonthStatisticsLvAdapter extends BaseAdapter {

    // instance variable
    private ArrayList<MonthStatisticsData> monthStatisticsDataArrayList = new ArrayList<>();

    // constructor
    public MonthStatisticsLvAdapter() {
    }

    // setter
    public void setMonthStatisticsDataArrayList(ArrayList<MonthStatisticsData> monthStatisticsDataArrayList) {
        this.monthStatisticsDataArrayList = monthStatisticsDataArrayList;
    }

    @Override
    public int getCount() {
        return monthStatisticsDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return monthStatisticsDataArrayList.get(position);
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
        convertView = inflater.inflate(R.layout.custom_lv_month_statistics, parent, false);

        // widget
        MaterialTextView year = convertView.findViewById(R.id.c_lv_month_statistics_year);
        MaterialTextView month = convertView.findViewById(R.id.c_lv_month_statistics_month);
        MaterialTextView gameRecord = convertView.findViewById(R.id.c_lv_month_statistics_game_record);

        // monthStatisticsData
        MonthStatisticsData monthStatisticsData = (MonthStatisticsData) getItem(position);

        // setup widget
        year.setText(monthStatisticsData.getYear() + "년");
        month.setText(monthStatisticsData.getMonth() + "월");
        gameRecord.setText(
                ProjectBlueDataFormatter.getFormatOfGameRecord(
                        monthStatisticsData.getWinCount(),
                        monthStatisticsData.getLossCount()
                )
        );

        return convertView;
    }

    private String convertToGameRecordFormat(int winCount, int lossCount) {
        StringBuilder gameRecord = new StringBuilder()
                .append(winCount + lossCount)
                .append("전 ")
                .append(winCount)
                .append("승 ")
                .append(lossCount)
                .append("패");

        return gameRecord.toString();
    }

}
