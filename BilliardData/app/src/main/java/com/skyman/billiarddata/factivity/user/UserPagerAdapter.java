package com.skyman.billiarddata.factivity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserPagerAdapter extends FragmentStatePagerAdapter {

    // UserData : UserManager 로 부터 받은 UserData 객체를 담을 객체 선언\
    private UserDbManager userDbManager;
    private UserData userData;
    private FriendDbManager friendDbManager;
    private ArrayList<FriendData> friendDataArrayList;

    // constructor
    public UserPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, UserData userData, FriendDbManager friendDbManager, ArrayList<FriendData> friendDataArrayList) {
        super(fm);
        this.userDbManager = userDbManager;
        this.userData = userData;
        this.friendDbManager = friendDbManager;
        this.friendDataArrayList = friendDataArrayList;
    }

    public void setTest(String test) {
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserInputFragment(userDbManager, userData, friendDbManager, friendDataArrayList);
            case 1:
                return new UserInfoFragment(userDbManager, userData, friendDbManager, friendDataArrayList);
            case 2:
                return new UserFriendFragment(userDbManager, userData, friendDbManager);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
