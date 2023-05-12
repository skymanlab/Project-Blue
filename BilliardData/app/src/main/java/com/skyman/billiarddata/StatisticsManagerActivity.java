package com.skyman.billiarddata;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.etc.SessionManager;
import com.skyman.billiarddata.etc.calendar.SameDate;
import com.skyman.billiarddata.etc.calendar.SameDateCheckerUtil;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.fragment.statistics.StatisticsViewPagerAdapter;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;

public class StatisticsManagerActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final Display CLASS_LOG_SWITCH = Display.ON;
    private static final String CLASS_NAME = "StatisticsManagerActivity";

    // instance variable
    private UserData userData = null;

    // instance variable
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDate sameDate;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    // instance variable
    private StatisticsViewPagerAdapter adapter;

    // getter
    public AppDbManager getAppDbManager() {
        return appDbManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_manager);

        // sessionManager : getter ( userData )
        this.userData = SessionManager.getUserDataFromIntent(getIntent());

        // appDbManger
        initAppDbManager();

        // widget : connect -> init
        connectWidget();
        initWidget();


    } // End of method [onCreate]


    @Override
    protected void onDestroy() {

        appDbManager.closeDb();
        super.onDestroy();

    } // End of method [onDestroy]


    @Override
    public void initAppDbManager() {

        appDbManager = new AppDbManager(this);
        appDbManager.connectDb(
                true,
                false,
                true,
                true
        );

    }

    @Override
    public void connectWidget() {

        this.tabLayout = (TabLayout) findViewById(R.id.statisticsManager_tabLayout);

        this.viewPager2 = (ViewPager2) findViewById(R.id.statisticsManager_viewPager2);

    }

    @Override
    public void initWidget() {

        // 등록된 게임 정보를 가져오기
        if (userData != null) {

            billiardDataArrayList = loadBilliardDataArrayList();

            if (!billiardDataArrayList.isEmpty()) {

                SameDateCheckerUtil sameDateCheckerUtil = new SameDateCheckerUtil();
                sameDate = sameDateCheckerUtil.createSameDate(userData, billiardDataArrayList);

            }

        }

        // tabLayout, viewPager2 설정
        adapter = new StatisticsViewPagerAdapter(this, userData, billiardDataArrayList, sameDate);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(2);
        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position) {
                            case 0:
                                tab.setText(getString(R.string.statisticsManager_tabLayout_calendar));
                                break;
                            case 1:
                                tab.setText(getString(R.string.statisticsManager_tabLayout_monthStatistics));
                                break;
                            default:
                                break;
                        }

                    }
                }
        ).attach();

    }

    /**
     * [method] 해당 userId 값으로 billiard 테이블에서 데이터를 가져오기
     */
    private ArrayList<BilliardData> loadBilliardDataArrayList() {
        final String METHOD_NAME = "[loadBilliardDataArrayList] ";

        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();


        // <1>
        // 나의 id 와 name 으로
        // 내가 참가한 게임을 player 테이블에서 가져온다.
        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {
                        playerDataArrayList.addAll(
                                playerDbManager2.loadAllContentByPlayerIdAndPlayerName(userData.getId(), userData.getName())
                        );

                    }
                }
        );

        // <2>
        // 내가 참가한 게임에 대한 정보가 들어있는 playerDataArrayList 를 통해서
        // 모든 게임정보(billiardData) 를 가져와서 billiardDataArrayList 에 담는다.
        appDbManager.requestBilliardQuery(
                new AppDbManager.BilliardQueryRequestListener() {
                    @Override
                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                        for (int index = 0; index < playerDataArrayList.size(); index++) {

                            // playerData 의 billiardCount 로
                            // 해당 billiardData 가져와서 추가한다.
                            billiardDataArrayList.add(
                                    billiardDbManager2.loadContentByCount(playerDataArrayList.get(index).getBilliardCount())
                            );

                        }

                    }
                }
        );

        return billiardDataArrayList;
    } // End of method [loadBilliardDataArrayList]

}