package com.skyman.billiarddata;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.fragment.user.UserPagerAdapter;
import com.skyman.billiarddata.fragment.user.UserViewPagerAdapter;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.projectblue.database.AppDbManager;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting2;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class UserManagerActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final String CLASS_NAME = "[Ac]_UserManagerActivity";

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
        setContentView(R.layout.activity_user_manager);

        // sessionManager : getter ( userData )
        this.userData = SessionManager.getUserDataFromIntent(getIntent());
        this.pageNumberOfFragment = SessionManager.getPageNumberFromIntent(getIntent());

        // appDbManager
        initAppDbManager();

        // widget : connect -> init
        connectWidget();
        initWidget();

        DeveloperManager.displayLog(
                CLASS_NAME,
                "======================================>>>>>>>>>>>>>>>>>>>>> user manager activity 에서"
        );

        DeveloperManager.displayLog(
                CLASS_NAME,
                "userData Object = " + userData
        );

        DeveloperManager.displayLog(
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

        tabLayout = (TabLayout) findViewById(R.id.userManager_tabLayout);

        viewPager2 = (ViewPager2) findViewById(R.id.userManager_viewPager2);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        adapter = new UserViewPagerAdapter(this, userData);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position) {
                    case 0:
                        tab.setText(getString(R.string.userManager_tabLayout_userInput));
                        break;
                    case 1:
                        tab.setText(getString(R.string.userManager_tabLayout_userInfo));
                        break;
                    case 2:
                        tab.setText(getString(R.string.userManager_tabLayout_userFriend));
                }
            }
        }).attach();

        moveFragmentPage();

    }


    /**
     * [method] intent 에서 "pageNumber"로 값을 받아와서 해당 pageNumber 값으로 fragment 페이지로 이동
     */
    private void moveFragmentPage() {
        final String METHOD_NAME = "[moveFragmentPage] ";

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "pageNumber : " + pageNumberOfFragment + " 입니다.");
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