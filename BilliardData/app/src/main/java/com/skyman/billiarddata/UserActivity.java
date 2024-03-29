package com.skyman.billiarddata;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.fragment.user.UserViewPagerAdapter;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.etc.SessionManager;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;


public class UserActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "UserActivity";

    // constant
    public static final int USER_INPUT_FRAGMENT = 0;
    public static final int USER_INFO_FRAGMENT = 1;
    public static final int USER_FRIEND_FRAGMENT = 2;

    // instance variable
    private UserData userData = null;
    private int pageNumberOfFragment = 0;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable : widget
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    // instance variable
    private UserViewPagerAdapter adapter;

    // getter
    public AppDbManager getAppDbManager() {
        return appDbManager;
    }

    public UserViewPagerAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreate] ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // sessionManager : getter ( userData )
        this.userData = SessionManager.getUserDataFromIntent(getIntent());
        this.pageNumberOfFragment = SessionManager.getPageNumberFromIntent(getIntent());

        // appDbManager
        initAppDbManager();

        // widget : connect -> init
        connectWidget();
        initWidget();

        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "======================================>>>>>>>>>>>>>>>>>>>>> user manager activity 에서"
        );

        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "userData Object = " + userData
        );

        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "appDbManager Object = " + appDbManager
        );

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
                true,
                true,
                true
        );

    }

    @Override
    public void connectWidget() {

        tabLayout = (TabLayout) findViewById(R.id.A_user_tabLayout);

        viewPager2 = (ViewPager2) findViewById(R.id.A_user_viewPager2);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        adapter = new UserViewPagerAdapter(this, userData);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);
        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position) {
                            case 0:
                                tab.setText(getString(R.string.A_user_tabLayout_userInput));
                                break;
                            case 1:
                                tab.setText(getString(R.string.A_user_tabLayout_userInfo));
                                break;
                            case 2:
                                tab.setText(getString(R.string.A_user_tabLayout_userFriend));
                        }
                    }
                }
        ).attach();

        moveFragmentPage();

    }


    /**
     * [method] intent 에서 "pageNumber"로 값을 받아와서 해당 pageNumber 값으로 fragment 페이지로 이동
     */
    private void moveFragmentPage() {
        final String METHOD_NAME = "[moveFragmentPage] ";

        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "pageNumber : " + pageNumberOfFragment + " 입니다.");
        switch (pageNumberOfFragment) {

            case USER_INPUT_FRAGMENT:
                viewPager2.setCurrentItem(0);
                break;
            case USER_INFO_FRAGMENT:
                viewPager2.setCurrentItem(1);
                break;
            case USER_FRIEND_FRAGMENT:
                viewPager2.setCurrentItem(2);
                break;
            default:
                break;

        }

    } // End of method [moveFragmentPage]

}