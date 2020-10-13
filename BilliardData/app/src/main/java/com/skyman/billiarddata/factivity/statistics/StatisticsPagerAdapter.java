package com.skyman.billiarddata.factivity.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.calendar.SameDateChecker;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {

    // instance variable
    private UserDbManager userDbManager;
    private BilliardDBManager billiardDBManager;
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDateChecker sameDateChecker;

    public StatisticsPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, BilliardDBManager billiardDBManager, UserData userData, ArrayList<BilliardData> billiardDataArrayList, SameDateChecker sameDateChecker) {
        super(fm);
        this.userDbManager = userDbManager;
        this.billiardDBManager = billiardDBManager;
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
