package com.skyman.billiarddata.factivity.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.user.UserInfo;

public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {

    public StatisticsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        DeveloperManager.displayLog("[PaA] StatisticsPagerAdapter", "[constructor] 생성자 확인");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        DeveloperManager.displayLog("[PaA] StatisticsPagerAdapter", "[getItem] The method is executiong........");

        switch (position){
            case 0:
                return new CalendarFragment();
            case 1:
                return new ChartFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
