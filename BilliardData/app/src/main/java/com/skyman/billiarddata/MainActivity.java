package com.skyman.billiarddata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.dialog.PlayerList;
import com.skyman.billiarddata.dialog.PlayerListDialog;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private final String CLASS_NAME_LOG = "[Ac]_MainActivity";
    private final int TEMP_ID = 1;

    // instance variable
//    private UserDbManager userDbManager;
//    private FriendDbManager friendDbManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable
    private SectionManager sectionManager;

    // instance variable : widget
    private Button billiardInput;
    private Button billiardDisplay;
    private Button userManager;
    private Button statisticsManager;
    private Button appInfo;
    private Button appSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sectionManager
        initSectionManager();

        // widget : connect, init
        connectWidget();
        initWidget();


    } // End of method [onCreate]


    @Override
    public void initSectionManager() {

        // sectionManager : 섹션 관리를 위한 sectionManager 를 생성하고
        // db를 사용하기 위해 필요한 db manager 를 요청한다.
        sectionManager = new SectionManager(this);
        sectionManager.connectDb(true, true, false, false);
    }

    @Override
    public void connectWidget() {

        // 당구 데이터 입력
        billiardInput = (Button) findViewById(R.id.main_button_billiardInput);

        // 당구 데이터 보기
        billiardDisplay = (Button) findViewById(R.id.main_button_billiardDisplay);

        // 나의 정보
        userManager = (Button) findViewById(R.id.main_button_userManager);

        // 통계
        statisticsManager = (Button) findViewById(R.id.main_button_statisticsManager);

        // 어플 설정
        appSetting = (Button) findViewById(R.id.main_button_appSetting);

        // 어플 정보
        appInfo = (Button) findViewById(R.id.main_button_appInfo);

    }

    @Override
    public void initWidget() {

        // [lv/C]Button : billiardInput click listener setting
        billiardInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClickListenerOfBilliardInputButton();
                    }
                }
        );


        // [lv/C]Button : billiardDisplay click listener setting
        billiardDisplay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                        SessionManager.setIntentOfUserData(intent, userData);
                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : userManager click listener setting
        userManager.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                        SessionManager.setIntentOfUserData(intent, userData);
                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : statistics click listener setting
        statisticsManager.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), StatisticsManagerActivity.class);
                        SessionManager.setIntentOfUserData(intent, userData);
                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : appSetting click listener setting
        appSetting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [lv/C]Intent : AppInfoActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), AppSettingActivity.class);
                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : appInfo click listener setting
        appInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [lv/C]Intent : AppInfoActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);
                        startActivity(intent);

                    }
                }
        );

    }


    @Override
    protected void onStart() {
        final String METHOD_NAME = "[onStart] ";
        super.onStart();

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "====>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        sectionManager.requestDbQuery(
                new SectionManager.DbQueryRequestListener() {
                    @Override
                    public void requestUserDb(UserDbManager userDbManager) {
                        userData = userDbManager.loadContent(TEMP_ID);
                    }

                    @Override
                    public void requestFriendDb(FriendDbManager friendDbManager) {
                        friendDataArrayList = friendDbManager.loadAllContentByUserId(TEMP_ID);
                    }

                    @Override
                    public void requestBilliardDb(BilliardDbManager billiardDbManager) {

                    }

                    @Override
                    public void requestPlayerDb(PlayerDbManager playerDbManager) {

                    }
                }
        );

    } // End of method [onStart]


    @Override
    protected void onDestroy() {
        final String METHOD_NAME = "[onDestroy] ";
        super.onDestroy();

        // sectionManager : 연결요청한 db manager 를 종료한다.
        sectionManager.closeDb();

    } // End of method [onDestroy]


    /**
     * [method] userDate -> FriendData 의 데이터 유무를 확인하고 모두 존재할 경우만 친구목록을 선택하여 BilliardInputActivity 로 넘어간다.
     */
    private void setClickListenerOfBilliardInputButton() {

        final String METHOD_NAME = "[setClickListenerOfBilliardInputButton] ";

        // [check 1] : userData 가(나의 정보) 있다.
        if (userData != null) {

            // [check 2] : 친구 목록이 있다.
            if (friendDataArrayList.size() != 0) {

                // 친구 목록에서 같이 게임할 player 를 선택하는 AlertDialog
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 정보가 입력되어 친구를 선택합니다.");
                showDialogOfPlayerSelection();

            } else {

                // 친구 등록을 할 건지 물어보는 AlertDialog
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "친구 목록이 없습니다.");
                showDialogOfFriendRegisterCheck();

            } // [check 2]

        } else {

            // 사용자 등록을 할 건지 물어보는 AlertDialog
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "나의 정보가 등록되어 있지 않습니다.");
            showDialogOfUserRegisterCheck();

        } // [check 1]

    } // End of method [ setClickListenerOfBilliardInputButton]


    /**
     * [method] 친구목록을 AlertDialog 의 setSingleChoiceItems 를 설정하고, 선택한 값을 intent 로 넘겨준다.
     */
    private void showDialogOfPlayerSelection() {

        final String METHOD_NAME = "[showDialogOfPlayerSelection] ";

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
        builder.setTitle(R.string.main_dialog_playerSelection_title)
                .setSingleChoiceItems(friendNameArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/i]selectedIndex : 선택 된 RadioButton 의 위치를 담는다.
                        selectedIndex[0] = which;

                    }
                })
                .setPositiveButton(R.string.main_dialog_playerSelection_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [check 1] : 유저 정보가 있다. / 다시 한 번 검사하는게 필요있니?
                        if (userData != null) {

                            // [check 2] : 친구 목록이 있다. / 다시 한 번 검사하는게 필요있니?
                            if (friendNameArray.length != 0) {

                                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 친구를 출력합니다.");
                                DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendDataArrayList.get(selectedIndex[0]));

                                // [lv/C]Intent : BilliardInputActivity 로 이동하기 위한 Intent 생성
                                Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);

                                // [lv/C]Intent : selectedIndex 의 FriendData 값을 serialize 화여 Intent 로 넘겨주기
                                SessionManager.setIntentOfUserData(intent, userData);

                                // [lv/C]ArrayList<FriendData> : 선택 한 친구들을 추가하기
                                ArrayList<FriendData> tempArrayList = new ArrayList<>();
                                tempArrayList.add(friendDataArrayList.get(selectedIndex[0]));

                                // [lv/C]SessionManager : 'playerList' 에 추가해야 한다.
                                SessionManager.setIntentOfFriendPlayerList(intent, tempArrayList);

                                // [method] : intent 와 요청코드를 담아서 UserManagerActivity 로 이동
                                startActivityForResult(intent, 101);

                            } else {
                                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "친구 목록이 없습니다.");
                            } // [check 2]

                        } else {
                            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "나의 정보가 등록되어 있지 않습니다.");
                        } // [check 1]

                    }
                })
                .setNegativeButton(R.string.main_dialog_playerSelection_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogOfPlayerSelection]


    /**
     * [method] 유저 등록 화면으로 이동할 건지 물어보는 Dialog 를 띄우고, 다음화면을 누르면 UserManagerActivity 로 이동하면서 pageNumber=0 값을 intent 로 넘긴다.
     */
    private void showDialogOfUserRegisterCheck() {

        final String METHOD_NAME = "[showDialogOfUserRegisterCheck] ";

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.main_dialog_userRegisterCheck_title)
                .setMessage(R.string.main_dialog_userRegisterCheck_message)
                .setPositiveButton(R.string.main_dialog_userRegisterCheck_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 Intent 생성
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // [lv/C]Intent : UserManagerActivity 에서 0번째 Fragment 로 이동하라고 intent 에 담아서 넘겨주기
                        SessionManager.setIntentOfPageNumber(intent, 0);

                        // [method] : intent 와 요청코드를 담아서 UserManagerActivity 로 이동
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "나의 정보가 없어서 등록하러 이동합니다.");

                    }
                })
                .setNegativeButton(R.string.main_dialog_userRegisterCheck_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    } // End of method [showDialogOfUserRegisterCheck]


    /**
     * [method] 친구를 등록 할건지 물어보는 Dialog 를 띄우고, 다음화면을 누르면 UserManagerActivity 로 이동하면서 pageNumber=2 값을 intent 로 넘겨준다.
     */
    private void showDialogOfFriendRegisterCheck() {

        final String METHOD_NAME = "[showDialogOfFriendRegisterCheck] ";

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.main_dialog_friendRegisterCheck_title)
                .setMessage(R.string.main_dialog_friendRegisterCheck_message)
                .setPositiveButton(R.string.main_dialog_friendRegisterCheck_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 Intent 생성
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // [lv/C]Intent : UserManagerActivity 에서 2번째 Fragment 로 이동하라고 intent 에 담아서 넘겨주기
                        SessionManager.setIntentOfPageNumber(intent, 2);

                        // [lv/C]Intent : SessionManager 를 통해서 이미 user 정보는 입력되었으므로, intent 에 user 정보를 추가하여 보낸다.
                        SessionManager.setIntentOfUserData(intent, userData);

                        // [method] : intent 와 요청코드를 담아서 UserManagerActivity 로 이동
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "등록된 친구가 없어서 추가하러 이동합니다.");

                    }
                })
                .setNegativeButton(R.string.main_dialog_friendRegisterCheck_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    } // End of method [showDialogOfFriendRegisterCheck]\

}