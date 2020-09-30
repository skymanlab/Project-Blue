package com.skyman.billiarddata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
     * 용어 정리
     * 1. declaration = 선언
     * 2. create = 생성성
     * 3. colon = :
     * */

    // value : FriendDbManager
    private FriendDbManager friendDbManager;
    private ArrayList<FriendData> friendDataArrayList;

    // value : UserDbManager
    private UserDbManager userDbManager;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FriendDbManager : 친구 목록을 가져오기 위한
        friendDbManager = new FriendDbManager(this);
        friendDbManager.init_db();

        // UserDbManager : 입력된 정보가 있는지 검사
        userDbManager = new UserDbManager(this);
        userDbManager.init_db();
        userData = userDbManager.load_content();

        // Button : object declaration
        Button billiardInput = (Button) findViewById(R.id.main_bt_billiard_input);
        Button billiardDisplay = (Button) findViewById(R.id.main_bt_billiard_display);
        Button userManager = (Button) findViewById(R.id.main_bt_user_manager);
        Button statisticsManager = (Button) findViewById(R.id.main_bt_statistics_manager);
        Button appInfo = (Button) findViewById(R.id.main_bt_app_info);

        // Button 1 : inputData setting - '데이터 입력'
        billiardInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FriendData : friendDbManager 에서 load_contents 로 불러온 내용이 담길 배열
                friendDataArrayList = friendDbManager.load_contents();

                // check : 나의 정보가 있을 때
                if (userData != null) {
                    // check : friend 테이블에 저장 된 내용이 있다.
                    if (friendDataArrayList.size() != 0) {
                        // AlertDialog : Builder 로 생성
                        DeveloperManager.displayLog("[Ac] MainActivity", "[inputData button] 모든 정보가 입력되어 친구를 선택합니다.");
                        showAlertToFriendList();
                    } else {
                        // method : 저장 된 친구가 없다.
                        showAlertToFriendData();
                    }
                } else {
                    // method : 저장 된 나의 정보가 없을 때
                    showAlertToUserData();
                }
            }
        });

        // Button 2 : viewDAta setting - '데이터 보기'
        billiardDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                startActivity(intent);
            }
        });

        // Button 3 : userManager setting - '나의 정보'
        userManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                startActivity(intent);
            }
        });

        // Button 5 : statistics setting - '통계 정보'
        statisticsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticsManagerActivity.class);
                startActivity(intent);
            }
        });

        // Button 4 : appInfo setting - '어플 정보'
        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // UserData : 다시 userData 받아오기
        userData = userDbManager.load_content();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDbManager.closeUserDbHelper();
        friendDbManager.closeFriendDbHelper();
    }



    /*                                      private method
     *   ============================================================================================
     *  */

    /* method : BilliardInputActivity 에 들어가기 전 player 선택 */
    private void showAlertToFriendList() {


        // ArrayList<String> : 친구 이름만 담길 배열
        final ArrayList<String> friendList = new ArrayList<>();

        // cycle : friendDataArrayList 의 size 만큼 돌며, 이름만 friendList 에 담기
        for (int position = 0; position < friendDataArrayList.size(); position++) {
            friendList.add(friendDataArrayList.get(position).getName());
        }

        // CharSequence[] : friendList 를 AlertDialog>Builder - setSingleChoiceItems 의 매개변수로 넣기 위해 변화
        final CharSequence[] items = friendList.toArray(new String[friendList.size()]);

        // int[] : 선택된 친구의 index 를 받아오기 위한, 초기값은 0
        final int[] selectedIndex = {0};

        // AlertDialog.Builder : 다이어로그를 셋팅
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ad_main_friend_list_title)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex[0] = which;
                    }
                })
                .setPositiveButton(R.string.ad_main_bt_friend_list_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // check : item 의 size 가 0 이상일때
                        DeveloperManager.displayLog("[Ac] MainActivity", "[showAlertToFriendList] 친구가 있는지 검사합니다.");
                        if (items.length > 0) {
                            DeveloperManager.displayLog("[Ac] MainActivity", "[showAlertToFriendList] 모든 친구를 출력합니다.");
                            DeveloperManager.displayToFriendData("[Ac] MainActivity", friendDataArrayList.get(selectedIndex[0]));

                            // Intent : 선택 된 친구 값을 Intent 에 포함하여 BilliardInputActivity 로 이동합니다.
                            Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);
                            intent.putExtra("player", friendDataArrayList.get(selectedIndex[0]));
                            startActivityForResult(intent, 101);
                        } else {
                            DeveloperManager.displayLog("[Ac] MainActivity", "[showAlertToFriendList] 친구 목록이 없습니다.");
                        }
                    }
                })
                .setNegativeButton(R.string.ad_main_bt_friend_list_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    /* method : AlertDialog - 등록된 유저가 없어서 화면이동을 물어보는  */
    private void showAlertToUserData(){
        // AlertDialog.Builder :
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ad_main_user_data_title)
                .setMessage(R.string.ad_main_user_data_message)
                .setPositiveButton(R.string.ad_main_bt_user_data_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Intent : pageNumber 에 해당 페이지 번호 값을 넣어서 화면 이동
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                        intent.putExtra("pageNumber", 0);
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog("[Ac] MainActivity", "[showAlertToUserData] 나의 정보가 없어서 등록하러 이동합니다.");
                    }
                })
                .setNegativeButton(R.string.ad_main_bt_user_data_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /* method : AlertDialog - 등록된 친구가 없어서 화면이동을 물어보는  */
    private void showAlertToFriendData(){
        // AlertDialog.Builder :
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ad_main_friend_data_title)
                .setMessage(R.string.ad_main_friend_data_message)
                .setPositiveButton(R.string.ad_main_bt_friend_data_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Intent : pageNumber 에 해당 페이지 번호 값을 넣어서 화면 이동
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                        intent.putExtra("pageNumber", 2);
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog("[Ac] MainActivity", "[showAlertToFriendData] 등록된 친구가 없어서 추가하러 이동합니다.");
                    }
                })
                .setNegativeButton(R.string.ad_main_bt_friend_data_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}