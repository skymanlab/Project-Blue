package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.skyman.billiarddata.factivity.statistics.StatisticsPagerAdapter;

public class StatisticsManagerActivity extends AppCompatActivity {

    // variable : tab layout 객체 선언
    private TabLayout statisticsTabBar;

    // variable : view pager 객체 선언
    private ViewPager statisticsTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_manager);

        // TabLayout
        statisticsTabBar = (TabLayout) findViewById(R.id.statistics_manager_tl_tab_bar);
        statisticsTabBar.addTab(statisticsTabBar.newTab().setText("달력"));
        statisticsTabBar.addTab(statisticsTabBar.newTab().setText("차트"));
        statisticsTabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager
        statisticsTabPager = (ViewPager) findViewById(R.id.statistics_manager_pg_statistics_pager);
        StatisticsPagerAdapter statisticsPagerAdapter = new StatisticsPagerAdapter(getSupportFragmentManager());
        statisticsTabPager.setAdapter(statisticsPagerAdapter);
        statisticsTabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(statisticsTabBar));
        statisticsTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
}