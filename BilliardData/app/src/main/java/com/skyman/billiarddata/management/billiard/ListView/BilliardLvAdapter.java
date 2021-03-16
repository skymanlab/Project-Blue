package com.skyman.billiarddata.management.billiard.ListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.skyman.billiarddata.BilliardModifyActivity;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.dialog.PlayerList;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.data.BilliardDataFormatter;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * [class] billiard 테이블의 모든 데이터를 billiardDataArrayList 에 추가 하여 custom list view 에 뿌려주기 위한 작업을 하는 Adapter 이다.
 */
public class BilliardLvAdapter extends BaseAdapter {

    // constant
    private final String CLASS_NAME_LOG = "[LvM]_BilliardLvAdapter";

    // instance variable
    private BilliardDbManager billiardDbManager;
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private PlayerDbManager playerDbManager;

    // instance variable
    private ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();
    private UserData userData = null;

    // instance variable
    private ArrayList<FriendData> friendDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;

    // instance variable
    private ListView targetListView;

    // constructor
    public BilliardLvAdapter(BilliardDbManager billiardDbManager, UserDbManager userDbManager, PlayerDbManager playerDbManager, FriendDbManager friendDbManager, ListView targetListView) {
        this.billiardDbManager = billiardDbManager;
        this.userDbManager = userDbManager;
        this.playerDbManager = playerDbManager;
        this.friendDbManager = friendDbManager;
        this.targetListView = targetListView;
    }

    @Override
    public int getCount() {
        return billiardDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return billiardDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // [lv/C]String : method name constant
        final String METHOD_NAME = "[getView] ";

        // [lv/C]Context : context 가져오기
        Context context = parent.getContext();

        // [check 1] : convertView 가 있을 때
        if (convertView == null) {

            // [lv/C]LayoutInflater : ????
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // [lv/C]View : custom_lv_billiard_data layout 을 가져오기
            convertView = inflater.inflate(R.layout.custom_lv_billiard_data, parent, false);

        } // [check 1]

        // [lv/C]TextView : count, data, gameMode, playerCount, winnerName, playTime, score, cost mapping
        LinearLayout countLinearLayout = (LinearLayout) convertView.findViewById(R.id.c_lv_billiard_data_ll_count);
        TextView count = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_count);                            // 0. count
        TextView date = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_date);                              // 1. date
        TextView gameMode = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_game_mode);                     // 2. game mode
        TextView playerCount = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_player_count);               // 3. player count
        TextView winnerName = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_winner_name);                 // 4. winner name
        TextView playTime = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_play_time);                     // 5. play time
        TextView score = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_score);                            // 6. score
        TextView cost = (TextView) convertView.findViewById(R.id.c_lv_billiard_data_cost);                              // 7. cost

        // [lv/C]BilliardData : 각 리스트에 뿌려줄 아이템을 받아오는데 BilliardData 재활용
        BilliardData billiardData = (BilliardData) getItem(position);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + position + " / userData 의 id = " + userData.getId());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + position + " / userData 의 name = " + userData.getName());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + position + " / billiard 의 winnerId = " + billiardData.getWinnerId());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + position + " / billiard 의 winnerName = " + billiardData.getWinnerName());

        // [check 1] : userId 와 playerId,  userName 과 playerName 이 모두 같으면 승리 / 나의 승리 여부만 검사하면 됨
        if ((userData.getId() == billiardData.getWinnerId()) && (userData.getName().equals(billiardData.getWinnerName()))) {

            // [lv/C]LinearLayout : countLinearLayout 을 승리하여 background 를 R.color.colorBlue
            countLinearLayout.setBackgroundResource(R.color.colorBlue);

        } else {

            // [lv/C]LinearLayout : countLinearLayout 을 승리하여 background 를 R.color.colorRed
            countLinearLayout.setBackgroundResource(R.color.colorRed);

        } // [check 1]


        // [lv/C]TextView : 매핑된 count, data, targ etScore, speciality, playTime, winner, score, cost 값 셋팅
        count.setText(billiardData.getCount() + "");                                                                // 0. count
        date.setText(billiardData.getDate());                                                                       // 1. date
        gameMode.setText(billiardData.getGameMode());                                                               // 2. game mode
        playerCount.setText(billiardData.getPlayerCount() + "");                                                    // 3. player count
        winnerName.setText(billiardData.getWinnerName());                                                           // 4. winner name
        playTime.setText(BilliardDataFormatter.getFormatOfPlayTime(billiardData.getPlayTime()));                    // 5. play time
        score.setText(billiardData.getScore());                                                                     // 6. score
        cost.setText(BilliardDataFormatter.getFormatOfCost(billiardData.getCost()));                                // 7. cost

        // [lv/C]LinearLayout : countLinearLayout 의 click listener
        countLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] 클릭 리스터 불러오기
                setClickListenerOfCountLinearLayout(context, billiardData, userData);

            }
        });

        // [lv/C]TextView : playerCount 의 click listener
        playerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 게임에 참가한 player 의 수를
                setClickListenerOfPlayerCount(context, billiardData);
            }
        });

        return convertView;
    } // End of method [getView]


    /**
     * [method] [set] BilliardData 를 매개변수로 받아 billiardDataArrayList 에 mapping 한다.
     *
     * @param billiardDataArrayList billiard 데이터가 담긴
     */
    public void setBilliardDataArrayList(ArrayList<BilliardData> billiardDataArrayList) {

        // [iv/C]ArrayList<BilliardData> : 입력받은 BilliardData 가 배열형태로 담길 객체
        this.billiardDataArrayList = billiardDataArrayList;

    } // End of method [addItem]


    /**
     * [method] [set] UserData 를 매개변수로 받아 userData 에 mapping 한다.
     *
     * @param userData userData 데이터가 담긴
     */
    public void setUserData(UserData userData) {

        // [iv/C]UserData : 입력받은 UserData 가 배열형태로 담길 객체
        this.userData = userData;

    } // End of method [addItem]


    /**
     * [method] [BL] modify button 의 click listener 를 설정한다.
     */
    private void setClickListenerOfCountLinearLayout(Context context, BilliardData billiardData, UserData userData) {

        final String METHOD_NAME = "[setClickListenerOfCountLinearLayout] ";

        // [lv/C]ArrayList<PlayerData> : billiardData 의 count 값으로 player 데이터 가져오기
        ArrayList<PlayerData> playerDataArrayList = this.playerDbManager.loadAllContentByBilliardCount(billiardData.getCount());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiardCount 로 가져온 playerData 를 확인하겠습니다.");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

        // [lv/C]ArrayList<FriendData> : 위 playerDataArrayList 에서 friend 에 해당하는 playerId 로 가져온 데이터를 담을
        ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

        // [cycle 1] : playerDataArrayList 중 friend 에 해당하는 수 만큼
        for (int friendIndex = 1; friendIndex < playerDataArrayList.size(); friendIndex++) {

            // [lv/C]ArrayList<FriendData> : 가져온 friendData 추가
            friendDataArrayList.add(this.friendDbManager.loadContentById(playerDataArrayList.get(friendIndex).getPlayerId()));

        } // [cycle 1]
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerId 로 가져온 friendData 를 확인하겠습니다.");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendDataArrayList);


        // [method]showDialogToCheckWhetherModify : 수정을 하는 과정을 진행할 건지 물어보는 AlertDialog 를 보여준다.
        showDialogToCheckWhetherProgressNextStep(context, billiardData, userData, playerDataArrayList, friendDataArrayList);


    } // End of method [setClickListenerOfCountLinearLayout]


    /**
     * [method] [Dialog] 다음 단계로 데이터 수정을 진행할 건지 물어보는 AlertDialog 를 보여준다.
     */
    private void showDialogToCheckWhetherProgressNextStep(Context context, BilliardData billiardData, UserData userData, ArrayList<PlayerData> playerDataArrayList, ArrayList<FriendData> friendDataArrayList) {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // [lv/C]AlertDialog : Builder 초기값 설정
        builder.setTitle(R.string.at_billiard_lv_adapter_check_modify_title)
                .setMessage(R.string.at_billiard_lv_adapter_check_modify_message)
                .setPositiveButton(R.string.at_billiard_lv_adapter_bt_check_modify_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 Intent 생성
                        Intent intent = new Intent(context, BilliardModifyActivity.class);

                        SessionManager.setIntentOfUserData(intent, userData);
                        SessionManager.setIntentOfBilliardData(intent, billiardData);
                        SessionManager.setIntentOfFriendPlayerList(intent, friendDataArrayList);
                        SessionManager.setIntentOfPlayerList(intent, playerDataArrayList);

                        ((Activity) context).finish();
                        context.startActivity(intent);

                    }
                })
                .setNegativeButton(R.string.at_billiard_lv_adapter_bt_check_modify_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherProgressNextStep]


    /**
     * [method] [BL] setClickListenerOfPlayerCount
     */
    private void setClickListenerOfPlayerCount(Context context, BilliardData billiardData) {

        final String METHOD_NAME = "[setClickListenerOfPlayerCount] ";

        // [lv/C]ArrayList<PlayerData> : billiardData 의 count 값으로 player 데이터 가져오기
        ArrayList<PlayerData> playerDataArrayList = this.playerDbManager.loadAllContentByBilliardCount(billiardData.getCount());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiardCount 로 가져온 playerData 를 확인하겠습니다.");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

        // [lv/C]PlayerList : billiardData 에 참가한 player 의 목록을 보여주는 dialog 생성
        PlayerList playerList = new PlayerList(context);

        playerList.setPlayerDataArrayList(playerDataArrayList);

        playerList.setDialog();

    } // End of method [setClickListenerOfPlayerCount]

}
