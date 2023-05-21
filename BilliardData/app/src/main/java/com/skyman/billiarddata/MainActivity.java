package com.skyman.billiarddata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.SessionManager;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;
import com.skyman.billiarddata.table.user.database.UserDbManager2;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "MainActivity";
    private final int TEMP_ID = 1;                                      //
    private static final int NUMBER_OF_SELECTABLE_MAX_FRIEND = 3;       // 선택할 수 있는 최대 친구 수

    // instance variable : load database
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable : database manager
    private AppDbManager appDbManager;

    // instance variable : widget
    private Button billiardInput;
    private Button billiardDisplay;
    private Button userManager;
    private Button stats;
    private Button appInfo;
    private Button appSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AppDbManager
        initAppDbManager();

        // Widget : connect -> init
        connectWidget();
        initWidget();


    } // End of method [onCreate]


    @Override
    protected void onStart() {
        super.onStart();

        appDbManager.requestQuery(
                new AppDbManager.QueryRequestListener() {

                    @Override
                    public void requestUserQuery(UserDbManager2 userDbManager2) {

                        userData = userDbManager2.loadContent(TEMP_ID);
                        DeveloperLog.printLogUserData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                userData
                        );
                    }

                    @Override
                    public void requestFriendQuery(FriendDbManager2 friendDbManager2) {

                        friendDataArrayList = friendDbManager2.loadAllContentByUserId(TEMP_ID);
                        DeveloperLog.printLogFriendData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                friendDataArrayList
                        );

                    }

                    @Override
                    public void requestBilliardQuery(BilliardDbManager2 billiardDbManager2) {

                        DeveloperLog.printLogBilliardData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                billiardDbManager2.loadAllContent()
                        );
                    }


                    @Override
                    public void requestPlayerQuery(PlayerDbManager2 playerDbManager2) {

                        DeveloperLog.printLogPlayerData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                playerDbManager2.loadAllContent()
                        );
                    }
                }
        );

    } // End of method [onStart]


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
                false,
                false
        );
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
        stats = (Button) findViewById(R.id.main_button_stats);

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

                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);

                        // sessionManager : set ( userData)
                        SessionManager.setUserDataFromIntent(intent, userData);

                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : userManager click listener setting
        userManager.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // sessionManager : set ( userData)
                        SessionManager.setUserDataFromIntent(intent, userData);

                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : statistics click listener setting
        stats.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), StatsActivity.class);

                        // sessionManager : set ( userData)
                        SessionManager.setUserDataFromIntent(intent, userData);

                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : appSetting click listener setting
        appSetting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(getApplicationContext(), AppSettingActivity.class);

                        // sessionManager : set ( userData)
                        SessionManager.setUserDataFromIntent(intent, userData);

                        startActivity(intent);

                    }
                }
        );

        // [lv/C]Button : appInfo click listener setting
        appInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);

                        startActivity(intent);

                    }
                }
        );

    }

    /**
     * [method] userDate -> FriendData 의 데이터 유무를 확인하고 모두 존재할 경우만 친구목록을 선택하여 BilliardInputActivity 로 넘어간다.
     */
    private void setClickListenerOfBilliardInputButton() {
        final String METHOD_NAME = "[setClickListenerOfBilliardInputButton] ";

        // [check 1] : userData 가(나의 정보) 있다.
        if (userData != null) {

            // [check 2] : 친구 목록이 있다.
            if (!friendDataArrayList.isEmpty()) {

                // 친구 목록에서 같이 게임할 player 를 선택하는 AlertDialog
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "모든 정보가 입력되어 친구를 선택합니다.");
                showDialogOfPlayerMultiChoice();

            } else {

                // 친구 등록을 할 건지 물어보는 AlertDialog
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "친구 목록이 없습니다.");
                showDialogOfFriendRegisterCheck();

            } // [check 2]

        } else {

            // 사용자 등록을 할 건지 물어보는 AlertDialog
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "나의 정보가 등록되어 있지 않습니다.");
            showDialogOfUserRegisterCheck();

        } // [check 1]

    } // End of method [ setClickListenerOfBilliardInputButton]


    /**
     * AlertDialog : 게임에 참가하는 친구를 선택한다. 단 1명만
     */
    private void showDialogOfPlayerSingleChoice() {
        final String METHOD_NAME = "[showDialogOfParticipantsSingleSelection] ";

        // 내가 등록한 친구 목록으로 alertDialog 에 넣을 친구 이름 만들기
        final String[] friendNameList = createFriendNameList();

        // 선택한 라디오 버튼의 위치
        final int[] selectedIndex = {0};

        // <사용자 확인>
        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_playerChoiceInGame_title)
                .setSingleChoiceItems(
                        friendNameList,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // 친구 목록에서 선택한 친구의 위치값 (= friendDataArrayList 에서의 index)
                                selectedIndex[0] = which;

                            }
                        }
                )
                .setPositiveButton(
                        R.string.main_dialog_playerChoiceInGame_positive,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // <1>
                                // 선택한 라디오 버튼의 위치로
                                // 게임에 참가한 친구 목록에 추가 (1명만 됨)
                                ArrayList<FriendData> participatedFriendListInGame = new ArrayList<>();
                                participatedFriendListInGame.add(friendDataArrayList.get(selectedIndex[0]));

                                // <2> 게임 시작
                                Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);

                                // sessionManger : set ( userData, participatedFriendListInGame )
                                SessionManager.setUserDataFromIntent(intent, userData);
                                SessionManager.setParticipatedFriendListInGameFromIntent(intent, participatedFriendListInGame);

                                // 이동
                                startActivity(intent);

                            }
                        }
                )
                .setNegativeButton(
                        R.string.main_dialog_playerChoiceInGame_negative,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                )
                .show();

    } // End of method [showDialogOfParticipantsSingleSelection]


    /**
     * AlertDialog : 게임에 참가하는 친구를 선택한다. 최대 인원(NUMBER_OF_SELECTABLE_MAX_FRIEND)까지만 선택할 수 있다.
     */
    private void showDialogOfPlayerMultiChoice() {

        // 내가 등록한 친구 목록으로 alertDialog 에 넣을 친구 이름 만들기
        final String[] friendNameList = createFriendNameList();

        // 체크한 아이템이 무엇인가요?
        final boolean[] isCheckedList = new boolean[friendDataArrayList.size()];
        Arrays.fill(isCheckedList, false);

        // <사용자 확인>
        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_playerChoiceInGame_title)
                .setMultiChoiceItems(
                        friendNameList,
                        null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                isCheckedList[which] = isChecked;

                            }
                        }
                )
                .setPositiveButton(
                        R.string.main_dialog_playerChoiceInGame_positive,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // <1>
                                // isCheckedList 가 true 인 친구를
                                // 게임에 참가한 친구 목록에 추가하기
                                ArrayList<FriendData> participatedFriendListInGame = new ArrayList<>();
                                for (int index = 0; index < isCheckedList.length; index++) {
                                    DeveloperLog.printLog(CLASS_LOG_SWITCH,
                                            CLASS_NAME,
                                            "< " + index + " > isChecked : " + isCheckedList[index]
                                    );
                                    if (isCheckedList[index]) {
                                        participatedFriendListInGame.add(
                                                friendDataArrayList.get(index)
                                        );
                                    }

                                }

                                DeveloperLog.printLogFriendData(
                                        CLASS_LOG_SWITCH,
                                        CLASS_NAME,
                                        participatedFriendListInGame
                                );

                                // <2>
                                // 게임에 참가할 수 있는 친구의 수가 3명 이하 인지 확인
                                if (participatedFriendListInGame.isEmpty()) {
                                    // <사용자 알림>
                                    Toast.makeText(
                                            getApplicationContext(),
                                            R.string.main_noticeUser_choicePlayer,
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }
                                if (!(participatedFriendListInGame.size() <= NUMBER_OF_SELECTABLE_MAX_FRIEND)) {

                                    String notice = new StringBuilder()
                                            .append("선택할 수 있는 최대 인원은 ")
                                            .append(NUMBER_OF_SELECTABLE_MAX_FRIEND)
                                            .append(" 명 입니다.")
                                            .toString();

                                    // <사용자 알림>
                                    Toast.makeText(
                                            getApplicationContext(),
                                            notice,
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }

                                // <2>
                                // 게임 시작
                                Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);

                                // sessionManger : set ( userData, participatedFriendListInGame )
                                SessionManager.setUserDataFromIntent(intent, userData);
                                SessionManager.setParticipatedFriendListInGameFromIntent(intent, participatedFriendListInGame);

                                // 이동
                                startActivity(intent);
                            }
                        }
                )
                .setNegativeButton(
                        R.string.main_dialog_playerChoiceInGame_negative,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                )
                .show();
    } // End of method [showDialogOfPlayerMultiSelection]


    /**
     * AlertDialog : 사용자 등록을 하기위해 이동한다. ( UserManagerActivity 의 UserInputFragment )
     */
    private void showDialogOfUserRegisterCheck() {
        final String METHOD_NAME = "[showDialogOfUserRegisterCheck] ";

        // <사용자 알림>
        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_userRegisterCheck_title)
                .setMessage(R.string.main_dialog_userRegisterCheck_message)
                .setPositiveButton(R.string.main_dialog_userRegisterCheck_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "나의 정보가 없어서 등록하러 이동합니다.");

                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // sessionManager : set ( pageNumber )
                        SessionManager.setPageNumberFromIntent(intent, UserManagerActivity.USER_INPUT_FRAGMENT);

                        // 이동
                        startActivity(intent);

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
     * AlertDialog : 친구등록을 하기위해 이동한다. ( UserManagerActivity 의 UserFriendFragment )
     */
    private void showDialogOfFriendRegisterCheck() {
        final String METHOD_NAME = "[showDialogOfFriendRegisterCheck] ";

        // <사용자 알림>
        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_friendRegisterCheck_title)
                .setMessage(R.string.main_dialog_friendRegisterCheck_message)
                .setPositiveButton(R.string.main_dialog_friendRegisterCheck_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "등록된 친구가 없어서 추가하러 이동합니다.");

                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);

                        // sessionManager : set ( userData, pageNumber )
                        SessionManager.setUserDataFromIntent(intent, userData);
                        SessionManager.setPageNumberFromIntent(intent, UserManagerActivity.USER_FRIEND_FRAGMENT);

                        // 이동
                        startActivity(intent);

                    }
                })
                .setNegativeButton(R.string.main_dialog_friendRegisterCheck_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    } // End of method [showDialogOfFriendRegisterCheck]


    /**
     * friendDataArrayList 에서 name 만 뽑아서 String 배열로 반환한다.
     *
     * @return 친구 이름 목록
     */
    private String[] createFriendNameList() {

        String[] friendNameList = new String[friendDataArrayList.size()];

        for (int index = 0; index < friendDataArrayList.size(); index++) {
            friendNameList[index] = friendDataArrayList.get(index).getName();
        }

        return friendNameList;
    }

}