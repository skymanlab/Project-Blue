package com.skyman.billiarddata.fragment.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.statistics.SameDateChecker;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class StatisticsViewPagerAdapter extends FragmentStateAdapter {

    // instance variable
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDateChecker sameDateChecker;

    // constructor
    public StatisticsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserData userData, ArrayList<BilliardData> billiardDataArrayList, SameDateChecker sameDateChecker) {
        super(fragmentActivity);
        this.userData = userData;
        this.billiardDataArrayList = billiardDataArrayList;
        this.sameDateChecker = sameDateChecker;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return CalendarFragment.newInstance(userData, billiardDataArrayList, sameDateChecker);
            case 1:
                return ChartFragment.newInstance(userData, billiardDataArrayList, sameDateChecker);
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
