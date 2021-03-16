package com.skyman.billiarddata.fragment.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserPagerAdapter extends FragmentStatePagerAdapter {

    // instance variable
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private BilliardDbManager billiardDbManager;
    private PlayerDbManager playerDbManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    // constructor
    public UserPagerAdapter(@NonNull FragmentManager fm, UserDbManager userDbManager, FriendDbManager friendDbManager, BilliardDbManager billiardDBManager, PlayerDbManager playerDbManager, UserData userData, ArrayList<FriendData> friendDataArrayList) {
        super(fm);
        this.userDbManager = userDbManager;
        this.friendDbManager = friendDbManager;
        this.billiardDbManager = billiardDBManager;
        this.playerDbManager = playerDbManager;
        this.userData = userData;
        this.friendDataArrayList = friendDataArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserInputFragment(this.userDbManager, this.friendDbManager, this.billiardDbManager, this.playerDbManager, this.userData);
            case 1:
                return new UserInfoFragment(this.billiardDbManager, this.userData, this.friendDataArrayList);
            case 2:
                return new UserFriendFragment(this.friendDbManager, this.billiardDbManager, this.userData, this.friendDataArrayList);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
