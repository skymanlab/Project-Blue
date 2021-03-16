package com.skyman.billiarddata.fragment.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.statistics.SameDateChecker;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {

    // instance variable
    private UserDbManager userDbManager;
    private BilliardDbManager billiardDbManager;
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDateChecker sameDateChecker;

    public StatisticsPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, BilliardDbManager billiardDbManager, UserData userData, ArrayList<BilliardData> billiardDataArrayList, SameDateChecker sameDateChecker) {
        super(fm);
        this.userDbManager = userDbManager;
        this.billiardDbManager = billiardDbManager;
        this.userData = userData;
        this.billiardDataArrayList = billiardDataArrayList;
        this.sameDateChecker = sameDateChecker;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new CalendarFragment(userData, billiardDataArrayList, sameDateChecker);
            case 1:
                return ChartFragment.newInstance(userData, billiardDataArrayList, sameDateChecker);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
