package com.skyman.billiarddata.fragment.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skyman.billiarddata.table.user.data.UserData;

public class UserViewPagerAdapter extends FragmentStateAdapter {

    // instance variable
    private UserData userData;

    public UserViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, UserData userData) {
        super(fragmentActivity);
        this.userData = userData;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                UserInputFragment inputFragment = UserInputFragment.newInstance(userData);
                return inputFragment;
            case 1:
                UserInfoFragment infoFragment = UserInfoFragment.newInstance(userData);
                return infoFragment;
            case 2:
                UserFriendFragment friendFragment = UserFriendFragment.newInstance(userData);
                return friendFragment;
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
