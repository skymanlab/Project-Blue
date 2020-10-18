package com.skyman.billiarddata.management.billiard.listview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.dialog.BilliardModify;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.data.BilliardDataFormatter;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

/**
 * [class] billiard 테이블의 모든 데이터를 billiardDataArrayList 에 추가 하여 custom list view 에 뿌려주기 위한 작업을 하는 Adapter 이다.
 */
public class BilliardLvAdapter extends BaseAdapter {

    // constant
    private final String CLASS_NAME_LOG = "[LvM]_BilliardLvAdapter";

    // instance variable
    private ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();
    private ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();
    private UserData userData;
    private BilliardDbManager billiardDbManager;


    private String userName = new String();

    // constructor
    public BilliardLvAdapter(BilliardDbManager billiardDbManager){
        this.billiardDbManager = billiardDbManager;
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
        final String METHOD_NAME= "[getView] ";

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


        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userName : " + userName);

        // [check 1] : userName 과 winner 으로 승리여부 확인
//        if (userName.equals(billiardData.getWinner())) {
//
//            // [lv/C]LinearLayout : countLinearLayout 을 승리하여 background 를 R.color.colorBlue
//            countLinearLayout.setBackgroundResource(R.color.colorBlue);
//
//        } else {
//
//            // [lv/C]LinearLayout : countLinearLayout 을 승리하여 background 를 R.color.colorRed
//            countLinearLayout.setBackgroundResource(R.color.colorRed);
//
//        } // [check 1]


        // [lv/C]TextView : 매핑된 count, data, targetScore, speciality, playTime, winner, score, cost 값 셋팅
        count.setText(billiardData.getCount() + "");                                                                // 0. count
        date.setText(billiardData.getDate());                                                                       // 1. date
        gameMode.setText(billiardData.getGameMode());                                                               // 2. game mode
        playerCount.setText(billiardData.getPlayerCount());                                                         // 3. player count
        playTime.setText(BilliardDataFormatter.getFormatOfPlayTime(billiardData.getPlayTime()));                    // 5. play time
        score.setText(billiardData.getScore());                                                                     // 6. score
        cost.setText(BilliardDataFormatter.getFormatOfCost(billiardData.getCost()));                                // 7. cost

        // [lv/C]LinearLayout : countLinearLayout 의 click listener
        countLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogToCheckWhetherModify : 수정을 하는 과정을 진행할 건지 물어보는 AlertDialog 를 보여준다.
                showDialogToCheckWhetherProgressNextStep(context, billiardData);

            }
        });

        return convertView;
    }

    /**
     * [method] 매개 변수로 받은 값을 BilliardData 로 만들어 billiardDataArrayList 에 추가한다.
     *
     * @param cost          [0] count : primary key
     * @param date          [1] 날짜
     * @param gameMode      [2] 종목
     * @param playerCount   [3] 참가인원
     * @param winnerId      [4] 승리자 아이디
     * @param playTime      [5] 게임 시간
     * @param score         [6] 스코어
     * @param cost          [7] 비용
     */
    public void addItem(long count,         // 0. count
                        String date,        // 1. date
                        String gameMode,    // 2. game mode
                        int playerCount,    // 3. player count
                        long winnerId,      // 4. winner id
                        int playTime,       // 5. play time
                        String score,       // 6. score
                        int cost) {         // 7. cost

        // [lv/C]BilliardData : 매개변수로 받은 데이터를 BilliardData 담는다.
        BilliardData billiardData = new BilliardData();
        billiardData.setCount(count);                       // 0. count
        billiardData.setDate(date);                         // 1. date
        billiardData.setGameMode(gameMode);                 // 2. game mode
        billiardData.setPlayerCount(playerCount);           // 3. player count
        billiardData.setWinnerId(winnerId);                 // 4. winner id
        billiardData.setPlayTime(playTime);                 // 5. play time
        billiardData.setScore(score);                       // 6. score
        billiardData.setCost(cost);                         // 7. cost

        // [iv/C]ArrayList<BilliardData> : 위 에서 만든 데이터를 배열에 담는다.
        this.billiardDataArrayList.add(billiardData);

    } // End of method [addItem]


    /**
     * [method] BilliardData 를 매개변수로 받아 billiardDataArrayList 에 추가한다.
     *
     * @param billiardData billiard 데이터가 담긴
     */
    public void addItem(BilliardData billiardData) {

        // [iv/C]ArrayList<BilliardData> : 입력받은 BilliardData 가 배열형태로 담길 객체
        this.billiardDataArrayList.add(billiardData);

    } // End of method [addItem]


    /**
     * [method] userName 을 추가한다.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * [method] 다음 단계로 데이터 수정을 진행할 건지 물어보는 AlertDialog 를 보여준다.
     *
     */
    private void showDialogToCheckWhetherProgressNextStep (Context context, BilliardData billiardData) {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // [lv/C]AlertDialog : Builder 초기값 설정
        builder.setTitle(R.string.at_billiard_lv_adapter_check_modify_title)
                .setMessage(R.string.at_billiard_lv_adapter_check_modify_message)
                .setPositiveButton(R.string.at_billiard_lv_adapter_bt_check_modify_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]BilliardModify : billiard 데이터를 수정하는 dialog 를 호출하기 위한 객체 생성
                        BilliardModify billiardModify = new BilliardModify(context, billiardDbManager, billiardData);

                        // [lv/C]BilliardModify : dialog 보여주기
                        billiardModify.setDialog();
                    }
                })
                .setNegativeButton(R.string.at_billiard_lv_adapter_bt_check_modify_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherProgressNextStep]
}
