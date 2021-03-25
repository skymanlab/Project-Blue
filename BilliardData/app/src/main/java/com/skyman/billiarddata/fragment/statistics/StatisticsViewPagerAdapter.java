package com.skyman.billiarddata.fragment.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.etc.calendar.SameDate;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;

public class StatisticsViewPagerAdapter extends FragmentStateAdapter {

    // instance variable
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDate sameDate;

    // constructor
    public StatisticsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserData userData, ArrayList<BilliardData> billiardDataArrayList, SameDate sameDate) {
        super(fragmentActivity);
        this.userData = userData;
        this.billiardDataArrayList = billiardDataArrayList;
        this.sameDate = sameDate;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return CalendarFragment.newInstance(userData, billiardDataArrayList, sameDate);
            case 1:
                return ChartFragment.newInstance(userData, billiardDataArrayList, sameDate);
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
