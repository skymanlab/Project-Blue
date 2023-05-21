package com.skyman.billiarddata.fragment.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skyman.billiarddata.etc.calendar.SameDateGameAnalysis;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;

public class StatsViewPagerAdapter extends FragmentStateAdapter {

    // instance variable
    private UserData userData;
    private ArrayList<BilliardData> billiardDataList;
    private SameDateGameAnalysis analysis;

    // constructor
    public StatsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserData userData, ArrayList<BilliardData> billiardDataList, SameDateGameAnalysis analysis) {
        super(fragmentActivity);
        this.userData = userData;
        this.billiardDataList = billiardDataList;
        this.analysis = analysis;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return CalendarFragment.newInstance(userData, billiardDataList, analysis.getSameDateGameList());
            case 1:
                return ChartFragment.newInstance(userData, billiardDataList, analysis.getAllGame(), analysis.getSameYearGameList(), analysis.getSameMonthGameList());
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
