package com.skyman.billiarddata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.ChangedDataChecker;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class BilliardModifyActivity extends AppCompatActivity {

    // constant
    private final String CLASS_NAME_LOG = "[Ac]_BilliardModifyActivity";

    // instance variable
    private TextView count;

    private Spinner dateYear;
    private Spinner dateMonth;
    private Spinner dateDay;

    private Spinner gameMode;

    private TextView playerCount;
    private TextView winnerId;
    private Spinner winnerName;

    private EditText playTime;
    private LinearLayout playerSection[];
    private TextView playerName[];
    private Spinner targetScore[];
    private EditText score[];
    private EditText cost;

    private Button modify;
    private Button cancel;

    // instance variable
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private PlayerDbManager playerDbManager = null;
    private BilliardDbManager billiardDbManager = null;

    // instance variable
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;
    private BilliardData billiardData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_modify);

        final String METHOD_NAME = "[onCreate] ";

        // [lv/C]Intent : BilliardDisplayActivity 에서 전달 된 Intent 가져오기
        Intent intent = getIntent();

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "intent 를 통해 가져온 값 확인하기");
        // [iv/C]BilliardData : 선택한 게임의 billiardData 가져오기
        this.billiardData = SessionManager.getBilliardDataFromIntent(intent);

        // [iv/C]UserData : 나의 정보를 가져오기
        this.userData = SessionManager.getUserDataFromIntent(intent);

        // [iv/C]ArrayList<FriendData> : 이 게임에 참가한 player 중 friend 해당하는 데이터를 가져오기
        this.friendDataArrayList = SessionManager.getParticipatedFriendListInGameFromIntent(intent);

        // [iv/C]ArrayList<PlayerData> : 이 게임에 참가한 모든 player 의 데이터를 가져오기
        this.playerDataArrayList = SessionManager.getPlayerDataArrayListFromIntent(intent);

        // [check 1] : intent 로 가져온 데이터 입력받았다.
        if ((this.billiardData != null) && (this.userData != null) && (this.friendDataArrayList.size() != 0) && (this.playerDataArrayList.size() != 0)) {

            // [method] : billiard, user, friend, player 테이블 메니저 생성
            createDBManager();

            // [method] : widget mapping
            mappingOfWidget();

            // [method] : this.billiardData 로 count, playerCount, winnerId, playTime, cost 값 초기 설정
            setTextWithBilliardData(this.billiardData);

            // [method] : this.billiardData 로 date 값 초기 설정
            setAdapterOfDateSpinner(this.billiardData);

            // [method] : this.billiardData 로 gameMode 값 초기 설정
            setAdapterOfGameModeSpinner(this.billiardData);

            // [method] : this.billiardData, this.playerDataArrayList 로 winnerName 값 초기 설정
            setAdapterOfWinnerNameSpinner(this.billiardData, this.playerDataArrayList);

            // [method] : this.playerDataArrayList 로 player section 의 playerName, targetScore, score 값 초기 설정
            initialSettingOfPlayerScoreWidget(this.playerDataArrayList);

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "Intent 에서 아직 받아오지 않은 데이터가 있어요!");
        } // [check 1]

        // [iv/C]Button : modify click listener setting
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [check 1] : intent 로 가져온 데이터 입력받았다.
                if ((billiardData != null) && (userData != null) && (friendDataArrayList.size() != 0) && (playerDataArrayList.size() != 0)) {

                    // [method] : 수정을 진행 여부를 묻는 AlertDialog 를 보여준다.
                    showDialogWhetherModify(Integer.parseInt(playerCount.getText().toString()));

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "Intent 에서 아직 받아오지 않은 데이터가 있어요!");
                } // [check 1]

            }
        });

        // [iv/C]Button : cancel click listener setting
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Intent : update 완료 후 BilliardDisplayActivity 로 이동하여 변경된 값 확인
                Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);

                // [lv/C]SessionManager : intent 에 변경된 userData 를 포함하기
                SessionManager.setUserDataFromIntent(intent, userData);

                finish();

                startActivity(intent);

            }
        });

    } // End of method [onCreate]


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
     * [method] [set] project_blue.db 의 billiard, user, friend 테이블을 관리하는 메니저를 생성한다.
     */
    private void createDBManager() {

        // [iv/C]BilliardDBManager : billiard 테이블을 관리하는 메니저 생성과 초기화
        this.billiardDbManager = new BilliardDbManager(this);
        this.billiardDbManager.initDb();

        // [iv/C]UserDbManager : user 테이블을 관리하는 메니저 생성과 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블을 관리하는 메니저 생성과 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

        // [iv/C]PlayerDbManager : player 테이블을 관리하는 메니저 생성과 초기화
        this.playerDbManager = new PlayerDbManager(this);
        this.playerDbManager.initDb();

    } // End of method [createDBManager]


    /**
     * [method] [set] custom_dialog_billiard_modify layout 의 widget 들을 mapping 한다.
     */
    public void mappingOfWidget() {

        // [iv/C]TextView : count mapping
        this.count = (TextView) findViewById(R.id.billiardModify_count);

        // [iv/C]Spinner : dateYear mapping
        this.dateYear = (Spinner) findViewById(R.id.billiardModify_sp_date_year);

        // [iv/C]Spinner : dateMonth mapping
        this.dateMonth = (Spinner) findViewById(R.id.billiardModify_sp_date_month);

        // [iv/C]Spinner : dateDay mapping
        this.dateDay = (Spinner) findViewById(R.id.billiardModify_sp_date_day);

        // [iv/C]Spinner : gameMode mapping
        this.gameMode = (Spinner) findViewById(R.id.billiardModify_gameMode);

        // [iv/C]TextView : playerCount mapping
        this.playerCount = (TextView) findViewById(R.id.billiardModify_playerCount);

        // [iv/C]TextView : playerCount mapping
        this.winnerId = (TextView) findViewById(R.id.billiardModify_winnerId);

        // [iv/C]Spinner : winner mapping
        this.winnerName = (Spinner) findViewById(R.id.billiardModify_winnerName);

        // [iv/C]EditText : playTime mapping
        this.playTime = (EditText) findViewById(R.id.billiardModify_playTime);

        // [iv/C]LinearLayout : playerSection 배열 생생
        this.playerSection = new LinearLayout[4];

        // [iv/C]LinearLayout : playerSection mapping
        this.playerSection[0] = (LinearLayout) findViewById(R.id.billiardModify_playerSection_0);
        this.playerSection[1] = (LinearLayout) findViewById(R.id.billiardModify_playerSection_1);
        this.playerSection[2] = (LinearLayout) findViewById(R.id.billiardModify_playerSection_2);
        this.playerSection[3] = (LinearLayout) findViewById(R.id.billiardModify_playerSection_3);

        // [iv/C]TextView : playerName 배열 생성
        this.playerName = new TextView[4];

        // [iv/C]TextView : playerName mapping
        this.playerName[0] = (TextView) findViewById(R.id.billiardModify_playerSection_playerName_0);
        this.playerName[1] = (TextView) findViewById(R.id.billiardModify_playerSection_playerName_1);
        this.playerName[2] = (TextView) findViewById(R.id.billiardModify_playerSection_playerName_2);
        this.playerName[3] = (TextView) findViewById(R.id.billiardModify_playerSection_playerName_3);

        // [iv/C]Spinner : targetScore 배열 생성
        this.targetScore = new Spinner[4];

        // [iv/C]Spinner : targetScore mapping
        this.targetScore[0] = (Spinner) findViewById(R.id.billiardModify_playerSection_targetScore_0);
        this.targetScore[1] = (Spinner) findViewById(R.id.billiardModify_playerSection_targetScore_1);
        this.targetScore[2] = (Spinner) findViewById(R.id.billiardModify_playerSection_targetScore_2);
        this.targetScore[3] = (Spinner) findViewById(R.id.billiardModify_playerSection_targetScore_3);

        // [iv/C]EditText : score 배열 생성
        this.score = new EditText[4];

        // [iv/C]EditText : score mapping
        this.score[0] = (EditText) findViewById(R.id.billiardModify_playerSection_score_0);
        this.score[1] = (EditText) findViewById(R.id.billiardModify_playerSection_score_1);
        this.score[2] = (EditText) findViewById(R.id.billiardModify_playerSection_score_2);
        this.score[3] = (EditText) findViewById(R.id.billiardModify_playerSection_score_3);

        // [iv/C]EditText : cost mapping
        this.cost = (EditText) findViewById(R.id.billiardModify_cost);

        // [iv/C]Button : modify mapping
        this.modify = (Button) findViewById(R.id.billiardModify_button_modify);

        // [iv/C]Button : cancel mapping
        this.cancel = (Button) findViewById(R.id.billiardModify_button_cancel);

    } // End of method [mappingOfWidget]


    /**
     * [method] [set] billiard widget 중 setText 로 초기값을 정할 수 있는 TextView, EditText 를 billiardData 로 초기 설정을 한다.
     */
    private void setTextWithBilliardData(BilliardData billiardData) {

        // [iv/C]TextView : count set text
        this.count.setText(billiardData.getCount() + "");

        // [iv/C]TextView : playerCount set text
        this.playerCount.setText(billiardData.getPlayerCount() + "");

        // [iv/C]TextView : winnerId set text
        this.winnerId.setText(billiardData.getWinnerId() + "");

        // [iv/C]EditText : playTime set text
        this.playTime.setText(billiardData.getPlayTime() + "");

        // [iv/C]EditText : cost set text
        this.cost.setText(billiardData.getCost() + "");

    } // End of method [setTextWithBilliardData]


    /**
     * [method] [set] player widget 중 playerDataArrayList 의 모든 player 의 targetScore, score 값으로 targetScore, score widget 을 초기 설정을 한다.
     *
     * @param playerDataArrayList 게임에 참가한 player 데이터가 있는
     */
    private void initialSettingOfPlayerScoreWidget(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[initialSettingOfPlayerScoreWidget] ";

        // [lv/C]ArrayAdapter : R.array.targetScore 을 연결하는 adapter 를 생성한다.
        ArrayAdapter targetScoreAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);

        // [cycle 1] : player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [iv/C]TextView : playerName 을 설정
            this.playerName[index].setText(playerDataArrayList.get(index).getPlayerName());

            // [iv/C]Spinner : targetScore 를 targetScoreAdapter 와 연결 및 초기화
            this.targetScore[index].setAdapter(targetScoreAdapter);
            this.targetScore[index].setSelection(playerDataArrayList.get(index).getTargetScore() - 1);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + index + " 번째 targetScore = " + playerDataArrayList.get(index).getTargetScore() + " / score = " + playerDataArrayList.get(index).getScore());

            // [iv/C]EditText : playerData 의 score 값을 설정한다.
            this.score[index].setText(playerDataArrayList.get(index).getScore() + "");

        } // [cycle 1]

        // [cycle 2] : player 의 수 이외의
        for (int goneIndex = playerDataArrayList.size(); goneIndex < 4; goneIndex++) {

            // [iv/C]LinearLayout : 등록된 이외의 player 의 section 은 gone 으로 변경
            this.playerSection[goneIndex].setVisibility(LinearLayout.GONE);

        } // [cycle 2]

    } // End of method [initialSettingOfPlayerScoreWidget]


    /**
     * [method] [set] billiard widget 중 date 를 기본데이터 연결하기
     *
     * @param billiardData 선택된 게임 데이터가 들어있는
     */
    private void setAdapterOfDateSpinner(BilliardData billiardData) {

        final String METHOD_NAME = "[setAdapterOfDateSpinner] ";

        // [lv/i]classificationDate : '####년 ##월 ##일' 형태의 날짜를 숫자만 구분하기
        int[] classificationDate = ProjectBlueDataFormatter.changeDateToIntArrayType(billiardData.getDate());

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " [0]=" + classificationDate[0] + " / [1]=" + classificationDate[1] + " / [2]=" + classificationDate[2]);

        // [lv/C]ArrayAdapter : R.array.year 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateYearAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.year, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateYear select / 위 에서 만든 dateYearAdapter 연결하기 / classificationDate 의 index=0 인 값을 선택값으로 만들기
        this.dateYear.setAdapter(dateYearAdapter);
        this.dateYear.setSelection(classificationDate[0] - 2020);

        // [lv/C]ArrayAdapter : R.array.month 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateMonthAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.month, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateMonth select
        this.dateMonth.setAdapter(dateMonthAdapter);
        this.dateMonth.setSelection(classificationDate[1] - 1);

        // [lv/C]ArrayAdapter : R.array.day 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateDayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.day, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateDay select
        this.dateDay.setAdapter(dateDayAdapter);
        this.dateDay.setSelection(classificationDate[2] - 1);

    } // End of method [setAdapterOfDateSpinner]


    /**
     * [method] [set] billiard widget 중 date spinner 을 기본데이터 연결하기
     *
     * @param billiardData 선택된 게임 데이터가 들어있는
     */
    private void setAdapterOfGameModeSpinner(BilliardData billiardData) {

        // [lv/C]ArrayAdapter : R.array.gameMode 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter gameModeAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gameMode, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위에서 만든 dateYearAdapter 연결하기 / 초기값 선택
        this.gameMode.setAdapter(gameModeAdapter);
        this.gameMode.setSelection(ProjectBlueDataFormatter.getSelectedIdOfBilliardGameModeSpinner(billiardData.getGameMode()));

    } // End of method [setAdapterOfBilliardWidgetSpinner]


    /**
     * [method] [set] billiard widget 중 winnerName spinner 을 playerDataArrayList 의 playerName 으로 설정한다.
     *
     * @param billiardData        선택된 게임 데이터가 들어있는
     * @param playerDataArrayList 게임에 player 의 데이터가 들어있는
     */
    private void setAdapterOfWinnerNameSpinner(BilliardData billiardData, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setAdapterOfWinnerNameSpinner] ";

        // [lv/C]ArrayAdapter<String> : playerDataArrayList 의 playerName 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter<String> winnerNameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);

        // [lv/i]positionOfWinner : winner 의 이름으로 찾은 playerDataArrayList 의 위치
        int positionOfWinner = 0;

        // [cycle 1] : 게임에 참가한 player 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayAdapter<String> : playerName 추가하기
            winnerNameAdapter.add(playerDataArrayList.get(index).getPlayerName());

            // [check 1] : winnerId 와 winnerName 으로 playerDataArrayList 에 똑같은 player 가 있다.
            if ((billiardData.getWinnerId() == playerDataArrayList.get(index).getPlayerId()) && (billiardData.getWinnerName().equals(playerDataArrayList.get(index).getPlayerName()))) {

                // [lv/i]positionOfWinner : 이 index 가 player 의 위치이다.
                positionOfWinner = index;

            } else {

            } // [check 1]

        } // [cycle 1]

        // [iv/C]Spinner : 위에서 만든 dateYearAdapter 연결하기 / 초기값 선택
        this.winnerName.setAdapter(winnerNameAdapter);
        this.winnerName.setSelection(positionOfWinner);

    } // End of method [setAdapterOfWinnerNameSpinner]


    /**
     * [method] [AlertDialog] 정말 수정할 건지 물어보는 dialog 를 보여준다.
     */
    private void showDialogWhetherModify(int playerCount) {

        // [lv/C]AlertDialog : builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : builder 초기값 설정 및 보여주기
        builder.setTitle(R.string.billiardModify_dialog_modifyData_title)
                .setMessage(R.string.billiardModify_dialog_modifyData_message)
                .setPositiveButton(R.string.billiardModify_dialog_modifyData_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method] : 변경된 값들로 설정하고 정확한 값들인지 검사하여 데이터베이스에 수정된 데이터를 반영한다.
                        setClickListenerOfModify(playerCount);

                    }
                })
                .setNegativeButton(R.string.billiardModify_dialog_modifyData_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogWhetherModify]


    /**
     * [method] modify button click listener
     */
    private void setClickListenerOfModify(int playerCount) {

        final String METHOD_NAME = "[setClickListenerOfModify] ";

        // [lv/C]ModifyChecker : billiardData, userData, friendDataArrayList, playerDataArrayList 의 변경 여부를 체크하는 객체를 참가한 player 수로 생성한다.
        ChangedDataChecker changedDataChecker = new ChangedDataChecker(playerCount);

        // [method] : 위의 modifyChecker 의 초기 셋팅하기
        setModifyChecker(changedDataChecker);

        // [check 1] : modifyChecker 의 데이터를 수정하고 변경할 준비가 되어있다.
        if (changedDataChecker.isCompleteSetting()) {

            int changedWinnerPlayerIndex = this.winnerName.getSelectedItemPosition();
            long changedWinnerId = this.playerDataArrayList.get(changedWinnerPlayerIndex).getPlayerId();
            String changedWinnerName = this.winnerName.getSelectedItem().toString();
            String changedGameMode = this.gameMode.getSelectedItem().toString();
            int changedPlayTime = Integer.parseInt(this.playTime.getText().toString());
            int changedCost = Integer.parseInt(this.cost.getText().toString());
            int[] changedTargetScores = setChangedTargetScores(playerCount, this.targetScore);
            int[] changedScores = setChangedScores(playerCount, this.score);

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ********************* 변경된 값들 확인 *********************");
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "0. playerCount = " + playerCount);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "1. changedWinnerPlayerIndex = " + changedWinnerPlayerIndex);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "2. changedWinnerId = " + changedWinnerId);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "3. changedWinnerName = " + changedWinnerName);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "4. changedGameMode = " + changedGameMode);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "5. changedPlayTime = " + changedPlayTime);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "6. changedCost = " + changedCost);
            for (int index = 0; index < playerCount; index++) {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "7. changedTargetScores <player " + index + "> = " + changedTargetScores[index]);
            }
            for (int index = 0; index < playerCount; index++) {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "8. changedScores <player " + index + "> = " + changedScores[index]);
            }


            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <전> billiard ************************************");
            DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, changedDataChecker.getBilliardData());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <전> user ************************************");
            DeveloperManager.displayToUserData(CLASS_NAME_LOG, changedDataChecker.getUserData());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <전> friend ************************************");
            DeveloperManager.displayToFriendData(CLASS_NAME_LOG, changedDataChecker.getFriendDataArrayList());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <전> player ************************************");
            DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, changedDataChecker.getPlayerDataArrayList());

            // [method] : winner 가 변경되었는지 판별하여 변경한다. / billiard section
            changedDataChecker.changeWinner(changedWinnerId, changedWinnerName, changedWinnerPlayerIndex);

            // [method] : gameMode 가 변경되었는지 판별하여 변경한다. / billiard section
            changedDataChecker.changeGameMode(changedGameMode);

            // [method] : playTime 이 변경되었는지 판별하여 변경한다. / billiard section
            changedDataChecker.changePlayTime(changedPlayTime);

            // [method] : cost 가 변경되었는지 판별하여 변경한다. / billiard section
            changedDataChecker.changeCost(changedCost);

            // [method] : 모든 targetScore 가 변경되었는지 판별하여 변경한다. / player section
            changedDataChecker.changeTargetScores(changedTargetScores);

            // [method] : 모든 score 가 변경되었는지 판별하여 변경한다. / player section
            changedDataChecker.changeScores(changedScores);

            // [method] : 위에서 변경한 값들 중 / score 가 범위에 맞는 값이 입력되었는지 검사하여 오류가 발생하면 true 가 된다.
            changedDataChecker.setRangeError();

            // [method] : 위에서 변경한 값들 중 / 승리자의 targetScore 와 score 값이 같은지 검사하여 오류가 발생하면 true 가 된다.
            changedDataChecker.setEqualError();

            // [check 2] : 위에서 변경한 값들이 범위에 맞는 값들로 변경되었다.
            if (!changedDataChecker.isRangeError()) {

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "범위 체크에서 통과하였습니다. ");

                // [check 3] : 승리자의 targetScore 와 score 가 같다.
                if (!changedDataChecker.isEqualError()) {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "승리자의 수지와 점수는 같습니다.");

                    // [check 4] : 변경된 값이 있나요?
                    if (changedDataChecker.isChanged()) {

                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "변경된 값이 있어서 테이터베이스에 데이터를 갱신해야 합니다.");
                        // [iv/C]BilliardData : modifyChecker 의 billiardData 를 데이터베이스에 반영하기
                        this.billiardDbManager.updateContentByCount(changedDataChecker.getBilliardData());
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <전> billiard ************************************");
                        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, changedDataChecker.getBilliardData());

                        // [method] : modifyChecker 의 userData 를 데이터베이스에 반영하기
                        updateOfChangedUserData(changedDataChecker.getUserData());

                        // [method] : modifyChecker 의 friendDataArrayList 를 데이터베이스에 반영하기
                        updateOfChangedAllFriendData(changedDataChecker.getFriendDataArrayList());

                        // [method] : modifyChecker 의 playerDataArrayList 를 데이터베이스에 반영하기
                        updateOfChangedAllPlayerData(changedDataChecker.getPlayerDataArrayList());

                        // [lv/C]Intent : update 완료 후 BilliardDisplayActivity 로 이동하여 변경된 값 확인
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);

                        // [lv/C]SessionManager : intent 에 변경된 userData 를 포함하기
                        SessionManager.setUserDataFromIntent(intent, changedDataChecker.getUserData());

                        finish();

                        startActivity(intent);

                    } else {
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "변경된 값이 없어!");
                    } // [check 4]

                } else {
                    Toast.makeText(getApplicationContext(), "승리자는 '수지'와 '점수'가 같아야 합니다.", Toast.LENGTH_SHORT).show();
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<승리자> 의 targetScore 와 score 값이 다릅니다요!");
                } // [check 3]

            } else {
                Toast.makeText(getApplicationContext(), "점수를 범위에 맞는 값들로 입력해주세요.", Toast.LENGTH_SHORT).show();
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player> 의 score 범위에 맞는 값이 아니예요!");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 데이터가 설정되지 않았어!");
        } // [check 1]

    } // End of method [setClickListenerOfModify]


    /**
     * [method] [set] ModifyManager 를 생성하고, billiardData, userData, friendDataArrayList, playerDataArrayList 를 설정하기
     */
    private void setModifyChecker(ChangedDataChecker changedDataChecker) {

        // [lv/C]ModifyChecker : billiardData 입력
        changedDataChecker.setBilliardData(this.billiardData);

        // [lv/C]ModifyChecker : userData 입력
        changedDataChecker.setUserData(this.userData);

        // [lv/C]ModifyChecker : friendDataArrayList 입력
        changedDataChecker.setFriendDataArrayList(this.friendDataArrayList);

        // [lv/C]ModifyChecker : playerDataArrayList 입력
        changedDataChecker.setPlayerDataArrayList(this.playerDataArrayList);

        // [lv/C]ModifyChecker : init 변수들 초기값 설정
        changedDataChecker.setInitData();

        // [lv/C]ModifyChecker : 초기 화 완료 되었는지 세팅
        changedDataChecker.setCompleteSetting();

    } // End of method [setModifyManager]


    /**
     * [method] [setData] player widget 중 targetScore spinner 의 선택한 값들을 int[] 로 만들어서 반환한다.
     */
    private int[] setChangedTargetScores(int playerCount, Spinner[] targetScore) {

        // [lv/i]changedTargetScores : 모든 targetScore spinner 의 값들을 확인하며 모두 저장
        int[] changedTargetScores = new int[playerCount];

        // [cycle 1] : player 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [lv/i]changedTargetScores : targetScore spinner 의 값을 int type casting
            changedTargetScores[index] = Integer.parseInt(targetScore[index].getSelectedItem().toString());

        } // [cycle 1]

        return changedTargetScores;

    } // End of method [setChangedTargetScores]


    /**
     * [method] [setData] player widget 중 score EditText 의 값들을 int[] 로 만들어서 반환한다.
     */
    private int[] setChangedScores(int playerCount, EditText[] score) {

        // [lv/i]changedScores : 모든 score EditText 의 값들을 확인하며 모두 저장
        int[] changedScores = new int[playerCount];

        // [cycle 1] : player 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [lv/i]changedTargetScores : targetScore spinner 의 값을 int type casting
            changedScores[index] = Integer.parseInt(score[index].getText().toString());

        } // [cycle 1]

        return changedScores;
    } // End of method [setChangedScores]


    /**
     * [method] [update] 변경된 UserData 를 데이터베이스에 반영하기
     */
    private void updateOfChangedUserData(UserData changedUserData) {

        final String METHOD_NAME = "[updateOfChangedUserData] ";
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <후> user ************************************");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, changedUserData);

        // [iv/C]UserDbManager : 위 changedUserData 를 데이터베이스에 반영하기
        this.userDbManager.updateContent(changedUserData.getId(), changedUserData.getGameRecordWin(), changedUserData.getGameRecordLoss(), changedUserData.getRecentGameBilliardCount(), changedUserData.getTotalPlayTime(), changedUserData.getTotalCost());

    } // End of method [updateOfChangedUserData]


    /**
     * [method] [update] 변경된 모든 friendData 를 데이터베이스에 반영하기
     */
    private void updateOfChangedAllFriendData(ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[updateOfChangedAll] ";
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <후> friend ************************************");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendDataArrayList);

        // [lv/i]result : update 한 후의 결과를 저장
        int[] result = new int[friendDataArrayList.size()];

        // [cycle 1] : 참가한 player 중 friend 의 수 만큼
        for (int index = 0; index < friendDataArrayList.size(); index++) {

            // [lv/i]result : 변경된 데이터 중 하나를 데이터베이스에 반영하고 그 결과를 받는다.
            result[index] = this.friendDbManager.updateContentById(friendDataArrayList.get(index).getId(),
                    friendDataArrayList.get(index).getGameRecordWin(),
                    friendDataArrayList.get(index).getGameRecordLoss(),
                    friendDataArrayList.get(index).getRecentGameBilliardCount(),
                    friendDataArrayList.get(index).getTotalPlayTime(),
                    friendDataArrayList.get(index).getTotalCost());

        } // [cycle 1]

    } // End of method [updateOfChangedAllFriendData]

    /**
     * [method] [update] 변경된 모든 playerData 를 데이터베이스에 반영하기
     */
    private void updateOfChangedAllPlayerData(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[updateOfChangedAllPlayerData] ";
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ************************************ <후> player ************************************");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

        // [lv/i]result : update 한 후의 결과를 저장
        int[] result = new int[playerDataArrayList.size()];

        // [cycle 1] : 참가한 player 중 friend 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/i]result : 변경된 데이터 중 하나를 데이터베이스에 반영하고 그 결과를 받는다.
            result[index] = this.playerDbManager.updateContentByCount(playerDataArrayList.get(index));

        } // [cycle 1]

    } // End of method [updateOfChangedAllPlayerData]
}