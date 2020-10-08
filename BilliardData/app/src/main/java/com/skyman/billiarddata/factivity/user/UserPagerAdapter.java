package com.skyman.billiarddata.factivity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserPagerAdapter extends FragmentStatePagerAdapter {

    // instance variable
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private BilliardDBManager billiardDBManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    // constructor
    public UserPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, FriendDbManager friendDbManager, BilliardDBManager billiardDBManager, UserData userData, ArrayList<FriendData> friendDataArrayList) {
        super(fm);
        this.userDbManager = userDbManager;
        this.friendDbManager = friendDbManager;
        this.billiardDBManager = billiardDBManager;
        this.userData = userData;
        this.friendDataArrayList = friendDataArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserInputFragment(userDbManager, friendDbManager, billiardDBManager, userData);
            case 1:
                return new UserInfoFragment(userData, friendDataArrayList);
            case 2:
                return new UserFriendFragment(friendDbManager, userData, friendDataArrayList);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
