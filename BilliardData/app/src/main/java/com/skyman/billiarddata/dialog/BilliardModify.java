package com.skyman.billiarddata.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;


public class BilliardModify {

    // constant
    private final String CLASS_NAME_LOG = "[Di]_BilliardModify";

    // instance variable
    private Context context;

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
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private PlayerDbManager playerDbManager;
    private BilliardDbManager billiardDbManager;

    // instance variable
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;
    private BilliardData billiardData = null;

    private UserData changeUserData = new UserData();
    private ArrayList<FriendData> changeFriendDataArrayList = new ArrayList<>();
    private ArrayList<PlayerData> changePlayerDataArrayList = new ArrayList<>();
    private BilliardData changeBilliardData = new BilliardData();

    // instance variable
    private long initWinnerId;
    private String initWinnerName;
    private int initWinnerPlayerIndex;
    private int initPlayTime;
    private int initScore[];
    private int initCost;

    // constructor
    public BilliardModify(Context context, BilliardDbManager billiardDbManager, UserDbManager userDbManager, FriendDbManager friendDbManager, PlayerDbManager playerDbManager) {
        this.context = context;
        this.billiardDbManager = billiardDbManager;
        this.userDbManager = userDbManager;
        this.friendDbManager = friendDbManager;
        this.playerDbManager = playerDbManager;
    }


    /**
     * [method] Dialog 객체를 생성하고, dialog 를 통해서 widget 을 mapping 한다.
     */
    public void setDialog(final ListView targetListView) {

        final String METHOD_NAME = "[setDialog] ";

        // [lv/C]String : method name log
        final String METHOD_NAME_LOG = "[setDialog] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME_LOG + "dialog 를 시작합니다.");

        // [lv/C]Dialog : 객체 생성
        final Dialog dialog = new Dialog(this.context);

        // [lv/C]Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // [lv/C]Dialog : layout 을 custom_dialog_date_modify 으로 설정하기
        dialog.setContentView(R.layout.custom_dialog_billiard_modify);

        // [method] : custom_dialog_billiard_modify layout 의 widget mapping
        mappingOfWidget(dialog);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard = " + billiardData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user = " + userData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friend = " + friendDataArrayList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player = " + playerDataArrayList);

        // [method] : 생성자로 넘오온 것 copy 하기
        copyAllData(this.billiardData, this.userData, this.friendDataArrayList, this.playerDataArrayList);


        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "change billiard = " + this.changeBilliardData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "change user = " + this.changeUserData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "change friend = " + this.changeFriendDataArrayList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "change player = " + this.changePlayerDataArrayList);


        // [check 1] : billiardData, playerDataArrayList, userData, friendData 를 모두 설정하였다.
        if (
                (this.changeBilliardData != null)
                        && (this.changePlayerDataArrayList.size() != 0)
                        && (this.changeUserData != null)
                        && (this.changeFriendDataArrayList.size() != 0)

        ) {

            // [method] : 초기 winnerId, winnerName, playTime, score[], cost 를 미리 저장한다. / 변경된 값과 비교하기 위해서
            setInitData(this.changeBilliardData, this.changePlayerDataArrayList);

            // [method] : date, playerCount, winnerId, playTime, cost 초기 설정
            setTextWithBilliardData();

            // [method] : date spinner 초기 설정 / billiardData 참고
            setAdapterOfDateSpinner(dialog, this.changeBilliardData);

            // [method] : gameMode spinner 초기 설정 / billiardData 참고
            setAdapterOfGameModeSpinner(dialog, this.changeBilliardData);

            // [method] : winnerName 초기 설정 / 승리자가 선택되도록 기본값을 정한다. / billiardData, playerDataArrayList 참고
            setAdapterOfWinnerNameSpinner(dialog, this.changeBilliardData, this.changePlayerDataArrayList);

            // [method] : player 의 수 만큼 score 표시 / playerDataArrayList 참고
            initialSettingOfPlayerScoreWidget(dialog, this.changePlayerDataArrayList);

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiardData, playerDataArrayList, userData, friendDataArrayList 중 하나를 설정하지 않았습니다.");
        } // [check 1]


        // [iv/C]Button : modify click listener
        this.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogToCheckWhetherModify : 수정을 정말 진행할 건지 물어보는 AlertDialog 를 보여준다.
                showDialogToCheckWhetherModify(dialog, targetListView);

            }
        });

        // [iv/C]Button : cancel click listener
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Dialog : dialog 종료
                dialog.dismiss();
            }
        });

        // [lv/C]Dialog : 위에서 설정 된 dialog 를 화면에 보여준다.
        dialog.show();

    } // End of method [setDialog]


    /**
     * [method] [set] billiardData 를 설정하기
     */
    public void setBilliardData(BilliardData billiardData) {

        this.billiardData = billiardData;

    } // End of method [setBilliardData]


    /**
     * [method] [set] userData 를 설정하기
     */
    public void setUserData(UserData userData) {

        this.userData = userData;

    } // End of method [setUserData]


    /**
     * [method] [set] friendData 를 설정하기
     */
    public void setFriendDataArrayList(ArrayList<FriendData> friendDataArrayList) {

        this.friendDataArrayList = friendDataArrayList;

    } // End of method [setFriendDataArrayList]


    /**
     * [method] [set] playerDataArrayList 를 설정하기
     */
    public void setPlayerDataArrayList(ArrayList<PlayerData> playerDataArrayList) {

        this.playerDataArrayList = playerDataArrayList;

    } // End of method [setPlayerDataArrayList]


    /**
     * [method] [A] custom_dialog_billiard_modify layout 의 widget 들을 mapping 한다.
     */
    public void mappingOfWidget(Dialog dialog) {

        // [iv/C]TextView : count mapping
        this.count = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_count);

        // [iv/C]Spinner : dateYear mapping
        this.dateYear = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_sp_date_year);

        // [iv/C]Spinner : dateMonth mapping
        this.dateMonth = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_sp_date_month);

        // [iv/C]Spinner : dateDay mapping
        this.dateDay = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_sp_date_day);

        // [iv/C]Spinner : gameMode mapping
        this.gameMode = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_sp_speciality);

        // [iv/C]TextView : playerCount mapping
        this.playerCount = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_player_count);

        // [iv/C]TextView : playerCount mapping
        this.winnerId = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_winner_id);

        // [iv/C]Spinner : winner mapping
        this.winnerName = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_winner_name);

        // [iv/C]EditText : playTime mapping
        this.playTime = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_play_time);

        // [iv/C]LinearLayout : playerSection 배열 생생
        this.playerSection = new LinearLayout[4];

        // [iv/C]LinearLayout : playerSection mapping
        this.playerSection[0] = (LinearLayout) dialog.findViewById(R.id.c_di_billiard_modify_player_section_0);
        this.playerSection[1] = (LinearLayout) dialog.findViewById(R.id.c_di_billiard_modify_player_section_1);
        this.playerSection[2] = (LinearLayout) dialog.findViewById(R.id.c_di_billiard_modify_player_section_2);
        this.playerSection[3] = (LinearLayout) dialog.findViewById(R.id.c_di_billiard_modify_player_section_3);

        // [iv/C]TextView : playerName 배열 생성
        this.playerName = new TextView[4];

        // [iv/C]TextView : playerName mapping
        this.playerName[0] = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_player_name_0);
        this.playerName[1] = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_player_name_1);
        this.playerName[2] = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_player_name_2);
        this.playerName[3] = (TextView) dialog.findViewById(R.id.c_di_billiard_modify_player_name_3);

        // [iv/C]Spinner : targetScore 배열 생성
        this.targetScore = new Spinner[4];

        // [iv/C]Spinner : targetScore mapping
        this.targetScore[0] = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_target_score_0);
        this.targetScore[1] = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_target_score_1);
        this.targetScore[2] = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_target_score_2);
        this.targetScore[3] = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_target_score_3);

        // [iv/C]EditText : score 배열 생성
        this.score = new EditText[4];

        // [iv/C]EditText : score mapping
        this.score[0] = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_score_0);
        this.score[1] = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_score_1);
        this.score[2] = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_score_2);
        this.score[3] = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_score_3);

        // [iv/C]EditText : cost mapping
        this.cost = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_cost);

        // [iv/C]Button : modify mapping
        this.modify = (Button) dialog.findViewById(R.id.c_di_billiard_modify_bt_modify);

        // [iv/C]Button : cancel mapping
        this.cancel = (Button) dialog.findViewById(R.id.c_di_billiard_modify_bt_cancel);

    } // End of method [mappingOfWidget]


    /**
     * [method] 생성자로 받아온 billiardData, userData, friendDataArrayList, playerDataArrayList 를 복사한다.
     */
    private void copyAllData(BilliardData billiardData,
                             UserData userData,
                             ArrayList<FriendData> friendDataArrayList,
                             ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[copyAllData] ";

        // [iv/C]BilliardData : changeBilliardData 에 복사하기
        this.changeBilliardData.setCount(billiardData.getCount());
        this.changeBilliardData.setDate(billiardData.getDate());
        this.changeBilliardData.setGameMode(billiardData.getGameMode());
        this.changeBilliardData.setPlayerCount(billiardData.getPlayerCount());
        this.changeBilliardData.setWinnerId(billiardData.getWinnerId());
        this.changeBilliardData.setWinnerName(billiardData.getWinnerName());
        this.changeBilliardData.setPlayTime(billiardData.getPlayTime());
        this.changeBilliardData.setScore(billiardData.getScore());
        this.changeBilliardData.setCost(billiardData.getCost());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** changeBilliardData 확인");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, this.changeBilliardData);

        // [iv/C]UserData : changUserData 에 복사하기
        this.changeUserData.setId(userData.getId());
        this.changeUserData.setName(userData.getName());
        this.changeUserData.setTargetScore(userData.getTargetScore());
        this.changeUserData.setSpeciality(userData.getSpeciality());
        this.changeUserData.setGameRecordWin(userData.getGameRecordWin());
        this.changeUserData.setGameRecordLoss(userData.getGameRecordLoss());
        this.changeUserData.setRecentGameBilliardCount(userData.getRecentGameBilliardCount());
        this.changeUserData.setTotalPlayTime(userData.getTotalPlayTime());
        this.changeUserData.setTotalCost(userData.getTotalCost());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** changeUserData 확인");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.changeUserData);


        // [iv/C]ArrayList<FriendData> : changeFriendDataArrayList 의 기존 데이터를 모두 지운다.
        this.changeFriendDataArrayList.clear();

        // [cycle 1] : friendDataArrayList 의 size 만큼
        for (int friendIndex = 0; friendIndex < friendDataArrayList.size(); friendIndex++) {

            // [lv/C]FriendData : friendDataArrayList 에서 하나의 FriendData 복사
            FriendData friendData = new FriendData();
            friendData.setId(friendDataArrayList.get(friendIndex).getId());
            friendData.setUserId(friendDataArrayList.get(friendIndex).getUserId());
            friendData.setName(friendDataArrayList.get(friendIndex).getName());
            friendData.setGameRecordWin(friendDataArrayList.get(friendIndex).getGameRecordWin());
            friendData.setGameRecordLoss(friendDataArrayList.get(friendIndex).getGameRecordLoss());
            friendData.setRecentGameBilliardCount(friendDataArrayList.get(friendIndex).getRecentGameBilliardCount());
            friendData.setTotalPlayTime(friendDataArrayList.get(friendIndex).getTotalPlayTime());
            friendData.setTotalCost(friendDataArrayList.get(friendIndex).getTotalCost());

            // [lv/C]ArrayList<FriendData> : 위의 friendData 를 changeFriendDataArrayList 에 추가하기
            this.changeFriendDataArrayList.add(friendData);

        } // [cycle 1]
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** changeFriendDataArrayList 배열 확인");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, this.changeFriendDataArrayList);

        // [iv/C]ArrayList<PlayerData> : changePlayerDataArrayList 의 기존 데이터를 모두 지운다.
        this.changePlayerDataArrayList.clear();

        // [cycle 2] : playerDataArrayList 의 size 만큼
        for (int playerIndex = 0; playerIndex < playerDataArrayList.size(); playerIndex++) {

            // [lv/C]PlayerData : playerDataArrayList 에서 하나의 playerData 복사
            PlayerData playerData = new PlayerData();
            playerData.setCount(playerDataArrayList.get(playerIndex).getCount());
            playerData.setBilliardCount(playerDataArrayList.get(playerIndex).getBilliardCount());
            playerData.setPlayerId(playerDataArrayList.get(playerIndex).getPlayerId());
            playerData.setPlayerName(playerDataArrayList.get(playerIndex).getPlayerName());
            playerData.setTargetScore(playerDataArrayList.get(playerIndex).getTargetScore());
            playerData.setScore(playerDataArrayList.get(playerIndex).getScore());

            // [lv/C]ArrayList<PlayerData> : 위의 playerData 를 changePlayerDataArrayList 에 추가하기
            this.changePlayerDataArrayList.add(playerData);

        } // [cycle 2]
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** changePlayerDataArrayList 배열 확인");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.changePlayerDataArrayList);

    } // End of method [copyAllData]


    /**
     * [method] 생성자로 받은 billiardData, userData, friendDataArrayList, playerDataArrayList 를 변경된 값으로 최종 변경
     */
    private void copyAllUpdatedData(BilliardData changeBilliardData,
                                    UserData changeUserData,
                                    ArrayList<FriendData> changeFriendDataArrayList,
                                    ArrayList<PlayerData> changePlayerDataArrayList) {

        final String METHOD_NAME = "[copyAllUpdatedData] ";

        // [iv/C]BilliardData : 필드변수 billiardData 에 복사하기
        this.billiardData.setCount(changeBilliardData.getCount());
        this.billiardData.setDate(changeBilliardData.getDate());
        this.billiardData.setGameMode(changeBilliardData.getGameMode());
        this.billiardData.setPlayerCount(changeBilliardData.getPlayerCount());
        this.billiardData.setWinnerId(changeBilliardData.getWinnerId());
        this.billiardData.setWinnerName(changeBilliardData.getWinnerName());
        this.billiardData.setPlayTime(changeBilliardData.getPlayTime());
        this.billiardData.setScore(changeBilliardData.getScore());
        this.billiardData.setCost(changeBilliardData.getCost());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** 생성자 billiardData 확인");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, this.billiardData);

        // [iv/C]UserData : 필드변수 userData 에 복사하기
        this.userData.setId(changeUserData.getId());
        this.userData.setName(changeUserData.getName());
        this.userData.setTargetScore(changeUserData.getTargetScore());
        this.userData.setSpeciality(changeUserData.getSpeciality());
        this.userData.setGameRecordWin(changeUserData.getGameRecordWin());
        this.userData.setGameRecordLoss(changeUserData.getGameRecordLoss());
        this.userData.setRecentGameBilliardCount(changeUserData.getRecentGameBilliardCount());
        this.userData.setTotalPlayTime(changeUserData.getTotalPlayTime());
        this.userData.setTotalCost(changeUserData.getTotalCost());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** 생성자 userData 확인");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);


        // [iv/C]ArrayList<FriendData> : friendDataArrayList 의 기존 데이터를 모두 지운다.
        this.friendDataArrayList.clear();

        // [cycle 1] : friendDataArrayList 의 size 만큼
        for (int friendIndex = 0; friendIndex < changeFriendDataArrayList.size(); friendIndex++) {

            // [lv/C]FriendData : friendDataArrayList 에서 하나의 FriendData 복사
            FriendData friendData = new FriendData();
            friendData.setId(changeFriendDataArrayList.get(friendIndex).getId());
            friendData.setUserId(changeFriendDataArrayList.get(friendIndex).getUserId());
            friendData.setName(changeFriendDataArrayList.get(friendIndex).getName());
            friendData.setGameRecordWin(changeFriendDataArrayList.get(friendIndex).getGameRecordWin());
            friendData.setGameRecordLoss(changeFriendDataArrayList.get(friendIndex).getGameRecordLoss());
            friendData.setRecentGameBilliardCount(changeFriendDataArrayList.get(friendIndex).getRecentGameBilliardCount());
            friendData.setTotalPlayTime(changeFriendDataArrayList.get(friendIndex).getTotalPlayTime());
            friendData.setTotalCost(changeFriendDataArrayList.get(friendIndex).getTotalCost());

            // [lv/C]ArrayList<FriendData> : 위의 friendData 를 changeFriendDataArrayList 에 추가하기
            this.friendDataArrayList.add(friendData);

        } // [cycle 1]
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** 생성자 friendData 배열 확인");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, this.friendDataArrayList);

        // [iv/C]ArrayList<PlayerData> : playerDataArrayList 의 기존 데이터를 모두 지운다.
        this.playerDataArrayList.clear();

        // [cycle 2] : playerDataArrayList 의 size 만큼
        for (int playerIndex = 0; playerIndex < changePlayerDataArrayList.size(); playerIndex++) {

            // [lv/C]PlayerData : playerDataArrayList 에서 하나의 playerData 복사
            PlayerData playerData = new PlayerData();
            playerData.setCount(changePlayerDataArrayList.get(playerIndex).getCount());
            playerData.setBilliardCount(changePlayerDataArrayList.get(playerIndex).getBilliardCount());
            playerData.setPlayerId(changePlayerDataArrayList.get(playerIndex).getPlayerId());
            playerData.setPlayerName(changePlayerDataArrayList.get(playerIndex).getPlayerName());
            playerData.setTargetScore(changePlayerDataArrayList.get(playerIndex).getTargetScore());
            playerData.setScore(changePlayerDataArrayList.get(playerIndex).getScore());

            // [lv/C]ArrayList<PlayerData> : 위의 playerData 를 changePlayerDataArrayList 에 추가하기
            this.playerDataArrayList.add(playerData);

        } // [cycle 2]
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " **************** 생성자 playerData 배열 확인");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.playerDataArrayList);

    } // End of method [copyAllUpdatedData]


    /**
     * [method] [A0] init variable 설정하기
     *
     * <p>
     * 1. winnerId
     * 2. winnerName
     * 3. playTime
     * 4. score
     * 5. cost
     * </p>
     */
    private void setInitData(BilliardData billiardData, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setInitData] ";

        // [iv/l]initWinnerId : billiardData 의 처음 winnerId 값 저장
        this.initWinnerId = billiardData.getWinnerId();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winnerId 의 값 = " + this.initWinnerId);

        // [iv/C]String : initWinnerName / billiardData 의 처음 winnerName 값 저장
        this.initWinnerName = billiardData.getWinnerName();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winnerName 의 값 = " + this.initWinnerName);

        // [iv/i]initPlayTime : billiardData 의 처음 winnerName 값 저장
        this.initPlayTime = billiardData.getPlayTime();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 playTime 의 값 = " + this.initPlayTime);

        // [iv/i]initScore : playerDataArrayList 에 등록되어 있는 player 수 만큼 배열 객체 생성
        this.initScore = new int[playerDataArrayList.size()];

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerDataArrayList 로 구한 초기 score 들을 확입합니다.");
        // [cycle 1] : player 의 수 만큼
        for (int playerIndex = 0; playerIndex < playerDataArrayList.size(); playerIndex++) {


            this.initScore[playerIndex] = playerDataArrayList.get(playerIndex).getScore();
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + playerIndex + "번째 player 의 score = " + this.initScore[playerIndex]);

            // [check 1] : 승리한 사람인가?
            if ((billiardData.getWinnerId() == playerDataArrayList.get(playerIndex).getPlayerId()) && (billiardData.getWinnerName().equals(playerDataArrayList.get(playerIndex).getPlayerName()))) {

                // [iv/i]initWinnerPlayerIndex : 승리한 player 의 index 를 저장
                this.initWinnerPlayerIndex = playerIndex;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winner 가 playerDataArrayList 에 몇 번째 player 인가? = " + this.initWinnerPlayerIndex);

            } // [check 1]

        } // [cycle 1]

        // [iv/i]initCost : billiardData 의 처음 winnerName 값 저장
        this.initCost = billiardData.getCost();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 cost 의 값 = " + this.initCost);

    } // End of method [setInitData]


    /**
     * [method] [A1] billiard widget 중 setText 로 초기값을 정할 수 있는 TextView, EditText 를 billiardData 로 초기 설정을 한다.
     */
    private void setTextWithBilliardData() {

        // [iv/C]TextView : count set text
        this.count.setText(this.billiardData.getCount() + "");

        // [iv/C]TextView : playerCount set text
        this.playerCount.setText(this.billiardData.getPlayerCount() + "");

        // [iv/C]TextView : winnerId set text
        this.winnerId.setText(this.billiardData.getWinnerId() + "");

        // [iv/C]EditText : playTime set text
        this.playTime.setText(this.billiardData.getPlayTime() + "");

        // [iv/C]EditText : cost set text
        this.cost.setText(this.billiardData.getCost() + "");

    } // End of method [setTextWithBilliardData]


    /**
     * [method] [A1] player widget 중 playerDataArrayList 의 모든 player 의 targetScore, score 값으로 targetScore, score widget 을 초기 설정을 한다.
     */
    private void initialSettingOfPlayerScoreWidget(Dialog dialog, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[initialSettingOfPlayerScoreWidget] ";

        // [lv/C]ArrayAdapter : R.array.targetScore 을 연결하는 adapter 를 생성한다.
        ArrayAdapter targetScoreAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);

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
     * [method] [A1] billiard widget 중 date 를 기본데이터 연결하기
     *
     * @param dialog context 를 얻기 위한
     */
    private void setAdapterOfDateSpinner(Dialog dialog, BilliardData billiardData) {

        final String METHOD_NAME = "[setAdapterOfDateSpinner] ";

        // [lv/i]classificationDate : '####년 ##월 ##일' 형태의 날짜를 숫자만 구분하기
        int[] classificationDate = ProjectBlueDataFormatter.changeDateToIntArrayType(billiardData.getDate());

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " [0]=" + classificationDate[0] + " / [1]=" + classificationDate[1] + " / [2]=" + classificationDate[2]);

        // [lv/C]ArrayAdapter : R.array.year 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateYearAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.year, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateYear select / 위 에서 만든 dateYearAdapter 연결하기 / classificationDate 의 index=0 인 값을 선택값으로 만들기
        this.dateYear.setAdapter(dateYearAdapter);
        this.dateYear.setSelection(classificationDate[0] - 2020);

        // [lv/C]ArrayAdapter : R.array.month 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateMonthAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.month, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateMonth select
        this.dateMonth.setAdapter(dateMonthAdapter);
        this.dateMonth.setSelection(classificationDate[1] - 1);

        // [lv/C]ArrayAdapter : R.array.day 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateDayAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.day, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateDay select
        this.dateDay.setAdapter(dateDayAdapter);
        this.dateDay.setSelection(classificationDate[2] - 1);

    } // End of method [setAdapterOfDateSpinner]


    /**
     * [method] [A1] billiard widget 중 date spinner 을 기본데이터 연결하기
     *
     * @param dialog context 를 얻기 위한
     */
    private void setAdapterOfGameModeSpinner(Dialog dialog, BilliardData billiardData) {

        // [lv/C]ArrayAdapter : R.array.gameMode 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter gameModeAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.gameMode, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위에서 만든 dateYearAdapter 연결하기 / 초기값 선택
        this.gameMode.setAdapter(gameModeAdapter);
        this.gameMode.setSelection(getSelectedIdOfBilliardGameModeSpinner(billiardData.getGameMode()));

    } // End of method [setAdapterOfBilliardWidgetSpinner]


    /**
     * [method] [A1] billiard widget 중 winnerName spinner 을 playerDataArrayList 의 playerName 으로 설정한다.
     *
     * @param dialog context 를 얻기 위한
     */
    private void setAdapterOfWinnerNameSpinner(Dialog dialog, BilliardData billiardData, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setAdapterOfWinnerNameSpinner] ";

        // [lv/C]ArrayAdapter<String> : playerDataArrayList 의 playerName 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter<String> winnerNameAdapter = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_dropdown_item);

        // [cycle 1] : 게임에 참가한 player 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayAdapter<String> : playerName 추가하기
            winnerNameAdapter.add(playerDataArrayList.get(index).getPlayerName());

        } // [cycle 1]

        // [iv/C]Spinner : 위에서 만든 dateYearAdapter 연결하기 / 초기값 선택
        this.winnerName.setAdapter(winnerNameAdapter);
        this.winnerName.setSelection(this.initWinnerPlayerIndex);

    } // End of method [setAdapterOfWinnerNameSpinner]


    /**
     * [method] [G]해당 gameMode 문자열로 gameMode spinner 의 몇 번째 item 과 같은지를 비교하여 selectionId 값을 반환한다.
     *
     * @param gameMode 게임 종목
     * @return spinner 의 selection id 값
     */
    private int getSelectedIdOfBilliardGameModeSpinner(String gameMode) {

        final String METHOD_NAME = "[getSelectedIdOfBilliardGameModeSpinner] ";

        // [check 1] : "3구" 이면 0, "4구" 이면 1, "포켓볼" 이면 2  - java version 7 이후로 switch 문에서 String 을 지원한다.
        switch (gameMode) {
            case "3구":
                return 0;
            case "4구":
                return 1;
            case "포켓볼":
                return 2;
            default:
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "현재 목록 중에 있는 값이 없네요.");
                return -1;
        }

    } // End of method [getSelectedIdOfBilliardGameModeSpinner]


    // =======================================================================================================================


    /**
     * [method] [B] 정말 수정할 건지 물어보는 alert dialog 를 보여준다.
     */
    private void showDialogToCheckWhetherModify(Dialog parentDialog, ListView targetListView) {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        // [lv/C]AlertDialog : Builder 초기값 설정
        builder.setTitle(R.string.ad_billiard_modify_check_modify_title)
                .setMessage(R.string.ad_billiard_modify_check_modify_message)
                .setPositiveButton(R.string.ad_billiard_modify_bt_check_modify_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String METHOD_NAME = "[modify-positive button] ";
//                        // [method]updateAllData : 해당 count 로 billiard 테이블을 수정한다.
//                        updateAllData();

                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================== 초기 billiardData 를 확인하겠습다. ==================");
                        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, changeBilliardData);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================== 초기 userData 를 확인하겠습다. ==================");
                        DeveloperManager.displayToUserData(CLASS_NAME_LOG, changeUserData);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================== 초기 friendDataArrayList 를 확인하겠습다. ==================");
                        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, changeFriendDataArrayList);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================== 초기 playerDataArrayList 를 확인하겠습다. ==================");
                        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, changePlayerDataArrayList);


                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ================================= 변경 확인 중 =================================");
                        // [method] : 초기 winner 에서 변경된 값이 있으면 기존 데이터에 반영하여 수정하기
                        changeWinner(winnerName, initWinnerPlayerIndex, changeBilliardData, changeUserData, changeFriendDataArrayList, changePlayerDataArrayList);

                        // [method] : 초기 playTime 에서 변경된 값이 있으면 기존 데이터에 반영하여 수정하기
                        changePlayTime(playTime, initPlayTime, changeBilliardData, changeUserData, changeFriendDataArrayList);

                        // [method] : 초기 targetScore 에서 변경된 값이 있으면 기존 데이터에 반영하여 수정하기
                        changeTargetScore(targetScore, changePlayerDataArrayList);

                        // [check 1] : score 변경이 완료되었다. / 초기 player 들의 score 가 변경된 값이 있으면 기존 데이터에 반영하여 수정하기
                        if (!changeScores(score, initScore, changeBilliardData, changePlayerDataArrayList)) {

                            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "범위 오류! 다음 코드 실행 중지!");
                            Toast.makeText(context, "0 < score < targetScore 범위의 값을 입력해주세요.", Toast.LENGTH_SHORT).show();

                            // [iv/C]ArrayList<FriendData> : changeFriendDataArrayList 가 변경된 값이 있으므로 기존 값(fiendDataArrayList)을 넣기 위해 clear
                            changeFriendDataArrayList.clear();

                            // [iv/C]ArrayList<PlayerData> : changePlayerDataArrayList 가 변경된 값이 있으므로 기존 값(playerDataArrayList)을 넣기 위해 clear
                            changePlayerDataArrayList.clear();

                            // [method] : chang 변수들을 기존 billiardData, userData, friendDataArrayList, playerDataArrayList 로 다시 초기화
                            copyAllData(billiardData, userData, friendDataArrayList, playerDataArrayList);

                            // 범위 오류이면 아래의 코드 실행 중지
                            return;
                        } // [check 1]

                        // [method] : 초기 cost 에서 변경된 값이 있으면 기존 데이터에 반영하여 수정하기
                        changeCost(cost, initCost, changeBilliardData, changeUserData, changeFriendDataArrayList);

                        // [check 2] : 승리자의 targetScore 와 score 가 같다. / 즉 승리자의 score 를 형식에 맞게 입력하였다.
                        if (checkEqualOfTargetScoreAndScore(changeBilliardData.getWinnerId(), changeBilliardData.getWinnerName(), winnerName.getSelectedItemPosition(), changePlayerDataArrayList)) {

                            // [method] : 변경된 모든 데이터를 기존 데이터(생성자 매개변수)에 반영하기
                            copyAllUpdatedData(changeBilliardData, changeUserData, changeFriendDataArrayList, changePlayerDataArrayList);


                        } else {
                            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "승리자 score 가 형식에 맞지 않아요.");
                            Toast.makeText(context, "승리자의 score 값이 뭐가 이상해요!", Toast.LENGTH_SHORT).show();

                            // [iv/C]ArrayList<FriendData> : changeFriendDataArrayList 가 변경된 값이 있으므로 기존 값(fiendDataArrayList)을 넣기 위해 clear
                            changeFriendDataArrayList.clear();

                            // [iv/C]ArrayList<PlayerData> : changePlayerDataArrayList 가 변경된 값이 있으므로 기존 값(playerDataArrayList)을 넣기 위해 clear
                            changePlayerDataArrayList.clear();

                            // [method] : chang 변수들을 기존 billiardData, userData, friendDataArrayList, playerDataArrayList 로 다시 초기화
                            copyAllData(billiardData, userData, friendDataArrayList, playerDataArrayList);

                            // 범위 오류이면 아래의 코드 실행 중지
                            return;
                        } // [check 2]

                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " ================================= xxxx 변경 완료 xxxxx =================================");

                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================================= 변경된 후의 billiardData 를 확인하겠습니다. =================================");
                        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, changeBilliardData);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================================= 변경된 후의 userData 를 확인하겠습니다. =================================");
                        DeveloperManager.displayToUserData(CLASS_NAME_LOG, changeUserData);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================================= 변경된 후의 friendDataArrayList 를 확인하겠습니다. =================================");
                        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, changeFriendDataArrayList);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "================================= 변경된 후의 playerDataArrayList 를 확인하겠습니다. =================================");
                        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, changePlayerDataArrayList);


                        // [lv/C]Dialog : dialog 종료
                        parentDialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.ad_billiard_modify_bt_check_modify_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Dialog : dialog 종료
                        parentDialog.dismiss();

                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherModify]


    /**
     * [method] [Change] winnerName spinner 의 값이 변경되었으면 해당 데이터와 관련된 것들을 변경한다.
     *
     * <p>
     * 1. billiardData
     * - winnerId
     * - winnerName
     * 2. userData / player 0
     * - gameRecordWin
     * - gameRecordLoss
     * 3. friendData / player 1, 2, 3
     * - gameRecordWin
     * - gameRecordLoss
     * </p>
     */
    private void changeWinner(Spinner winnerName, int initWinnerPlayerIndex, BilliardData billiardData, UserData userData, ArrayList<FriendData> friendDataArrayList, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[changeWinner] ";

        // [lv/i]selectionPosition : winnerName spinner 의 선택한 위치
        int changePosition = winnerName.getSelectedItemPosition();

        // [check 1] : 기존 winner 에서 다른 player 가 승리자로 변경되었다.
        if (changePosition != initWinnerPlayerIndex) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "기존 initWinnerPlayerIndex = " + initWinnerPlayerIndex + " / 변경된 winner 의 player index = " + changePosition);

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiardData 의 winnerId, winnerName 을 변경합니다.");
            // [lv/C]BilliardData : winnerName spinner 의 값으로 설정
            billiardData.setWinnerId(playerDataArrayList.get(winnerName.getSelectedItemPosition()).getPlayerId());
            billiardData.setWinnerName(winnerName.getSelectedItem().toString());

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData, friendDataArrayList 의 gameRecordWin, gameRecordLoss 을 변경하겠습니다.");
            // [check 2] : 몇 번째 player 로 바뀌었나요?
            if (changePosition == 0) {
                // player 0 / userData

                // [lv/C]UserData : 기존 패배자인 player 0 을 승리자로 변경 / 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                userData.setGameRecordWin(userData.getGameRecordWin() + 1);
                userData.setGameRecordLoss(userData.getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 0 이 <패배자> -> <승리자>로 변경되었습니다. / userData");

            } else if (changePosition == 1) {
                // player 1 / friendDataArrayList 의 0 번째

                // [lv/C]ArrayList<FriendData> : 기존 패배자인 player 1 을 승리자로 변경 / 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                friendDataArrayList.get(0).setGameRecordWin(friendDataArrayList.get(0).getGameRecordWin() + 1);
                friendDataArrayList.get(0).setGameRecordLoss(friendDataArrayList.get(0).getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1 이 <패배자> -> <승리자>로 변경되었습니다. / friendDataArrayList.get(0)");

            } else if (changePosition == 2) {
                // player 2 / friendDataArrayList 의 1 번째

                // [lv/C]ArrayList<FriendData> : 기존 패배자인 player 2 을 승리자로 변경 / 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                friendDataArrayList.get(1).setGameRecordWin(friendDataArrayList.get(1).getGameRecordWin() + 1);
                friendDataArrayList.get(1).setGameRecordLoss(friendDataArrayList.get(1).getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 2 이 <패배자> -> <승리자>로 변경되었습니다. / friendDataArrayList.get(1)");

            } else if (changePosition == 3) {
                // player 3 / friendDataArrayList 의 2 번째

                // [lv/C]ArrayList<FriendData> : 기존 패배자인 player 3 을 승리자로 변경 / 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                friendDataArrayList.get(2).setGameRecordWin(friendDataArrayList.get(2).getGameRecordWin() + 1);
                friendDataArrayList.get(2).setGameRecordLoss(friendDataArrayList.get(2).getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 3 이 <패배자> -> <승리자>로 변경되었습니다. / friendDataArrayList.get(2)");

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "몇 번째 player 인지 모르겠어요!");
            } // [check 2]

            // [method] : 기존 승리자를 패배자로 변경하기
            changToLooserFromInitialWinner(initWinnerPlayerIndex, userData, friendDataArrayList);

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winner 에서 변경되지 않았습니다. ");
        } // [check 1]

    } // End of method [changeWinner]


    /**
     * [method] [Change] 기존 승리자가 누구인지를 판별해서 그 player 를 승리자에서 패배자로 변경한다. 즉 기존의 gameRecordWin 과 gameRecordLoss 를 바꾼다.
     *
     * <p>
     * <p>
     * gameRecordWin -= 1
     * gameRecordLoss += 1
     * </p>
     *
     * @param initWinnerPlayerIndex winnerName spinner 에서 초기 승리자로 선택된 position 값
     * @param userData              기존 player 0 인 userData
     * @param friendDataArrayList   기존 player 1,2,2 인 friendData 들
     */
    private void changToLooserFromInitialWinner(int initWinnerPlayerIndex, UserData userData, ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[changToLooserFromInitialWinner] ";

        // [check 1] : 몇 번째 player 인가요?
        if (initWinnerPlayerIndex == 0) {
            // player 0 / userData

            // [lv/C]UserData : 기존 승리자인 player 0 을 패배자로 변경 / 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
            userData.setGameRecordWin(userData.getGameRecordWin() - 1);
            userData.setGameRecordLoss(userData.getGameRecordLoss() + 1);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 0 이 <승리자> -> <패배자>로 변경되었습니다. / userData");

        } else if (initWinnerPlayerIndex == 1) {
            // player 1 / friendDataArrayList 의 0 번째

            // [lv/C]ArrayList<FriendData> : 기존 승리자인 player 1 을 패배자로 변경 / 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
            friendDataArrayList.get(0).setGameRecordWin(friendDataArrayList.get(0).getGameRecordWin() - 1);
            friendDataArrayList.get(0).setGameRecordLoss(friendDataArrayList.get(0).getGameRecordLoss() + 1);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1 이 <승리자> -> <패배자>로 변경되었습니다. / friendDataArrayList.get(0)");

        } else if (initWinnerPlayerIndex == 2) {
            // player 2 / friendDataArrayList 의 1 번째

            // [lv/C]ArrayList<FriendData> : 기존 승리자인 player 2 을 패배자로 변경 / 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
            friendDataArrayList.get(1).setGameRecordWin(friendDataArrayList.get(1).getGameRecordWin() - 1);
            friendDataArrayList.get(1).setGameRecordLoss(friendDataArrayList.get(1).getGameRecordLoss() + 1);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 2 이 <승리자> -> <패배자>로 변경되었습니다. / friendDataArrayList.get(1)");

        } else if (initWinnerPlayerIndex == 3) {
            // player 3 / friendDataArrayList 의 2 번째

            // [lv/C]ArrayList<FriendData> : 기존 승리자인 player 3 을 패배자로 변경 / 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
            friendDataArrayList.get(2).setGameRecordWin(friendDataArrayList.get(2).getGameRecordWin() - 1);
            friendDataArrayList.get(2).setGameRecordLoss(friendDataArrayList.get(2).getGameRecordLoss() + 1);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 3 이 <승리자> -> <패배자>로 변경되었습니다. / friendDataArrayList.get(2)");

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "몇 번째 player 인지 모르겠어요!");
        } // [check 1]

    } // End of method [changToLooserFromInitialWinner]


    /**
     * [method] [Change] 기존 playTime 가 변경되었는지 판별하여 playTime 과 관련된 부분을 변경한다.
     *
     * <p>
     * 1. billiardData
     * - playTime
     * 2. userData
     * - totalPlayTime
     * 3. FriendData
     * - totalPlayTime
     * </p>
     */
    private void changePlayTime(EditText playTime, int initPlayTime, BilliardData billiardData, UserData userData, ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[changePlayTime] ";

        // [lv/i]changePlayTime : playTime EditText 의 값을 가져와서 int type casting
        int changePlayTime = Integer.parseInt(playTime.getText().toString());

        // [check 1] : 기존 playTime 이 변경되었다.
        if (changePlayTime != initPlayTime) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "기존 initPlayTime = " + initPlayTime + " / 변경된 playTime = " + changePlayTime);

            // [lv/C]BilliardData : 이 게임의 기존 playTime(initPlayTime) 을 변경된 playTime(changePlayTime) 으로 변경한다.
            billiardData.setPlayTime(changePlayTime);

            // [lv/C]UserData : 기존 TotalPlayTime 에서 초기값(initPlayTime)을 빼고 변경된 playTime(changePlayTime) 을 더한다.
            userData.setTotalPlayTime(userData.getTotalPlayTime() - initPlayTime + changePlayTime);

            // [cycle 1] : player 중 friend 의 수 만큼
            for (int friendIndex = 0; friendIndex < friendDataArrayList.size(); friendIndex++) {

                // [lv/C]ArrayList<FriendData> : 모든 player 의 기존 totalPlayTime 에서 초기값(initPlayTime)을 빼고 변경된 playTime(changePlayTime) 을 더한다.
                friendDataArrayList.get(friendIndex).setTotalPlayTime(friendDataArrayList.get(friendIndex).getTotalPlayTime() - initPlayTime + changePlayTime);

            } // [cycle 1]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 playTime 에서 변경되지 않았습니다.");
        } // [check 1]

    } // End of method [changePlayTime]


    /**
     * [method] [Change] 기존 cost 가 변경되었는지 판별하여 cost 부분과 관련된 부분을 수정한다.
     */
    private void changeCost(EditText cost, int initCost, BilliardData billiardData, UserData userData, ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[changeCost] ";

        // [lv/i]changeCost : cost EditText 의 값을 가져와서 int type casting
        int changeCost = Integer.parseInt(cost.getText().toString());

        // [check 1] : 기존 cost 가 변경되었다.
        if (changeCost != initCost) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "기존 initCost = " + initCost + " / 변경된 cost = " + changeCost);

            // [lv/C]BilliardData : 이 게임의 기존 cost(initCost) 를 변경된 cost(changeCost) 로 변경한다.
            billiardData.setCost(changeCost);

            // [lv/C]UserData : 기존 totalCost 에서 초기값(initCost)을 빼고 변경된 cost(changeCost)로 변경한다.
            userData.setTotalCost(userData.getTotalCost() - initCost + changeCost);

            // [cycle 1] : player 중 friend 의 수 만큼
            for (int friendIndex = 0; friendIndex < friendDataArrayList.size(); friendIndex++) {

                // [lv/C]ArrayList<FriendData> : 모든 player 의 기존 totalPlayTime 에서 초기값(initPlayTime)을 빼고 변경된 playTime(changePlayTime) 을 더한다.
                friendDataArrayList.get(friendIndex).setTotalCost(friendDataArrayList.get(friendIndex).getTotalCost() - initCost + changeCost);

            } // [cycle 1]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 cost 가 변경되지 않았습니다.");
        } // [check 1]

    } // End of method [changeCost]


    /**
     * [method] 기존 targetScore 가 변경되었느지 판별하여 targetScore 와 관련된 부분을 수정한다.
     */
    private void changeTargetScore(Spinner[] targetScore, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[changeTargetScore] ";

        // [cycle 1] : player 의 수 만큼
        for (int playerIndex = 0; playerIndex < playerDataArrayList.size(); playerIndex++) {

            // [lv/i]changeTargetScore : targetScore 값을 가져와서 int type casting
            int changeTargetScore = Integer.parseInt(targetScore[playerIndex].getSelectedItem().toString());

            // [check 1] : targetScore 값이 변경되었다.
            if (changeTargetScore != playerDataArrayList.get(playerIndex).getTargetScore()) {

                // [lv/C]ArrayList<PlayerData> : 기존 targetScore 값을 changeTargetScore 값으로 변경한다.
                playerDataArrayList.get(playerIndex).setTargetScore(changeTargetScore);

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + " 번째 targetScore 값은 변경되지 않았습니다.");
            } // [check 1]

        } // [cycle 1]

    } // End of method [changeTargetScore]


    /**
     * [method] 기존 score 가 변경되었는지 판별하여 score 와 관련된 부분을 수정한다. 그리고 만약 score 의 범위에 안 맞는 값이 입력되었을 때 false 를 리턴한다.
     *
     * <p>
     * 1. billiard
     * - 변경된 score 를 특정 문자열 형태로 변환하여
     * 2. playerData
     * - 변경된 player 의 score
     * </p>
     */
    private boolean changeScores(EditText[] score, int[] initScore, BilliardData billiardData, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[changeScores] ";

        // [lv/b]hasComplete : 변경이 완료되었나요?
        boolean hasComplete = true;

        // [lv/C]ArrayList<String> : 모든 player 의 score 값을 저장한다.
        ArrayList<String> changeScoreList = new ArrayList<>();

        // [cycle 1] : player 수 만큼
        for (int playerIndex = 0; playerIndex < playerDataArrayList.size(); playerIndex++) {

            // [lv/i]changeScore : score EditText 에서 값을 int type casting
            int changeScore = Integer.parseInt(score[playerIndex].getText().toString());

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + playerIndex + " 번째의 기존 score 값을 변경 중입니다.");
            // [check 1] : 기존 score 값이 변경되었다.
            if (changeScore != initScore[playerIndex]) {

                // [check 2] : changeScore 값이 player 의 targetScore 값 범위 안의 값이다.
                if (checkWhetherInputWithinRangeOfChangeScore(changeScore, playerDataArrayList.get(playerIndex).getTargetScore())) {

                    // [lv/C]ArrayList<PlayerData> : 해당 player 의 score 값을 changeScore 값으로 변경
                    playerDataArrayList.get(playerIndex).setScore(changeScore);

                    // [lv/C]ArrayList<String> : changeScoreList 에 changeScore 값을 추가
                    changeScoreList.add(changeScore + "");

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + playerDataArrayList + " 번째 score 값이 범위 안의 값이 아닙니다.");

                    // [lv/b]hasComplete : 변경중에 범위에 맞지 않는 값이여서 중단되었어요!
                    hasComplete = false;

                } // [check 2]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + playerIndex + " 번째의 기존 score 값이 변경되지 않았어!");

                // [lv/C]ArrayList<String> : changeScoreList 에 score 값을 추가
                changeScoreList.add(initScore[playerIndex] + "");

            } // [check 1]

        } // [cycle 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 score = " + billiardData.getScore() + " / 변경되었는지 모르지만 score = " + ProjectBlueDataFormatter.getFormatOfScore(changeScoreList));

        // [lv/C]BilliardCount : 위에서 다시 설정한 player 들의 score 값으로 billiard 의 score 값으로 변경하기
        billiardData.setScore(ProjectBlueDataFormatter.getFormatOfScore(changeScoreList));

        return hasComplete;
    } // End of method [changeScores]


    /**
     * [method] [Check] 변경된 score 값이 player 의 targetScore 범위 안의 값이면 true 를 반환한다.
     */
    private boolean checkWhetherInputWithinRangeOfChangeScore(int changeScore, int targetScore) {

        final String METHOD_NAME = "[checkWhetherInputWithinRangeOfChangeScore] ";

        // [lv/b]isWithinRange : 범위 내의 값인가요?
        boolean isWithinRange;

        // [check 1] : 0 <= changeScore <= targetScore
        if ((0 <= changeScore) && (changeScore <= targetScore)) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "변경된 score 값이 범위 안의 값이다.");
            // [lv/b]isWithinRange : 범위 안의 값이다.
            isWithinRange = true;

        } else {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "변경된 score 값이 잘못된 범위의 값이다.");
            // [lv/b]isWithinRange : 범위 안의 값이 아니여유!
            isWithinRange = false;

        } // [check 1]

        return isWithinRange;
    } // End of method [checkWhetherInputWithinRangeOfChangeScore]


    /**
     * [method] [Check] 승리자로 선택된 player 의 targetScore 와 score 가 같은지 판별한다.
     */
    private boolean checkEqualOfTargetScoreAndScore(long winnerId, String winnerName, int winnerPlayerIndex, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[checkEqualOfTargetScoreAndScore] ";

        // [lv/b]isEqual : 승리자의 targetScore 와 score 값이 같은지 / 왜? 승리자는 목표점수와 같아야 하므로
        boolean isEqual = false;

        // [check 1] : player 의 id 및 name 이 winnerId 와 winnerName 이 같다.
        if ((winnerId == playerDataArrayList.get(winnerPlayerIndex).getPlayerId()) && (winnerName.equals(playerDataArrayList.get(winnerPlayerIndex).getPlayerName()))) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + winnerPlayerIndex + " 번째 player 는 winner 입니다. targetScore 와 score 를 확인하겠습니다.");

            // [check 2] : winner 의 targetScore 와 score 가 같다.
            if (playerDataArrayList.get(winnerPlayerIndex).getTargetScore() == playerDataArrayList.get(winnerPlayerIndex).getScore()) {

                // [lv/b]isEqual : 이 winner 는 진짜 승리자이다.
                isEqual = true;

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "winner 의 targetScore 와 score 값이 다릅니다. 승리자는 이 값이 같아야 합니다.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + winnerPlayerIndex + " 번째 player 는 winner 가 아닙니다.");
        } // [check 1]


        return isEqual;
    } // End of method [checkEqualOfTargetScoreAndScore]


    // =======================================================================================================================


    // =======================================================================================================================


    /**
     * [method] 수정 된 값을 이용하여 해당 count 로 billiard 테이블을 수정한다.
     */
    private void updateModifiedAllData(BilliardData billiardData, UserData userData, ArrayList<FriendData> friendDataArrayList, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME_LOG = "[updateAllData] ";

        // [lv/i]billiardResult : billiardData 를 update
        int billiardResult = this.billiardDbManager.updateContentByCount(billiardData);

        // [lv/i]userResult : userData 를 update
        int userResult = this.userDbManager.updateContent(userData.getId(),
                userData.getGameRecordWin(),
                userData.getGameRecordLoss(),
                userData.getRecentGameBilliardCount(),
                userData.getTotalPlayTime(),
                userData.getTotalCost());

        // [lv/i]friendResult : friendDataArrayList 를 update
        int[] friendResult = updateModifiedFriendDataArrayList(friendDataArrayList);

        // [lv/i]playerResult : playerDataArrayList 를 update
        int playerResult;


    } // End of method [updateAllData]


    /**
     * [method] player 중 friend 의 수정된 정보가 있는 friendDataArrayList 를 update 한다.
     *
     * @param friendDataArrayList 변경된 내용이 들어있는
     * @return 각각의 friendData 를 update 한 결과가 들어있다.
     */
    private int[] updateModifiedFriendDataArrayList(ArrayList<FriendData> friendDataArrayList) {

        // [lv/i]results : update 결과
        int[] results = new int[friendDataArrayList.size()];

        // [cycle 1] : player 중 friend 의 수 만큼
        for (int index = 0; index < friendDataArrayList.size(); index++) {

            // [lv/i]results : friendData 를 update 후 결과를 받는다.
            results[index] = this.friendDbManager.updateContentById(
                    friendDataArrayList.get(index).getId(),
                    friendDataArrayList.get(index).getGameRecordWin(),
                    friendDataArrayList.get(index).getGameRecordLoss(),
                    friendDataArrayList.get(index).getRecentGameBilliardCount(),
                    friendDataArrayList.get(index).getTotalPlayTime(),
                    friendDataArrayList.get(index).getTotalCost()
            );

        } // [cycle 1]

        return results;
    } // End of method [updateModifiedAllFriendData]


    /**
     * [method] 게임에 참가한 모든 player 의 수정된 정보가 있는 playerDataArrayList 를 update 한다.
     */
    private int[] updateModifiedAllPlayerData(ArrayList<PlayerData> playerDataArrayList) {

        // [lv/i]results : update 결과
        int[] results = new int[friendDataArrayList.size()];

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/i]results : playerData 를 update 한 결과를 받는다.
//            results[index] = this.playerDbManager.up

        } // [cycle 1]

        return results;
    } // End of method [updateModifiedAllPlayerData]
}
