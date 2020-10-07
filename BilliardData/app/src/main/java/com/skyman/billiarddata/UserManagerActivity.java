package com.skyman.billiarddata;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.user.UserPagerAdapter;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserManagerActivity extends AppCompatActivity {

    // instance variable
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;

    // instance variable : widget
    private ViewPager userTabPager;
    private TabLayout userTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        // [method] : user, friend 테이블을 관리하는 메니저 생성과 초기화
        createDBManager();

        // [lv/C]UserData : user 정보가 담길 객체
        this.userData = this.userDbManager.loadContent(1);

        // [lv/C]ArrayList<FriendData> : 위 의 user 의 id 로 friend 테이블의 모든 친구목록 가져오기
        this.friendDataArrayList = this.friendDbManager.loadAllContentByUserId(1);

        // TabLayout : tab setting
        this.userTabBar = (TabLayout) findViewById(R.id.user_manager_tl_tab_bar);
        this.userTabBar.addTab(this.userTabBar.newTab().setText("기본 정보 입력"));
        this.userTabBar.addTab(this.userTabBar.newTab().setText("정보 확인"));
        this.userTabBar.addTab(this.userTabBar.newTab().setText("친구 목록"));
        this.userTabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager : tabPager setting - pager 를 fragment 로 연결하고, tab layout 형식으로 fragment 이동
        this.userTabPager = (ViewPager) findViewById(R.id.user_manager_pg_user_pager);
        this.userTabPager.setAdapter(new UserPagerAdapter(getSupportFragmentManager(), this.userDbManager, this.friendDbManager, this.userData, this.friendDataArrayList));
        this.userTabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.userTabBar));
        this.userTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        // [method]moveFragmentPage : intent 값에서 pageNumber 값을 가져와서 해당 pageNumber 로 Fragment 페이지 이동을 한다. / 위 의 pager 가 셋팅되어야지만 이동할 수 있다.
        moveFragmentPage();


    } // End of method [onCreate]

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // [check 1] : UserDbManager 가 있다.
        if(this.userDbManager != null) {
            // UserDbManager : close
            userDbManager.closeUserDbHelper();
        } else {

        } // [check 1]

        // [check 2] : FriendDbManager 가 있다.
        if (this.friendDbManager != null) {
            // FriendDbManager : close
            friendDbManager.closeFriendDbHelper();
        }
    } // End of method [onDestroy]


    /*                                      private method
     *   ============================================================================================
     *  */
    /**
     * [method] user, friend 테이블을 관리하는 메니저를 생성한다.
     *
     */
    private void createDBManager() {

        // [iv/C]UserDbManager : user 테이블을 관리하는 매니저 생성과 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.init_db();
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블을 관리하는 매니저 생성과 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.init_db();
        this.friendDbManager.initDb();
    }


    /**
     * [method] intent 에서 "pageNumber"로 값을 받아와서 해당 pageNumber 값으로 fragment 페이지로 이동
     *
     */
    private void moveFragmentPage(){

        // [lv/C]Intent : 그 전 activity 에서 보낸 intent 가져오기
        Intent pageNumberIntent = getIntent();

        // [lv/i]pageNumber : 위 의 intent 에서 pageNumber 값으로 가져오기 - 기본 값은 '-1' 이다.
        int pageNumber = pageNumberIntent.getIntExtra("pageNumber", -1);

        DeveloperManager.displayLog("[Ac]_UserManagerActivity", "[getPageNumberToIntent] pageNumber : " + pageNumber + " 입니다.");
        switch (pageNumber) {
            case 0:
                // [iv/C]ViewPager : pageNumber 값으로 페이지 이동하기
                this.userTabPager.setCurrentItem(0);
                break;
            case 1:
                this.userTabPager.setCurrentItem(1);
                break;
            case 2:
                this.userTabPager.setCurrentItem(2);
                break;
            default:
                break;
        }

    } // End of method [moveFragmentPage]

}