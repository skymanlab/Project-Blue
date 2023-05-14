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

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.DataTransformUtil;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.ChangedDataChecker;
import com.skyman.billiarddata.etc.SessionManager;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;
import com.skyman.billiarddata.table.user.database.UserDbManager2;

import java.util.ArrayList;

public class BilliardModifyActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "BilliardInputActivity";

    // instance variable
    private UserData userData = null;
    private BilliardData billiardData = null;
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable : widget
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
    private Button delete;
    private Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreate] ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_modify);

        // <1> 
        // sessionManager : getter ( userData, billiardData, friendDataArrayList, playerDataArrayList )
        this.userData = SessionManager.getUserDataFromIntent(getIntent());
        this.billiardData = SessionManager.getBilliardDataFromIntent(getIntent());
        this.friendDataArrayList = SessionManager.getParticipatedFriendListInGameFromIntent(getIntent());
        this.playerDataArrayList = SessionManager.getPlayerDataArrayListFromIntent(getIntent());

        // AppDbManager
        initAppDbManager();

        // Widget : connect -> init
        connectWidget();
        initWidget();

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

        this.delete = (Button) findViewById(R.id.billiardModify_button_delete);

        // [iv/C]Button : cancel mapping
        this.cancel = (Button) findViewById(R.id.billiardModify_button_cancel);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        if ((billiardData != null) && (userData != null) && !friendDataArrayList.isEmpty() && !playerDataArrayList.isEmpty()) {

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

            dateYear.setEnabled(false);
            dateMonth.setEnabled(false);
            dateDay.setEnabled(false);

        } else {
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "Intent 에서 아직 받아오지 않은 데이터가 있어요!");
        } // [check 1]


        // widget (modify) : click listener
        modify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [check 1] : intent 로 가져온 데이터 입력받았다.
                        if ((billiardData != null) && (userData != null) && !friendDataArrayList.isEmpty() && !playerDataArrayList.isEmpty()) {

                            setClickListenerOfModifyButton(Integer.parseInt(playerCount.getText().toString()));

                        } else {
                            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "Intent 에서 아직 받아오지 않은 데이터가 있어요!");
                        } // [check 1]

                    }

                }
        );

        // widget (delete) : click listener
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [check 1] : intent 로 가져온 데이터 입력받았다.
                        if ((billiardData != null) && (userData != null) && !friendDataArrayList.isEmpty() && !playerDataArrayList.isEmpty()) {


                        }
                    }
                }
        );


        // [iv/C]Button : cancel click listener setting
        this.cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // [lv/C]Intent : update 완료 후 BilliardDisplayActivity 로 이동하여 변경된 값 확인
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);

                        // sessionManager : setter ( userData )
                        SessionManager.setUserDataFromIntent(intent, userData);

                        finish();

                        startActivity(intent);

                    }
                }
        );
    }


    // ============================================================ Widget : 각 widget init 하기 위한 ============================================================

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
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + index + " 번째 targetScore = " + playerDataArrayList.get(index).getTargetScore() + " / score = " + playerDataArrayList.get(index).getScore());

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
        int[] classificationDate = DataTransformUtil.changeDateToIntArrayType(billiardData.getDate());

        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " [0]=" + classificationDate[0] + " / [1]=" + classificationDate[1] + " / [2]=" + classificationDate[2]);

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
        this.gameMode.setSelection(DataTransformUtil.getSelectedIdOfBilliardGameModeSpinner(billiardData.getGameMode()));

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


    // ============================================================ 수정된 데이터를 데이터베이스에 반영하는 과정에 필요한 method ============================================================

    /**
     * [method] modify button click listener
     */
    private void setClickListenerOfModifyButton(int playerCount) {

        final String METHOD_NAME = "[modifyData] ";

        // [lv/C]ModifyChecker : billiardData, userData, friendDataArrayList, playerDataArrayList 의 변경 여부를 체크하는 객체를 참가한 player 수로 생성한다.
        ChangedDataChecker changedDataChecker = new ChangedDataChecker(playerCount);

        // [method] : 위의 modifyChecker 의 초기 셋팅하기
        setModifyChecker(changedDataChecker);

        // [check 1] : modifyChecker 의 데이터를 수정하고 변경할 준비가 되어있다.
        if (changedDataChecker.isCompleteSetting()) {

            // Widget 
            // 각 widget 에 입력된 값 가져오기
            int changedWinnerPlayerIndex = this.winnerName.getSelectedItemPosition();
            long changedWinnerId = this.playerDataArrayList.get(changedWinnerPlayerIndex).getPlayerId();
            String changedWinnerName = this.winnerName.getSelectedItem().toString();

            String changedGameMode = this.gameMode.getSelectedItem().toString();
            int changedPlayTime = Integer.parseInt(this.playTime.getText().toString());
            int changedCost = Integer.parseInt(this.cost.getText().toString());
            int[] changedTargetScores = setChangedTargetScores(playerCount, this.targetScore);
            int[] changedScores = setChangedScores(playerCount, this.score);

            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ********************* 변경된 값들 확인 *********************");
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "0. playerCount = " + playerCount);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "1. changedWinnerPlayerIndex = " + changedWinnerPlayerIndex);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "2. changedWinnerId = " + changedWinnerId);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "3. changedWinnerName = " + changedWinnerName);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "4. changedGameMode = " + changedGameMode);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "5. changedPlayTime = " + changedPlayTime);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "6. changedCost = " + changedCost);
            for (int index = 0; index < playerCount; index++) {
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "7. changedTargetScores <player " + index + "> = " + changedTargetScores[index]);
            }
            for (int index = 0; index < playerCount; index++) {
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "8. changedScores <player " + index + "> = " + changedScores[index]);
            }


            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <전> billiard ************************************");
            DeveloperLog.printLogBilliardData(CLASS_LOG_SWITCH, CLASS_NAME, changedDataChecker.getBilliardData());
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <전> user ************************************");
            DeveloperLog.printLogUserData(CLASS_LOG_SWITCH, CLASS_NAME, changedDataChecker.getUserData());
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <전> friend ************************************");
            DeveloperLog.printLogFriendData(CLASS_LOG_SWITCH, CLASS_NAME, changedDataChecker.getFriendDataArrayList());
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <전> player ************************************");
            DeveloperLog.printLogPlayerData(CLASS_LOG_SWITCH, CLASS_NAME, changedDataChecker.getPlayerDataArrayList());

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

                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "범위 체크에서 통과하였습니다. ");

                // [check 3] : 승리자의 targetScore 와 score 가 같다.
                if (!changedDataChecker.isEqualError()) {
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "승리자의 수지와 점수는 같습니다.");

                    // [check 4] : 변경된 값이 있나요?
                    if (changedDataChecker.isChanged()) {

                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "변경된 값이 있어서 테이터베이스에 데이터를 갱신해야 합니다.");


                        // <사용자 확인>
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.billiardModify_dialog_modifyData_title)
                                .setMessage(R.string.billiardModify_dialog_modifyData_message)
                                .setPositiveButton(R.string.billiardModify_dialog_modifyData_positive, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // billiard 수정
                                        appDbManager.requestBilliardQuery(
                                                new AppDbManager.BilliardQueryRequestListener() {
                                                    @Override
                                                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                                                        billiardDbManager2.updateContentByCount(changedDataChecker.getBilliardData());
                                                    }
                                                }
                                        );

                                        // user 수정
                                        updateOfChangedUserData(changedDataChecker.getUserData());

                                        // friend 수정
                                        updateOfChangedAllFriendData(changedDataChecker.getFriendDataArrayList());

                                        // player 수정
                                        updateOfChangedAllPlayerData(changedDataChecker.getPlayerDataArrayList());


                                        // Activity 이동
                                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                                        SessionManager.setUserDataFromIntent(intent, changedDataChecker.getUserData());
                                        finish();
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton(R.string.billiardModify_dialog_modifyData_negative, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();


                    } else {
                        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "변경된 값이 없어!");

                        // <사용자 알림>
                        Toast.makeText(
                                this,
                                R.string.billiardModify_noticeUser_noChangedData,
                                Toast.LENGTH_LONG
                        ).show();
                    } // [check 4]

                } else {
                    Toast.makeText(getApplicationContext(), "승리자는 '수지'와 '점수'가 같아야 합니다.", Toast.LENGTH_SHORT).show();
                    DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "<승리자> 의 targetScore 와 score 값이 다릅니다요!");
                } // [check 3]

            } else {
                Toast.makeText(getApplicationContext(), "점수를 범위에 맞는 값들로 입력해주세요.", Toast.LENGTH_SHORT).show();
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "<player> 의 score 범위에 맞는 값이 아니예요!");
            } // [check 2]

        } else {
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "초기 데이터가 설정되지 않았어!");
        } // [check 1]

    } // End of method [setClickListenerOfModifyButton]


    private void setClickListenerOfDeleteButton() {

        // <사용자 확인>
        new AlertDialog.Builder(this)
                .setTitle(R.string.billiardModify_dialog_deleteData_title)
                .setMessage(R.string.billiardModify_dialog_deleteData_message)
                .setPositiveButton(
                        R.string.billiardModify_dialog_deleteData_positive,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }
                )
                .setNegativeButton(
                        R.string.billiardModify_dialog_deleteData_negative,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                )
                .show();

    }


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
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <후> user ************************************");
        DeveloperLog.printLogUserData(CLASS_LOG_SWITCH, CLASS_NAME, changedUserData);

        appDbManager.requestUserQuery(
                new AppDbManager.UserQueryRequestListener() {
                    @Override
                    public void requestQuery(UserDbManager2 userDbManager2) {

                        userDbManager2.updateContentById(
                                changedUserData.getId(),
                                changedUserData.getGameRecordWin(),
                                changedUserData.getGameRecordLoss(),
                                changedUserData.getRecentGameBilliardCount(),
                                changedUserData.getTotalPlayTime(),
                                changedUserData.getTotalCost()
                        );
                    }
                }
        );

    } // End of method [updateOfChangedUserData]


    /**
     * [method] [update] 변경된 모든 friendData 를 데이터베이스에 반영하기
     */
    private void updateOfChangedAllFriendData(ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[updateOfChangedAll] ";
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <후> friend ************************************");
        DeveloperLog.printLogFriendData(CLASS_LOG_SWITCH, CLASS_NAME, friendDataArrayList);

        // [lv/i]result : update 한 후의 결과를 저장
        int[] result = new int[friendDataArrayList.size()];

        appDbManager.requestFriendQuery(
                new AppDbManager.FriendQueryRequestListener() {
                    @Override
                    public void requestQuery(FriendDbManager2 friendDbManager2) {

                        for (int index = 0; index < friendDataArrayList.size(); index++) {

                            result[index] = friendDbManager2.updateContentById(
                                    friendDataArrayList.get(index).getId(),
                                    friendDataArrayList.get(index).getGameRecordWin(),
                                    friendDataArrayList.get(index).getGameRecordLoss(),
                                    friendDataArrayList.get(index).getRecentGameBilliardCount(),
                                    friendDataArrayList.get(index).getTotalPlayTime(),
                                    friendDataArrayList.get(index).getTotalCost()
                            );

                        }

                    }
                }
        );


    } // End of method [updateOfChangedAllFriendData]

    /**
     * [method] [update] 변경된 모든 playerData 를 데이터베이스에 반영하기
     */
    private void updateOfChangedAllPlayerData(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[updateOfChangedAllPlayerData] ";
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + " ************************************ <후> player ************************************");
        DeveloperLog.printLogPlayerData(CLASS_LOG_SWITCH, CLASS_NAME, playerDataArrayList);

        // [lv/i]result : update 한 후의 결과를 저장
        int[] result = new int[playerDataArrayList.size()];

        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

                        for (int index = 0; index < playerDataArrayList.size(); index++) {

                            result[index] = playerDbManager2.updateContentByCount(
                                    playerDataArrayList.get(index)
                            );

                        }

                    }
                }
        );

    } // End of method [updateOfChangedAllPlayerData]
}