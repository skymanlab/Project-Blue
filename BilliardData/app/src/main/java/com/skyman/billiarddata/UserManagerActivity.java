package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.user.UserPagerAdapter;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class UserManagerActivity extends AppCompatActivity {

    // value : UserDbManager 객체 선언
    private UserDbManager userDbManager;

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
        UserData userData = new UserData();

        if (userDbManager != null) {
            userDbManager.init_db();
            userData = userDbManager.load_content();
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
        userTabPager.setAdapter(new UserPagerAdapter(getSupportFragmentManager(), userDbManager, userData, userTabPager));
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // UserDbManager : close
        userDbManager.closeUserDbHelper();
    }
}