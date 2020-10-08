package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.statistics.StatisticsPagerAdapter;
import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class StatisticsManagerActivity extends AppCompatActivity {

    // instance variable
    private UserDbManager userDbManager = null;
    private BilliardDBManager billiardDBManager = null;
    private UserData userData = null;

    // instance variable
    private TabLayout statisticsTabBar;
    private ViewPager statisticsTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_manager);

        // [method]createDbManager : user, billiard 메니저 생성 및 초기화
        createDbManager();

        // [method]mappingOfWidget : activity_statistics_manager layout 의 widget 을 mapping
        mappingOfWidget();

        // [iv/C]TabLayout : statisticsTabBar 의 메뉴 설정
        this.statisticsTabBar.addTab(this.statisticsTabBar.newTab().setText("달력"));
        this.statisticsTabBar.addTab(this.statisticsTabBar.newTab().setText("차트"));
        this.statisticsTabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        // [lv/C]StatisticsPagerAdapter : Fragment 와 연결하기 위한 adapter 생성하기 / userDbManager 와 billiardDbManager 을 넘겨준다.
        StatisticsPagerAdapter statisticsPagerAdapter = new StatisticsPagerAdapter(getSupportFragmentManager(), userDbManager, billiardDBManager);

        // [iv/C]ViewPager : 위 의 adapter 와 연결하기
        this.statisticsTabPager.setAdapter(statisticsPagerAdapter);

        // [iv/C]ViewPager  : 위 의 TabLayout 을 page change listener 와 연결하기
        this.statisticsTabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.statisticsTabBar));

        // [iv/C]TabLayout : statisticsTabBar 의 메뉴를 클릭 했을 때 Pager 의 몇 번째 fragment 로 움직일지 설정하기
        this.statisticsTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                // [iv/C]ViewPager : statisticsTabPager 를 userTabBar 에서 선택한 메뉴의 위치 값을 이용하여 해당 fragment 로 이동하기
                statisticsTabPager.setCurrentItem(tab.getPosition());
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

        // [check 1] : user 메니저가 생성되었다.
        if (this.userDbManager != null){
            this.userDbManager.closeDb();
        } else {
            DeveloperManager.displayLog("[Ac]_StatisticsManagerActivity", "[onDestroy] user 메니저가 생성되지 않았습니다.");
        } // [check 1]

        // [check 2] : billiard 메니저가 생성되었다.
        if (this.billiardDBManager != null) {
            this.billiardDBManager.closeDb();
        } else {
            DeveloperManager.displayLog("[Ac]_StatisticsManagerActivity", "[onDestroy] billiard 메니저가 생성되지 않았습니다.");
        } // [check 2]

    }


    /**
     * [method] user, billiard 메니저를 생성한다.
     *
     */
     private void createDbManager () {

         // [iv/C]UserDbManager : user 메니저 생성 및 초기화
         this.userDbManager = new UserDbManager(this);
         this.userDbManager.initDb();

         // [iv/C]BilliardDBManager : billiard 메니저 생성 및 초기화
         this.billiardDBManager = new BilliardDBManager(this);
         this.billiardDBManager.initDb();

     } // End of method [createDbManager]


    /**
     * [method] activity_statistics_manager layout 의 widget 을 mapping
     *
     */
    private void mappingOfWidget(){

        // [iv/C]TabLayout : statisticsTabBar mapping
        this.statisticsTabBar = (TabLayout) findViewById(R.id.statistics_manager_tl_tab_bar);

        // [iv/C]ViewPager : statisticsTabPager mapping
        this.statisticsTabPager = (ViewPager) findViewById(R.id.statistics_manager_pg_statistics_pager);

    } // End of method [mappingOfWidget]

}