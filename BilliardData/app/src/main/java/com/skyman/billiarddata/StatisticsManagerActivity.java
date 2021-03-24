package com.skyman.billiarddata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skyman.billiarddata.fragment.statistics.StatisticsViewPagerAdapter;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.management.player.database.PlayerDbManager2;
import com.skyman.billiarddata.management.projectblue.database.AppDbManager;
import com.skyman.billiarddata.management.statistics.SameDateChecker;
import com.skyman.billiarddata.management.statistics.SameDateCheckerMake;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class StatisticsManagerActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private final String CLASS_NAME = StatisticsManagerActivity.class.getSimpleName();

    // instance variable
    private UserData userData = null;

    // instance variable
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDateChecker sameDateChecker;

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

                SameDateCheckerMake util = new SameDateCheckerMake();

                sameDateChecker = util.makeSameDateCheckerWithUserDataAndAllBilliardData(userData, billiardDataArrayList);

            }

        }

        // tabLayout, viewPager2 설정
        adapter = new StatisticsViewPagerAdapter(this, userData, billiardDataArrayList, sameDateChecker);
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