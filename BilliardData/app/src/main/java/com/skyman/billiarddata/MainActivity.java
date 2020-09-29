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
import com.skyman.billiarddata.management.projectblue.ProjectBlueDatabaseManager;
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
        Button appInfo = (Button) findViewById(R.id.main_bt_app_info);

        // Button 1 : inputData setting - '데이터 입력'
        billiardInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FriendData : friendDbManager 에서 load_contents 로 불러온 내용이 담길 배열
                friendDataArrayList = friendDbManager.load_contents();

                // check : 나의 정보가 있을 때
                if (userData != null) {
                    // check : 친구 목록에 저장된 친구가 있을 때
                    if (friendDataArrayList.size() != 0) {
                        // AlertDialog : Builder 로 생성
                        DeveloperManager.displayLog("MainActivity", "모든 정보가 입력되어 친구를 선택합니다.");
                        showAlert();
                    } else {
                        // 저장 된 친구가 없을 때
                        showAlertToFriendData();
                    }
                } else {
                    // 저장 된 나의 정보가 없을 때
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

        // Button 4 : appInfo setting - '어플 정보'
        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);
                startActivity(intent);
            }
        });

        /*
         * 1. 유저 데이터가 있는지 확인
         * 2. 없으면 일단 user 기본 정보 입력하는 activity 로 이동 */


        // UserData :
        if (userData == null) {
            DeveloperManager.displayLog("MainActivity", "등록된 당신의 정보가 없습니다. 입력해주세요. 이동합니다.");
        } else {
            DeveloperManager.displayLog("MainActivity", "등록된 정보가 있습니다. 다음 단계로 넘어가 주세요.");
        }
    }

    /* method : BilliardInputActivity 에 들어가기 전 player 선택 */
    private void showAlert() {


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
        builder.setTitle("친구 목록")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex[0] = which;
                    }
                })
                .setPositiveButton("시작", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // check : item 의 size 가 0 이상일때
                        DeveloperManager.displayLog("MainActivity", "친구가 있는지 검사중");
                        if (items.length > 0) {
                            Log.d("MainActivity", "item : " + items[selectedIndex[0]]);
                            DeveloperManager.displayToFriendData("MainActivity", friendDataArrayList.get(selectedIndex[0]));
                            Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);
                            intent.putExtra("player", friendDataArrayList.get(selectedIndex[0]));
                            startActivityForResult(intent, 101);
                        } else {
                            DeveloperManager.displayLog("MainActivity", "친구 목록이 없습니다. 친구를 입력해주세요.");

                        }

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // UserData : 다시 userData 받아오기
        userData = userDbManager.load_content();

        DeveloperManager.displayLog("MainActivity", "화면이 없어졌다 back 키로 돌아왔습니다.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        friendDbManager.closeFriendDbHelper();
    }

    /* method : AlertDialog - 등록된 유저가 없어서 화면이동을 물어보는  */
    private void showAlertToUserData(){
        // AlertDialog.Builder :
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("다음 화면 이동")
                .setMessage("유저 등록이 되어있지 않습니다. 등록을 위해 이동하겠습니까?")
                .setPositiveButton("다음화면", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Intent : pageNumber 에 해당 페이지 번호 값을 넣어서 화면 이동
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                        intent.putExtra("pageNumber", 0);
                        finish();
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog("MainActivity", "나의 정보가 없어서 나의 정보를 저장하러 이동합니다.");
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
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
        builder.setTitle("다음 화면 이동")
                .setMessage("친구 등록이 되어있지 않습니다. 등록을 위해 이동하겠습니까?")
                .setPositiveButton("다음화면", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Intent : pageNumber 에 해당 페이지 번호 값을 넣어서 화면 이동
                        Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                        intent.putExtra("pageNumber", 2);
                        finish();
                        startActivityForResult(intent, 101);
                        DeveloperManager.displayLog("MainActivity", "등록된 친구가 없어서 추가하러 이동합니다.");
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}