package com.skyman.billiarddata;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.fragment.user.UserPagerAdapter;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserManagerActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME_LOG = "[Ac]_UserManagerActivity";

    // instance variable
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private BilliardDbManager billiardDbManager = null;
    private PlayerDbManager playerDbManager = null;
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;

    // instance variable : widget
    private ViewPager userTabPager;
    private TabLayout userTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        final String METHOD_NAME = "[onCreate] ";

        // [method] : user, friend 테이블을 관리하는 메니저 생성과 초기화
        createDBManager();

        // [lv/C]Intent : 저 Activity 에서 보내온 데이터를 담을 intent 가져오기
        Intent intent = getIntent();

        // [lv/C]UserData : user 정보가 담길 객체
        this.userData = SessionManager.getUserDataInIntent(intent);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "=====================================================================================");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "session 메니저를 통해 userData 를 가져 왔습니다. 확인해 보겠습니다.");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "=====================================================================================");

        // [check 1] : user 정보가 있다.
        if (this.userData != null) {
            // [lv/C]ArrayList<FriendData> : 위 의 user 의 id 로 friend 테이블의 모든 친구목록 가져오기
            this.friendDataArrayList = this.friendDbManager.loadAllContentByUserId(this.userData.getId());
            DeveloperManager.displayToFriendData(CLASS_NAME_LOG, this.friendDataArrayList);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "=====================================================================================");
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 정보가 입력되지 않아 친구목록을 받아오지 못합니다.");
        } // [check 1]


        // [iv/C]TabLayout : userTabBar mapping
        this.userTabBar = (TabLayout) findViewById(R.id.user_manager_tl_tab_bar);

        // [iv/C]ViewPager : userTabPager mapping
        this.userTabPager = (ViewPager) findViewById(R.id.user_manager_pg_user_pager);

        // [iv/C]TabLayout : userTabBar 의 메뉴 설정
        this.userTabBar.addTab(this.userTabBar.newTab().setText("기본 정보 입력"));
        this.userTabBar.addTab(this.userTabBar.newTab().setText("정보 확인"));
        this.userTabBar.addTab(this.userTabBar.newTab().setText("친구 목록"));
        this.userTabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        // [lv/C]UserPagerAdapter : Fragment 와 연결하기 위한 adapter 생성하기
        UserPagerAdapter userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager(), this.userDbManager, this.friendDbManager, this.billiardDbManager, this.playerDbManager, this.userData, this.friendDataArrayList);

        // [iv/C]ViewPager : 위 의 adapter 와 연결하기
        this.userTabPager.setAdapter(userPagerAdapter);

        // [iv/C]ViewPager  : 위 의 TabLayout 을 page change listener 와 연결하기
        this.userTabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.userTabBar));

        // [iv/C]TabLayout : userTabBar 의 메뉴를 클릭 했을 때 Pager 의 몇 번째 fragment 로 움직일지 설정하기
        this.userTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                // [iv/C]ViewPager : userTabPager 를 userTabBar 에서 선택한 메뉴의 위치 값을 이용하여 해당 fragment 로 이동하기
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
        moveFragmentPage(intent);


    } // End of method [onCreate]

    @Override
    protected void onDestroy() {
        final String METHOD_NAME = "[onDestroy] ";
        super.onDestroy();

        // user
        if (this.userDbManager != null) {
            this.userDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 메니저가 생성되지 않았습니다.");
        }


        // friend
        if (this.friendDbManager != null) {
            this.friendDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friend 메니저가 생성되지 않았습니다.");
        }


        // Billiard
        if (this.billiardDbManager != null) {
            this.billiardDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 메니저가 생성되지 않았습니다.");
        }

        // player
        if (this.playerDbManager != null) {
            this.playerDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 메니저가 생성되지 않았습니다.");
        }

    } // End of method [onDestroy]


    /*                                      private method
     *   ============================================================================================
     *  */

    /**
     * [method] user, friend 테이블을 관리하는 메니저를 생성한다.
     */
    private void createDBManager() {

        // [iv/C]UserDbManager : user 테이블을 관리하는 매니저 생성과 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블을 관리하는 매니저 생성과 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

        // [iv/C]BilliardDbManager : billiard 테이블을 관리하는 매니저 생성과 초기화
        this.billiardDbManager = new BilliardDbManager(this);
        this.billiardDbManager.initDb();

        // [iv/C]PlayerDbManager : player 테이블을 관리하는 매니저 생성과 초기화
        this.playerDbManager = new PlayerDbManager(this);
        this.playerDbManager.initDb();
    }


    /**
     * [method] intent 에서 "pageNumber"로 값을 받아와서 해당 pageNumber 값으로 fragment 페이지로 이동
     */
    private void moveFragmentPage(Intent intent) {


        final String METHOD_NAME = "[moveFragmentPage] ";

        // [lv/i]pageNumber : 위 의 intent 에서 pageNumber 값으로 가져오기 - 기본 값은 '-1' 이다.
        int pageNumber = SessionManager.getPageNumberInIntent(intent);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "pageNumber : " + pageNumber + " 입니다.");
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