package com.skyman.billiarddata;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.dialog.BilliardLvExplainDialog;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.etc.SessionManager;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.listView.BilliardLvAdapter2;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.table.user.data.UserData;
import com.skyman.billiarddata.table.user.database.UserDbManager2;

import java.util.ArrayList;


public class BilliardDisplayActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "BilliardDisplayActivity";

    // instance variable : session
    private UserData userData = null;

    // instance variable : load database
    private ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

    // instance variable : database manager
    private AppDbManager appDbManager;

    // instance variable : widget
    private ImageView more;
    private ListView billiardListView;
    private Button delete;

    // instance variable
    private BilliardLvAdapter2 billiardLvAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

        // session manager : userData
        this.userData = SessionManager.getUserDataFromIntent(getIntent());

        // AppDbManager
        initAppDbManager();

        // Widget : connect -> init
        connectWidget();
        initWidget();


    }

    @Override
    protected void onDestroy() {

        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "onDestroy 실행 ======================================");
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

        this.more = (ImageView) findViewById(R.id.billiardDisplay_button_more);

        this.billiardListView = (ListView) findViewById(R.id.billiardDisplay_listView_billiardList);

        // [iv/C]Button : delete mapping
        this.delete = (Button) findViewById(R.id.billiardDisplay_button_delete);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        if (this.userData != null) {

            // load : billiardDataArrayList
            loadDataOfPlayerAndBilliard();

            // init widget : billiardListView
            initWidgetOfBilliardListView();

        } else {
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "userData 가 없으므로 가져올 billiardData 도 없습니다.");
        } // [check 1]

        // widget (more) : click listener
        more.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(BilliardDisplayActivity.this, v);
                        MenuInflater inflater = popupMenu.getMenuInflater();
                        inflater.inflate(R.menu.billliard_display_menu_1, popupMenu.getMenu());

                        popupMenu.setOnMenuItemClickListener(
                                new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.billiardDisplay_menu1_billiardLvExplain:
                                                BilliardLvExplainDialog dialog = BilliardLvExplainDialog.newInstance();
                                                dialog.show(getSupportFragmentManager(), BilliardLvExplainDialog.class.getSimpleName());
                                                return true;
                                            default:
                                                return false;
                                        }
                                    }
                                }
                        );
                        popupMenu.show();
                    }
                }
        );

        // widget (delete) : click listener
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClickListenerOfDeleteButton();
                    }
                }
        );
    }


    private void loadDataOfPlayerAndBilliard() {

        final ArrayList<PlayerData>[] tempPlayerDataArrayList = new ArrayList[]{null};

        // 나의 id 와 name 으로 (중복을 피하기 위해서)
        // 내가 참가한 모든 경기에 대한 player list 가져오기
        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

//                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "=============>>> playerDataArrayList");
                        tempPlayerDataArrayList[0] = playerDbManager2.loadAllContentByPlayerIdAndPlayerName(userData.getId(), userData.getName());

//                        DeveloperLog.printLogPlayerData(CLASS_LOG_SWITCH, CLASS_NAME, tempPlayerDataArrayList[0]);
                    }
                }
        );

        // 위에서 가져온 player list 가 담긴 playerDataArrayList 에서
        // 각각의 playerData 의 billiardCount 로
        // 내가 참가한 게임 목록을 가져온다. (billiardDataArrayList 에 담긴다.)
        appDbManager.requestBilliardQuery(
                new AppDbManager.BilliardQueryRequestListener() {
                    @Override
                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

//                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "=============>>> billiardDataArrayList");

                        billiardDataArrayList = new ArrayList<>();

                        for (int index = 0; index < tempPlayerDataArrayList[0].size(); index++) {
                            billiardDataArrayList.add(
                                    billiardDbManager2.loadContentByCount(tempPlayerDataArrayList[0].get(index).getBilliardCount())
                            );
                        }

//                        DeveloperLog.printLogBilliardData(CLASS_LOG_SWITCH, CLASS_NAME, billiardDataArrayList);
                    }
                }
        );

    } // End of method [loadDataOfPlayerAndBilliard]


    private void initWidgetOfBilliardListView() {

        billiardLvAdapter2 = new BilliardLvAdapter2(getSupportFragmentManager(), userData, billiardDataArrayList, appDbManager);

        billiardListView.setAdapter(billiardLvAdapter2);

        billiardListView.setSelection(billiardListView.getCount());

    }


    /**
     * [method] delete Click Listener 설정
     */
    private void setClickListenerOfDeleteButton() {
        final String METHOD_NAME = "[setClickListenerOfDeleteButton] ";

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // <사용자 확인>
            new AlertDialog.Builder(this)
                    .setTitle(R.string.billiardDisplay_dialog_allDataDelete_title)
                    .setMessage(R.string.billiardDisplay_dialog_allDataDelete_message)
                    .setPositiveButton(R.string.billiardDisplay_dialog_allDataDelete_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    deleteGameRelatedAllData();

                                }
                            }
                    )
                    .setNegativeButton(R.string.billiardDisplay_dialog_allDataDelete_negative,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }
                    )
                    .show();

        } else {

            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "userData 가 없으므로 삭제 및 초기화할 필요가 없습니다.");

            // <사용자 알림>
            Toast.makeText(
                    this,
                    R.string.billiardDisplay_noticeUser_noData,
                    Toast.LENGTH_SHORT
            ).show();

        } // [check 1]

    } // End of method [setClickListenerOfDeleteButton]


    private void deleteGameRelatedAllData() {
        final String METHOD_NAME = "[deleteGameRelatedAllData] ";

        // <과정>
        // 1. billiardDataArrayList 의 count 에 해당하는 player 테이블의 내용을 모두 삭제
        // 2. billiardDataArrayList 의 count 에 해당하는 billiard 테이블의 내용을 모두 삭제
        // 3. 나의 userData 에서 user 테이블의 모든 내용을 초기 상태의 데이터로 변경한다.
        // 4. 나의 친구들의 모든 데이터를 초기 상태의 데이터로 변경한다.
        // 5. 변경된 내용을 adapter 를 이용하여 ListView 반영하기

        // <1>
        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {

                    int numberOfDeletedRows = 0;

                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

                        for (int index = 0; index < billiardDataArrayList.size(); index++) {
                            numberOfDeletedRows += playerDbManager2.deleteContentByBilliardCount(billiardDataArrayList.get(index).getCount());
                        }

                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "player 테이블에서 총 " + numberOfDeletedRows + " 개의 열을 삭제하였습니다.");
                    }
                }
        );

        // <2>
        appDbManager.requestBilliardQuery(
                new AppDbManager.BilliardQueryRequestListener() {
                    @Override
                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                        int numberOfDeletedRows = 0;

                        for (int index = 0; index < billiardDataArrayList.size(); index++) {
                            numberOfDeletedRows += billiardDbManager2.deleteContentByCount(billiardDataArrayList.get(index).getCount());
                        }

                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "billiard 테이블에서 총 " + numberOfDeletedRows + " 개의 열을 삭제하였습니다.");
                    }
                }
        );

        // <3>
        appDbManager.requestUserQuery(
                new AppDbManager.UserQueryRequestListener() {
                    @Override
                    public void requestQuery(UserDbManager2 userDbManager2) {
                        int numberOfUpdatedRows = userDbManager2.updateContentById(
                                userData.getId(),
                                0,
                                0,
                                0,
                                0,
                                0
                        );
                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "user 테이블에서 총 " + numberOfUpdatedRows + " 개의 열을 업데이트하였습니다.");
                    }
                }
        );

        // <4>
        appDbManager.requestFriendQuery(
                new AppDbManager.FriendQueryRequestListener() {
                    @Override
                    public void requestQuery(FriendDbManager2 friendDbManager2) {
                        int numberOfUpdatedRows = friendDbManager2.updateContentByUserId(
                                userData.getId(),
                                0,
                                0,
                                0,
                                0,
                                0
                        );
                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "player 테이블에서 총 " + numberOfUpdatedRows + " 개의 열을 업데이트하였습니다.");
                    }
                }
        );


        // <5>
        appDbManager.requestQuery(
                new AppDbManager.QueryRequestListener() {
                    @Override
                    public void requestUserQuery(UserDbManager2 userDbManager2) {

                        // 새롭게 갱신된 내용 반영하기
                        userData = userDbManager2.loadContent(userData.getId());

                        DeveloperLog.printLogUserData(CLASS_LOG_SWITCH, CLASS_NAME, userData);

                    }

                    @Override
                    public void requestFriendQuery(FriendDbManager2 friendDbManager2) {

                    }

                    @Override
                    public void requestBilliardQuery(BilliardDbManager2 billiardDbManager2) {

                        // 새롭게 갱신된 내용 반영하기
                        // (주의) 데이터베이스에서 loadAllContent() 메소드의 반환 값을 billiardDataArrayList 에 '=' 연산자로 대입하면
                        //        반환 값의 주소값만 가져와서 변경하므로
                        //        기존의 billiardDataArrayList 에는 반영 안된다.
                        //        그러므로 (1) 아래와 같은 방식으로 다시 추가하거나
                        //                 (2) adapter 에 setter 메소드를 이용하여 다시 billiardDataArrayList 를 setter 해야지
                        //        adapter 가 billiardDataArrayList 가 변경된지 알수 있다.
                        billiardDataArrayList.clear();
                        billiardDataArrayList.addAll(billiardDbManager2.loadAllContent());

                        // adapter 에 변경된 데이터가 있다는 것을 알려준다.
                        billiardLvAdapter2.notifyDataSetChanged();

                        DeveloperLog.printLogBilliardData(CLASS_LOG_SWITCH, CLASS_NAME, billiardDataArrayList);

                    }

                    @Override
                    public void requestPlayerQuery(PlayerDbManager2 playerDbManager2) {

                    }
                }
        );
    }


}