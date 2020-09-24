package com.skyman.billiarddata.factivity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class UserPagerAdapter extends FragmentStatePagerAdapter {

    // UserData : UserManager 로 부터 받은 UserData 객체를 담을 객체 선언\
    private UserDbManager userDbManager;
    private UserData userData;
    private ViewPager userPager;

    // constructor
    public UserPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, UserData userData, ViewPager userPager) {
        super(fm);
        this.userDbManager = userDbManager;
        this.userData = userData;
        this.userPager = userPager;
    }

    public void setTest(String test) {
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserInput(userDbManager, userData, userPager);
            case 1:
                return new UserInfo(userData, userPager);
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
