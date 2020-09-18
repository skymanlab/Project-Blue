package com.skyman.billiarddata.factivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class UserPagerAdapter extends FragmentStatePagerAdapter {
    public UserPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserInputData();
            case 1:
                return new UserInfo();
            case 2:
                return new UserFriend();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
