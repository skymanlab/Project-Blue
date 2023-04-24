package com.skyman.billiarddata.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.textview.MaterialTextView;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.etc.DataFormatUtil;
import com.skyman.billiarddata.etc.statistics.MonthStatistics;

import java.util.ArrayList;

public class MonthStatisticsLvAdapter extends BaseAdapter {

    // instance variable
    private ArrayList<MonthStatistics> monthStatisticsArrayList;

    // constructor
    public MonthStatisticsLvAdapter(ArrayList<MonthStatistics> monthStatisticsArrayList) {
        this.monthStatisticsArrayList = monthStatisticsArrayList;
    }

    @Override
    public int getCount() {
        return monthStatisticsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return monthStatisticsArrayList.get(position);
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
        MonthStatistics monthStatistics = (MonthStatistics) getItem(position);

        // widget

        MaterialTextView year = convertView.findViewById(R.id.clv_monthStatistics_year);
        MaterialTextView month = convertView.findViewById(R.id.clv_monthStatistics_month);
        MaterialTextView gameRecord = convertView.findViewById(R.id.clv_monthStatistics_gameRecord);

        // setup widget
        year.setText(monthStatistics.getYear() + "년");
        month.setText(monthStatistics.getMonth() + "월");
        gameRecord.setText(
                DataFormatUtil.formatOfGameRecord(
                        monthStatistics.getWinCount(),
                        monthStatistics.getLossCount()
                )
        );

        return convertView;
    }

}
