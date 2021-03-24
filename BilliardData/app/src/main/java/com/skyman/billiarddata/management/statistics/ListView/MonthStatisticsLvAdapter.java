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
    private ArrayList<MonthStatisticsData> monthStatisticsDataArrayList;

    // constructor
    public MonthStatisticsLvAdapter(ArrayList<MonthStatisticsData> monthStatisticsDataArrayList) {
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

        // monthStatisticsData
        MonthStatisticsData monthStatisticsData = (MonthStatisticsData) getItem(position);

        // widget

        MaterialTextView year = convertView.findViewById(R.id.custom_lv_monthStatistics_year);
        MaterialTextView month = convertView.findViewById(R.id.custom_lv_monthStatistics_month);
        MaterialTextView gameRecord = convertView.findViewById(R.id.custom_lv_monthStatistics_gameRecord);

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

}
