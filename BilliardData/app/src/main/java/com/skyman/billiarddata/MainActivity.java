package com.skyman.billiarddata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // constant
    private final int TEMP_ID = 1;

    // instance variable
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [lv/C]Button : billiardInput mapping
        Button billiardInput = (Button) findViewById(R.id.main_bt_billiard_input);
        Button billiardDisplay = (Button) findViewById(R.id.main_bt_billiard_display);
        Button userManager = (Button) findViewById(R.id.main_bt_user_manager);
        Button statisticsManager = (Button) findViewById(R.id.main_bt_statistics_manager);
        Button appInfo = (Button) findViewById(R.id.main_bt_app_info);

        // [method]mappingOfWidget
        mappingOfWidget();

        // [iv/C]UserData : user 테이블에서 id 값으로 데이터 가져오기 / 현재는 혼자 이므로 id 는 1
        this.userData = this.userDbManager.loadContent(TEMP_ID);
        this.friendDataArrayList = this.friendDbManager.loadAllContentByUserId(TEMP_ID);

        // [lv/C]Button : billiardInput click listener setting
        billiardInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogOfCheckFriend : 유저 정보와 친구 목록이 있을 때, 친구를 선택해서 intent 에 저장하여 BilliardInputActivity 로 이동한다.
                setClickListenerOfBilliardInputButton();
            }
        });

        // [lv/C]Button : billiardDisplay click listener setting
        billiardDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
                Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                SessionManager.setIntentOfUserData(intent, userData);
                startActivity(intent);

            }
        });

        // [lv/C]Button : userManager click listener setting
        userManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 intent 생성
                Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                SessionManager.setIntentOfUserData(intent, userData);
                startActivity(intent);

            }
        });

        // [lv/C]Button : statistics click listener setting
        statisticsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
                Intent intent = new Intent(getApplicationContext(), StatisticsManagerActivity.class);
                SessionManager.setIntentOfUserData(intent, userData);
                startActivity(intent);

            }
        });

        // [lv/C]Button : appInfo click listener setting
        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Intent : AppInfoActivity 로 이동하기 위한 intent 생성
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);
                startActivity(intent);

            }
        });
    } // End of method [onCreate]


    @Override
    protected void onStart() {
        super.onStart();
        DeveloperManager.displayLog("[Ac]_MainActivity", "[onStart] 메소드가 실행하였습니다.");

        // [check 1] : UserDbManager 가 생성되었다.
        if (this.userDbManager != null) {

            // [iv/C]UserData : 뒤로 가기로 왔을 때 해당 userId 로 userData 다시 가져오기
            this.userData = this.userDbManager.loadContent(TEMP_ID);

        } else {
            DeveloperManager.displayLog("[Ac]_MainActivity", "[onStart] user 메니저가 생성되지 않았습니다.");
        } // [check 1]

    } // End of method [onStart]


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // [check 1] : userDbManager 가 생성되었다.
        if (this.userDbManager != null) {
            // [iv/C]UserDbManager : close
            this.userDbManager.closeDb();
        } else {
            DeveloperManager.displayLog("[Ac]_MainActivity", "[onDestroy] user 메니저가 생성되지 않았습니다.");
        } // [check 1]

        // [check 2] : FriendDbManager 가 생성되었다.
        if (this.friendDbManager != null) {
            // [iv/C]FriendDbManager : close
            this.friendDbManager.closeDb();
        } else {
            DeveloperManager.displayLog("[Ac]_MainActivity", "[onDestroy] friend 메니저가 생성되지 않았습니다.");
        } // [check 2]

    } // End of method [onDestroy]


    /**
     * [method] user, friend 테이블 관리하는 객체 생성
     */
    private void mappingOfWidget() {

        // [iv/C]UserDbManager : user 테이블을 관리하는 매니저 생성 및 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블을 관리하느 매니저 생성 및 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

    } // End of method [mappingOfWidget]


    /**
     * [method] userDate -> FriendData 의 데이터 유무를 확인하고 모두 존재할 경우만 친구목록을 선택하여 BilliardInputActivity 로 넘어간다.
     *
     */
    private void  setClickListenerOfBilliardInputButton() {

        // [check 1] : userData 가(나의 정보) 있다.
        if (userData != null) {

            // [iv/C]ArrayList<FriendData> : 나의 친구목록을 해당 userId 로 모두 받아오기
            friendDataArrayList = friendDbManager.loadAllContentByUserId(TEMP_ID);

            // [check 2] : 친구 목록이 있다.
            if (friendDataArrayList.size() != 0) {

                DeveloperManager.displayLog("[Ac]_MainActivity", "[setClickListenerOfBilliardInputButton] 모든 정보가 입력되어 친구를 선택합니다.");

                // [method]showAlertToFriendList : 모든 친구목록을 가져와서 RadioGroup 으로 띄우고, 선택된 친구의 정보를 FriendData 에 담아 intent 로 BilliardInputActivity 로 넘긴다.
                showDialogOfCheckFriend();

            } else {

                DeveloperManager.displayLog("[Ac]_MainActivity", "[setClickListenerOfBilliardInputButton] 친구 목록이 없습니다.");

                // [method]showDialogToCheckWhetherToMoveUMAWithFriendData : 친구 목록이 없으므로 친구를 추가 하도록 UserManagerActivity 로 pageNumber=2(UserFriend) 를 intent 로 넘긴다.
                showDialogToCheckWhetherToMoveUMAWithFriendData();

            } // [check 2]

        } else {

            DeveloperManager.displayLog("[Ac]_MainActivity", "[setClickListenerOfBilliardInputButton] 나의 정보가 등록되어 있지 않습니다.");

            // [method]showDialogToCheckWhetherToMoveUMAWithUserData : 나의 정보가 없으므로 초기 나의 정보를 저장하도록 UserManagerActivity 로 pageNumber=0(UserInput) 를 intent 로 넘긴다.
            showDialogToCheckWhetherToMoveUMAWithUserData();

        } // [check 1]

    } // End of method [ setClickListenerOfBilliardInputButton]


    /**
     * [method] 친구목록을 AlertDialog 의 setSingleChoiceItems 를 설정하고, 선택한 값을 intent 로 넘겨준다.
     *
     */
    private void showDialogOfCheckFriend() {

        // [lv/C]ArrayList<String> : 친구 이름만 담길 배열
        final ArrayList<String> friendNameList = new ArrayList<>();

        // [cycle 1] : friendDataArrayList 에 담긴 name 을 friendNameList 에 담는다.
        for (int position = 0; position < friendDataArrayList.size(); position++) {
            friendNameList.add(friendDataArrayList.get(position).getName());
        } // [cycle 1]

        // [lv/C]CharSequence[] : friendNameList 를 AlertDialog 에 넘길 수 있도록 toArray 로 만들기
        final CharSequence[] friendNameArray = friendNameList.toArray(new String[friendNameList.size()]);

        // [lv/i]selectedIndex : 선택된 친구의 index 를 받아오기 위한 변수 생성 / 초기값은 0 으로
        final int[] selectedIndex = {0};

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.ad_main_friend_list_title)
                .setSingleChoiceItems(friendNameArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/i]selectedIndex : 선택 된 RadioButton 의 위치를 담는다.
                        selectedIndex[0] = which;

                    }
                })
                .setPositiveButton(R.string.ad_main_bt_friend_list_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [check 1] : 유저 정보가 있다. / 다시 한 번 검사하는게 필요있니?
                        if(userData != null) {

                            // [check 2] : 친구 목록이 있다. / 다시 한 번 검사하는게 필요있니?
                            if (friendNameArray.length != 0) {

                                DeveloperManager.displayLog("[Ac]_MainActivity", "[showDialogOfCheckFriend] 모든 친구를 출력합니다.");
                                DeveloperManager.displayToFriendData("[Ac]_MainActivity", friendDataArrayList.get(selectedIndex[0]));

                                // [lv/C]Intent : BilliardInputActivity 로 이동하기 위한 Intent 생성
                                Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);

                                // [lv/C]Intent : selectedIndex 의 FriendData 값을 serialize 화여 Intent 로 넘겨주기
                                SessionManager.setIntentOfUserData(intent, userData);
                                SessionManager.setIntentOfPlayer(intent, friendDataArrayList.get(selectedIndex[0]));
                                SessionManager.setIntentOfPlayerList(intent, friendDataArrayList);
                                SessionManager.setIntentOfSelectedPlayerPosition(intent, selectedIndex[0]);

                                // [method] : intent 와 요청코드를 담아서 UserManagerActivity 로 이동
                                startActivityForResult(intent, 101);

                            } else {
                                DeveloperManager.displayLog("[Ac]_MainActivity", "[showDialogOfCheckFriend] 친구 목록이 없습니다.");
                            } // [check 2]

                        } else {
                            DeveloperManager.displayLog("[Ac]_MainActivity", "[showDialogOfCheckFriend] 나의 정보가 등록되어 있지 않습니다.");
                        } // [check 1]

                    }
                })
                .setNegativeButton(R.string.ad_main_bt_friend_list_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogOfCheckFriend]


    /**
     * [method] 유저 등록 화면으로 이동할 건지 물어보는 Dialog 를 띄우고, 다음화면을 누르면 UserManagerActivity 로 이동하면서 pageNumber=0 값을 intent 로 넘긴다.
     *
     */
    private void showDialogToCheckWhetherToMoveUMAWithUserData() {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.ad_main_user_data_title)
                .setMessage(R.string.ad_main_user_data_message)
                .setPositiveButton(R.string.ad_main_bt_user_data_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 Intent 생성
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // [lv/C]Intent : UserManagerActivity 에서 0번째 Fragment 로 이동하라고 intent 에 담아서 넘겨주기
                        intent.putExtra("pageNumber", 0);

                        // [method] : intent 와 요청코드를 담아서 UserManagerActivity 로 이동
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog("[Ac]_MainActivity", "[showDialogToCheckWhetherToMoveUMAWithUserData] 나의 정보가 없어서 등록하러 이동합니다.");

                    }
                })
                .setNegativeButton(R.string.ad_main_bt_user_data_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherToMoveUMAWithUserData]


    /**
     * [method] 친구를 등록 할건지 물어보는 Dialog 를 띄우고, 다음화면을 누르면 UserManagerActivity 로 이동하면서 pageNumber=2 값을 intent 로 넘겨준다.
     *
     */
    private void showDialogToCheckWhetherToMoveUMAWithFriendData() {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.ad_main_friend_data_title)
                .setMessage(R.string.ad_main_friend_data_message)
                .setPositiveButton(R.string.ad_main_bt_friend_data_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 Intent 생성
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // [lv/C]Intent : UserManagerActivity 에서 2번째 Fragment 로 이동하라고 intent 에 담아서 넘겨주기
                        intent.putExtra("pageNumber", 2);

                        // [method] : intent 와 요청코드를 담아서 UserManagerActivity 로 이동
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog("[Ac]_MainActivity", "[showDialogToCheckWhetherToMoveUMAWithFriendData] 등록된 친구가 없어서 추가하러 이동합니다.");

                    }
                })
                .setNegativeButton(R.string.ad_main_bt_friend_data_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherToMoveUMAWithFriendData]\

}