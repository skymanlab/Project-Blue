package com.skyman.billiarddata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.user.UserPagerAdapter;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserManagerActivity extends AppCompatActivity {

    // value : UserDbManager 객체 선언
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;

    // value : view pager 객체 선언
    private ViewPager userTabPager;

    // value : tab layout 객체 선언
    private TabLayout tabBar;

    // value : activity widget 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        // UserDbManager : userDbManager setting
        userDbManager = new UserDbManager(this);

        // UserData :  userData setting - userDbmanager 의 load_content method 를 통해 내용 가져오기
        UserData userData = null;

        // FriendDbManager :
        friendDbManager = new FriendDbManager(this);

        // FriendData : 최근 게임한 플레이어 정보
        FriendData friendData = null;

        // ArrayList<FriendData> : 친구 목록 전부
        ArrayList<FriendData> friendDataArrayList = null;

        if (userDbManager != null) {
            // userDbManager : userData 를
            userDbManager.init_db();
            userData = userDbManager.load_content();

            // friendDbManager : friendData 를
            friendDbManager.init_db();
//            friendData = friendDbManager.l
            friendData = friendDbManager.load_contents(1);
            friendDataArrayList  = friendDbManager.load_contents();
        } else {
            DeveloperManager.displayLog("UserManagerActivity", "userDbManager 가 생성되지 않았습니다.");
        }

        // DeveloperManager
        if(friendData != null){
            DeveloperManager.displayLog("UserManagerActivity", "friendData 가 있습니다.");
            DeveloperManager.displayToFriendData("UserManagerActivity", friendData);
        } else {
            DeveloperManager.displayLog("UserManagerActivity", "friendData 가 xxxxxxx");

        }

        // DeveloperManager
        if(userData != null) {
            DeveloperManager.displayLog("UserManagerActivity", "userData is not null.");
        } else {
            DeveloperManager.displayLog("UserManagerActivity", "userData is null.");
        }

        // TabLayout : tab setting
        tabBar = (TabLayout) findViewById(R.id.user_manager_tl_tab_bar);
        tabBar.addTab(tabBar.newTab().setText("기본 정보 입력"));
        tabBar.addTab(tabBar.newTab().setText("정보 확인"));
        tabBar.addTab(tabBar.newTab().setText("친구 목록"));
        tabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager : tabPager setting - pager 를 fragment 로 연결하고, tab layout 형식으로 fragment 이동
        userTabPager = (ViewPager) findViewById(R.id.user_manager_pg_user_pager);
        userTabPager.setAdapter(new UserPagerAdapter(getSupportFragmentManager(), userDbManager, userData, friendDbManager, friendDataArrayList));
        userTabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabBar));
        tabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                userTabPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Intent : MainActivity 에서 보내온 intent 의 pageNumber 값 확인
        Intent intent = getIntent();
        int pageNumber = intent.getIntExtra("pageNumber",-1);
        DeveloperManager.displayLog("UserManagerActivity", "pageNumber : " + pageNumber + " 입니다.");
        if(pageNumber == 0) {
            userTabPager.setCurrentItem(0);
        } else if(pageNumber == 2) {
            userTabPager.setCurrentItem(2);
        } else {
            DeveloperManager.displayLog("UserManagerActivity", "pageNumber 값이 없어요...");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // UserDbManager : close
        userDbManager.closeUserDbHelper();

        // FriendDbManager : close
        friendDbManager.closeFriendDbHelper();
    }


}