package com.skyman.billiarddata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.ListView.BilliardLvManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;


public class BilliardDisplayActivity extends AppCompatActivity {

    // constant
    private final String CLASS_NAME_LOG = "[Ac]_BilliardDisplayActivity";

    // instance variable
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private BilliardDbManager billiardDbManager = null;
    private PlayerDbManager playerDbManager = null;
    private UserData userData = null;
    private ArrayList<BilliardData> billiardDataArrayList = null;

    // instance variable
    private ListView allBilliardData;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreate] ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

        // SessionManager 에서 userData 가져오기
        this.userData = SessionManager.getUserDataInIntent(getIntent());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "Intent 를 통해 user Data 를 가져왔습니다. 내용을 보겠습니다.");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);


        // [method]createDbManager : user, friend, billiard 테이블 메니저 생성 및 초기화
        createDbManager();

        // [method]mappingOfWidget : activity_billiard_display layout 의 widget 을 mapping
        mappingOfWidget();

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // [method]mappingAllBilliardDataToListView : billiard 테이블에서 가져온 모든 데이터를 ListView 에 뿌려준다.
            mappingAllBilliardDataToListView();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없으므로 가져올 billiardData 도 없습니다.");
        } // [check 1]

        // [lv/C]Button : delete click listener
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogWhetherDelete : billiard 테이블의 내용을 삭제하며 user 와 user 의 friend 데이터를 초기화한다.
                showDialogWhetherDelete();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final String METHOD_NAME = "[onDestroy] ";

        // [check 1] : user 테이블 메니저가 생성되었다.
        if (this.userDbManager != null) {

            // [iv/C]UserDbManager : user 테이블 메니저를 종료한다.
            this.userDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 테이블 메니저가 생성되지 않았습니다.");
        } // [check 1]

        // [check 2] : friend 테이블 메니저가 생성되었다.
        if (this.friendDbManager != null) {

            // [iv/C]FriendDbManager : friend 테이블 메니저를 종료한다.

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friend 테이블 메니저가 생성되지 않았습니다.");
        } // [check 2]

        // [check 3] : billiard 테이블 메니저가 생성되었다.
        if (this.billiardDbManager != null) {

            // [iv/C]BilliardDbManager : billiard 테이블 메니저를 종료한다.
            this.billiardDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블 메니저가 생성되지 않았습니다.");
        } // [check 3]

        // [check 4] : player 테이블 메니저가 생성되었다.
        if (this.playerDbManager != null) {

            // [iv/C]PlayerDbManager : player 테이블 메니저를 종료한다.
            this.playerDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 테이블 메니저가 생성되지 않았습니다.");
        } // [check 4]

    } // End of method [onDestroy]

    /**
     * [method] user, friend, billiard 테이블 메니저 생성
     */
    private void createDbManager() {

        // [iv/C]UserDbManager : user 테이블 메니저 생성 및 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블 메니저 생성 및 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

        // [iv/C]BilliardDbManger : billiard 테이블 메니저 생성 및 초기화
        this.billiardDbManager = new BilliardDbManager(this);
        this.billiardDbManager.initDb();

        // [iv/C]PlayerDbManager : player 테이블 메니저 생성 및 초기화
        this.playerDbManager = new PlayerDbManager(this);
        this.playerDbManager.initDb();

    } // End of method [createDbManager]


    /**
     * [method] activity_billiard_display layout 의 widget 을 mapping
     */
    private void mappingOfWidget() {

        // [iv/C]ListView : allBilliardData mapping
        this.allBilliardData = (ListView) findViewById(R.id.billiardDisplay_listView_allBilliardData);

        // [iv/C]Button : delete mapping
        this.delete = (Button) findViewById(R.id.billiardDisplay_button_delete);

    } // End of method [mappingOfWidget]


    /**
     * [method] ListView 에 모든 billiardData 를 보여준다.
     */
    private void mappingAllBilliardDataToListView() {

        final String METHOD_NAME = "[mappingAllBilliardDataToListView] ";

        // [lv/C]ArrayList<PlayerData> : user 의 id 와 name 으로 참가한 모든 경기를 가져오기 / player 테이블에서 playerId 와 playerName 으로 찾아야 한다.
        ArrayList<PlayerData> playerDataArrayList = this.playerDbManager.loadAllContentByPlayerIdAndPlayerName(this.userData.getId(), this.userData.getName());

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "가져온 playerDataArrayLIst 를 확인하겠습니다.");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

        // ==============================================================================================================================================

        // [iv/C]ArrayList<BilliardData> : 위의 playerDataArrayList 의 billiardCount 로 가져온 BilliardData 를 담을
        this.billiardDataArrayList = new ArrayList<>();

        // [cycle 1] : 참가한 경기 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<BilliardData> : playerData 의 billiardCount 로 가져온 BilliardData 를 추가한다.
            this.billiardDataArrayList.add(this.billiardDbManager.loadAllContentByCount(playerDataArrayList.get(index).getBilliardCount()));

        } // [cycle 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "가져온 billiardDataArrayList 의 데이터를 알아보겠습니다.");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, this.billiardDataArrayList);

        // ==============================================================================================================================================

        // [lv/C]BilliardLvManager : billiard 테이블의 모든 내용을 가져와 list view 와 연결하는 메니저 객체 생성
        BilliardLvManager billiardLvManager = new BilliardLvManager(this.allBilliardData, this.billiardDbManager, this.userDbManager, this.playerDbManager, this.friendDbManager);

        // [lv/C]BilliardLvManager : userData 의 id 로 모든 billiardData 와 userData 의 name 을 추가한다.
        billiardLvManager.addData(billiardDataArrayList, this.userData);

        // [lv/C]BilliardLvManager : allBilliardList 를 adapter 로 연결하기
        billiardLvManager.setListViewToAdapter();

        // [iv/C]ListView : allBilliardData 에 있는 개수로 마지막 item 을 선택하여 보여주기
        this.allBilliardData.setSelection(this.allBilliardData.getCount());

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "allBilliardData 에 총 item 개수는 : " + this.allBilliardData.getCount());

    } // End of method [mappingAllBilliardDataToListView]


    /**
     * [method] 삭제 버튼을 눌렀을 때 진짜 삭제할 건지 물어본다.
     */
    private void showDialogWhetherDelete() {

        // [lv/C]AlertDialog : Builder 객체를 생성한다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : Builder 로 초기값 설정
        builder.setTitle(R.string.billiardDisplay_dialog_allDataDelete_title)
                .setMessage(R.string.billiardDisplay_dialog_allDataDelete_message)
                .setPositiveButton(R.string.billiardDisplay_dialog_allDataDelete_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method]setClickListenerOfDeleteButton : billiard 테이블의 내용을 삭제하며 user 와 user 의 friend 데이터를 초기화한다.
                        setClickListenerOfDeleteButton();
                    }
                })
                .setNegativeButton(R.string.billiardDisplay_dialog_allDataDelete_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogWhetherDelete]


    /**
     * [method] delete Click Listener 설정
     */
    private void setClickListenerOfDeleteButton() {

        final String METHOD_NAME = "[setClickListenerOfDeleteButton] ";

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // [cycle 1] : billiardDataArrayLst 의 개수만큼
            for (int playerIndex = 0; playerIndex < billiardDataArrayList.size(); playerIndex++) {


                int temp = this.playerDbManager.deleteContentByBilliardCount(this.billiardDataArrayList.get(playerIndex).getCount());

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 테이블이 몇개 지워졌나요? = " + temp);

            } // [cycle 1]


            // [cycle 2] : billiardDataArrayList 의 개수만큼
            for (int billiardIndex = 0; billiardIndex < billiardDataArrayList.size(); billiardIndex++) {

                int temp = this.billiardDbManager.deleteContentByCount(this.billiardDataArrayList.get(billiardIndex).getCount());

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블이 몇 개 지워졌나요? = " + temp);

            } // [cycle 2]


            // [lv/i]initDataOfUser : 해당 userId 로 user 의 데이터를 초기화한다.
            int initDataOfUser = this.userDbManager.updateContent(this.userData.getId(),
                    0,
                    0,
                    0,
                    0,
                    0);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 의 데이터가 초기화 되었습니다. : " + initDataOfUser);


            // [lv/i] : 해당 userId 로 모든 Friend 데이터를 초기값으로 변경 - 친구와 했던 모든 BilliardData 가 삭제되므로 모두 초기값으로 변경해야 한다.
            int initDataOfFriend = this.friendDbManager.updateContentByUserId(this.userData.getId(),
                    0,
                    0,
                    0,
                    0,
                    0);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 friend 의 데이터가 초기화 되었습니다. : " + initDataOfFriend);


            //=======================================================================================================


            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "======================== userData 초기화 후 변경 값 확인 =====================");
            // [iv/C]UserData : 위에서 갱신된 user 의 데이터를 userId 로 가져온다.
            this.userData = this.userDbManager.loadContent(this.userData.getId());
            DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);


            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "======================== friend 초기화 후 변경 값 확인 =====================");
            ArrayList<FriendData> tempFriendDataArrayList = this.friendDbManager.loadAllContentByUserId(this.userData.getId());
            DeveloperManager.displayToFriendData(CLASS_NAME_LOG, tempFriendDataArrayList);

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "======================== player 초기화 후 변경 값 확인 =====================");
            ArrayList<PlayerData> tempPlayerDataArrayList = this.playerDbManager.loadAllContentByPlayerIdAndPlayerName(this.userData.getId(), this.userData.getName());
            DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, tempPlayerDataArrayList);

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "======================== billiard 초기화 후 변경 값 확인 =====================");
            ArrayList<BilliardData> tempBilliardDataArrayList = this.billiardDbManager.loadAllContent();
            DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, tempBilliardDataArrayList);


            // [method]mappingAllBilliardDataToListView : billiard 테이블에서 가져온 모든 데이터를 ListView 에 뿌려준다.
            mappingAllBilliardDataToListView();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없으므로 삭제 및 초기화할 필요가 없습니다.");
        } // [check 1]

    } // End of method [setClickListenerOfDeleteButton]


    /**
     * [method] 해당 문자열을 toast 로 보여준다.
     */
    private void toastHandler(String content) {

        // [lv/C]Toast : toast 객체 생성
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);

        // [lv/C]Toast : 위에서 생성한 객체를 보여준다.
        myToast.show();

    } // End of method [toastHandler]
}