package com.skyman.billiarddata.factivity.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.statistics.calendar.CalendarFragment;
import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {

    // instance variable
    private UserDbManager userDbManager;
    private BilliardDBManager billiardDBManager;

    public StatisticsPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, BilliardDBManager billiardDBManager) {
        super(fm);
        this.userDbManager = userDbManager;
        this.billiardDBManager = billiardDBManager;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new CalendarFragment(userDbManager, billiardDBManager);
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
