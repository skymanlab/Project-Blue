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
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;


public class BilliardDisplayActivity extends AppCompatActivity {

    // constant
    private final String CLASS_NAME_LOG ="[Ac]_BilliardDisplayActivity";

    // instance variable
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private BilliardDbManager billiardDbManager = null;
    private UserData userData = null;

    // instance variable
    private ListView allBilliardData;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

        final String METHOD_NAME= "[onCreate] ";

        // [lv/C]Intent : 전 Activity 에서 전달 된 Intent 가져오기
        Intent intent = getIntent();

        // [iv/C]UserData : 저장된 user 로 이 화면에 들어왔다.
        this.userData = SessionManager.getUserDataInIntent(intent);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME +"Intent 를 통해 user Data 를 가져왔습니다. 내용을 보겠습니다.");
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
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME +"userData 가 없으므로 가져올 billiardData 도 없습니다.");
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

        final String METHOD_NAME= "[onDestroy] ";

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
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블 메니저가 생성되지 않았습니다.");
        } // [check 2]

        // [check 3] : billiard 테이블 메니저가 생성되었다.
        if (this.billiardDbManager != null) {

            // [iv/C]BilliardDbManager : billiard 테이블 메니저를 종료한다.
            this.billiardDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블 메니저가 생성되지 않았습니다.");
        } // [check 3]

    } // End of method [onDestroy]


    /**
     * [method] user, friend, billiard 테이블 메니저 생성
     */
    private void createDbManager() {

        // [iv/C]UserDbManager : uset 테이블 메니저 생성 및 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블 메니저 생성 및 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

        // [iv/C]BilliardDbManger : billiard 테이블 메니저 생성 및 초기화
        this.billiardDbManager = new BilliardDbManager(this);
        this.billiardDbManager.initDb();

    } // End of method [createDbManager]


    /**
     * [method] activity_billiard_display layout 의 widget 을 mapping
     */
    private void mappingOfWidget() {

        // [iv/C]ListView : allBilliardData mapping
        this.allBilliardData = (ListView) findViewById(R.id.billiard_display_lv_all_billiard_data);

        // [iv/C]Button : delete mapping
        this.delete = (Button) findViewById(R.id.billiard_display_bt_delete);

    } // End of method [mappingOfWidget]


    /**
     * [method] ListView 에 모든 billiardData 를 보여준다.
     */
    private void mappingAllBilliardDataToListView() {

        final String METHOD_NAME= "[mappingAllBilliardDataToListView] ";

        // [lv/C]BilliardLvManager : billiard 테이블의 모든 내용을 가져와 list view 와 연결하는 메니저 객체 생성
        BilliardLvManager billiardLvManager = new BilliardLvManager(this.allBilliardData, this.billiardDbManager);

        // [lv/C]BilliardLvManager : userData 의 id 로 모든 billiardData 와 userData 의 name 을 추가한다.
//        billiardLvManager.addData(this.billiardDbManager.loadAllContentByUserID(this.userData.getId()), this.userData.getName());

        // [lv/C]BilliardLvManager : allBilliardList 를 adapter 로 연결하기
        billiardLvManager.setListViewToAdapter();

        // [iv/C]ListView : allBilliardData 에 있는 개수로 마지막 item 을 선택하여 보여주기
        this.allBilliardData.setSelection(this.allBilliardData.getCount());

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "allBilliardData 에 총 item 개수는 : " + this.allBilliardData.getCount());

    } // End of method [mappingAllBilliardDataToListView]


    /**
     * [method] 삭제 버튼을 눌렀을 때 진짜 삭제할 건지 물어본다.
     *
     */
    private void showDialogWhetherDelete() {

        // [lv/C]AlertDialog : Builder 객체를 생성한다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : Builder 로 초기값 설정
        builder.setTitle(R.string.ad_billiard_display_delete_title)
                .setMessage(R.string.ad_billiard_display_delete_message)
                .setPositiveButton(R.string.ad_billiard_display_delete_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method]setClickListenerOfDeleteButton : billiard 테이블의 내용을 삭제하며 user 와 user 의 friend 데이터를 초기화한다.
                        setClickListenerOfDeleteButton();
                    }
                })
                .setNegativeButton(R.string.ad_billiard_display_delete_bt_negative, new DialogInterface.OnClickListener() {
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

        final String METHOD_NAME= "[setClickListenerOfDeleteButton] ";

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // [lv/i]methodResult : 해당 userId 로 billiard 테이블의 모든 내용을 삭제하고 그 결과 값을 받는다.
            int deleteBilliard = this.billiardDbManager.deleteAllContentByUserId(this.userData.getId());

            // [check 2] : 삭제 결과가 성공이다.
            if (deleteBilliard >= 0) {
                toastHandler("모든 내용이 삭제되었습니다.");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블에서 모든 내용이 삭제 성공하였습니다. 삭제 개수 : " + deleteBilliard);
            } else if (deleteBilliard == -1) {
                toastHandler("삭제 실패하였습니다.");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블에서 삭제가 실패하였습니다. : " + deleteBilliard);
            } else {
                toastHandler("모든 내용이 삭제되었습니다. 뭐지요? ");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "뭘까요? : " + deleteBilliard);
            } // [check 2]

            // [lv/i]initDataOfUser : 해당 userId 로 user 의 데이터를 초기화한다.
            int initDataOfUser = this.userDbManager.updateContent(this.userData.getId(),
                    0,
                    0,
                    0,
                    0,
                    0);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 의 데이터가 초기화 되었습니다. : " + initDataOfUser);

            // [iv/C]UserData : 위에서 갱신된 user 의 데이터를 userId 로 가져온다.
            this.userData = this.userDbManager.loadContent(this.userData.getId());
            DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);

            // [lv/i] : 해당 userId 로 모든 Friend 데이터를 초기값으로 변경 - 친구와 했던 모든 BilliardData 가 삭제되므로 모두 초기값으로 변경해야 한다.
            int initDataOfFriend = this.friendDbManager.updateContentByUserId(this.userData.getId(),
                    0,
                    0,
                    0,
                    0,
                    0);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 friend 의 데이터가 초기화 되었습니다. : " + initDataOfFriend);

            // [method]mappingAllBilliardDataToListView : billiard 테이블에서 가져온 모든 데이터를 ListView 에 뿌려준다.
            mappingAllBilliardDataToListView();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없으므로 삭제 및 초기화할 필요가 없습니다.");
        } // [check 1]

    } // End of method [setClickListenerOfDeleteButton]


    /**
     * [method] billiard 테이블에 저장된 모든 데이터를 custom layout 에 adapter 로 연결하여 뿌려준다.
     */
    private void displayBilliardData() {

        final String METHOD_NAME = "[displayBilliardData] ";

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // [lv/C]BilliardLvManager : 위 의 내용을 토대로 custom list view 에 뿌리는 메니저 객체 생성
            BilliardLvManager billiardLvManager = new BilliardLvManager(this.allBilliardData, this.billiardDbManager);

            // [lv/C]BilliardLvManager : userData 의 id 로 모든 billiardData 와 userData 의 name 을 추가한다.
//            billiardLvManager.addData(this.billiardDbManager.loadAllContentByUserID(this.userData.getId()), this.userData.getName());

            // [lv/C]BilliardLvManager : allBilliardList 를 adapter 로 연결하기
            billiardLvManager.setListViewToAdapter();

            // [iv/C]ListView : allBilliardData 에 있는 개수로 마지막 item 을 선택하여 보여주기
            this.allBilliardData.setSelection(this.allBilliardData.getCount());

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "allBilliardData 에 총 item 개수는 : " + this.allBilliardData.getCount());


        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없습니다.");
        } // [check 1]

    } // End of method [displayBilliardData]



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