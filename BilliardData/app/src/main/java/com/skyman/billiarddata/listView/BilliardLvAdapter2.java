package com.skyman.billiarddata.listView;

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
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.skyman.billiarddata.BilliardModifyActivity;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.dialog.PlayerListDialog;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.DataFormatUtil;
import com.skyman.billiarddata.etc.SessionManager;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;

/**
 * [class] billiard 테이블의 모든 데이터를 billiardDataArrayList 에 추가 하여 custom list view 에 뿌려주기 위한 작업을 하는 Adapter 이다.
 */
public class BilliardLvAdapter2 extends BaseAdapter {

    // constant
    private static final Display CLASS_LOG_SWITCH = Display.OFF;
    private static final String CLASS_NAME = "BilliardLvAdapter2";

    // instance variable
    private FragmentManager fragmentManager;
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private AppDbManager appDbManager;

    // constructor
    public BilliardLvAdapter2(FragmentManager fragmentManager, UserData userData, ArrayList<BilliardData> billiardDataArrayList, AppDbManager appDbManager) {
        this.fragmentManager = fragmentManager;
        this.userData = userData;
        this.billiardDataArrayList = billiardDataArrayList;
        this.appDbManager = appDbManager;
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
        final String METHOD_NAME = "[getView] ";

        // inflate
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_lv_billiard_data, parent, false);

        // [lv/C]BilliardData : 각 리스트에 뿌려줄 아이템을 받아오는데 BilliardData 재활용
        BilliardData billiardData = (BilliardData) getItem(position);

        // 0. count (primary key/autoincrement)
        // 1. date
        // 2. game mode
        // 3. player count
        // 4. winner id
        // 5. winner name
        // 6. play time
        // 7. score
        // 8. cost

        // connect widget
        LinearLayout countWrapper = (LinearLayout) convertView.findViewById(R.id.clv_billiardData_count_wrapper);
        TextView count = (TextView) convertView.findViewById(R.id.clv_billiardData_count);                            // 0. count
        TextView date = (TextView) convertView.findViewById(R.id.clv_billiardData_date);                              // 1. date
        TextView gameMode = (TextView) convertView.findViewById(R.id.clv_billiardData_gameMode);                      // 2. game mode
        LinearLayout playerCountWrapper = (LinearLayout) convertView.findViewById(R.id.clv_billiardData_playerCount_wrapper);
        TextView playerCount = (TextView) convertView.findViewById(R.id.clv_billiardData_playerCount);                // 3. player count
        TextView winnerName = (TextView) convertView.findViewById(R.id.clv_billiardData_winnerName);                  // 5. winner name
        TextView playTime = (TextView) convertView.findViewById(R.id.clv_billiardData_playTime);                      // 6. play time
        TextView score = (TextView) convertView.findViewById(R.id.clv_billiardData_score);                            // 7. score
        TextView cost = (TextView) convertView.findViewById(R.id.clv_billiardData_cost);                              // 8. cost

        // init widget :
        count.setText(billiardData.getCount() + "");                                                                // 0. count
        date.setText(billiardData.getDate());                                                                       // 1. date
        gameMode.setText(billiardData.getGameMode());                                                               // 2. game mode
        playerCount.setText(billiardData.getPlayerCount() + "");                                                    // 3. player count
        winnerName.setText(billiardData.getWinnerName());                                                           // 5. winner name
        playTime.setText(DataFormatUtil.formatOfPlayTime(billiardData.getPlayTime()));                    // 6. play time
        score.setText(billiardData.getScore());                                                                     // 7. score
        cost.setText(DataFormatUtil.formatOfCost(billiardData.getCost()));                                // 8. cost

        // countWrapper : background
        // userData 와 billiardData 를 이용하여
        // 해당 게임의 승패 여부를 판단하여 색을 다르게 한다.
        if ((userData.getId() == billiardData.getWinnerId()) && (userData.getName().equals(billiardData.getWinnerName()))) {
            // countWrapper 의 background = R.color.colorRed (승리는 Red)
            countWrapper.setBackgroundResource(R.color.colorRed);
        } else {
            // countWrapper 의 background = R.color.colorBlue (패배는 BLUE
            countWrapper.setBackgroundResource(R.color.colorBlue);
        }

        // countWrapper : click listener
        // 클릭을 하면 해당 게임에 대한 수정을 할 수 있는 화면으로 이동
        countWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setClickListenerOfCountWrapper(parent.getContext(), billiardData, userData);

            }
        });


        // playerCountWrapper
        playerCountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setClickListenerOfPlayerCountWrapper(parent.getContext(), billiardData);
            }
        });

        return convertView;
    } // End of method [getView]


    /**
     * countWrapper : click listener
     *
     * @param context
     * @param billiardData
     * @param userData
     */
    private void setClickListenerOfCountWrapper(Context context, BilliardData billiardData, UserData userData) {
        final String METHOD_NAME = "[setClickListenerOfCountWrapper] ";

        // <과정>
        // 1. 게임에 참가한 플레이어 목록을 가져온다.
        // 2. 게임에 참가한 플레이어 목록에서 친구의 id 로 친구 목록을 가져온다.
        // 3. 사용자 확인 후 게임에 대한 수정을 위한 수정 화면으로 이동한다.

        // <1>
        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {
                        playerDataArrayList.addAll(
                                playerDbManager2.loadAllContentByBilliardCount(billiardData.getCount())
                        );
                    }
                }
        );

        // <2>
        ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

        appDbManager.requestFriendQuery(
                new AppDbManager.FriendQueryRequestListener() {
                    @Override
                    public void requestQuery(FriendDbManager2 friendDbManager2) {

                        // index 가 0 이면 나의 정보(userData) 이므로
                        // index 가 1~3 까지가 friendData 이다.
                        for (int index = 1; index < playerDataArrayList.size(); index++) {
                            friendDataArrayList.add(
                                    friendDbManager2.loadContentById(
                                            playerDataArrayList.get(index).getPlayerId()
                                    )
                            );
                        }
                    }
                }
        );

        // <3>
        // <사용자 확인>
        new AlertDialog.Builder(context)
                .setTitle(R.string.lva_billiardLvAdapter_modifyCheck_title)
                .setMessage(R.string.lva_billiardLvAdapter_modifyCheck_message)
                .setPositiveButton(R.string.lva_billiardLvAdapter_modifyCheck_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 Intent 생성
                        Intent intent = new Intent(context, BilliardModifyActivity.class);

                        SessionManager.setBilliardDataFromIntent(intent, billiardData);                           // 게임 정보
                        SessionManager.setUserDataFromIntent(intent, userData);                                   // 나의 정보
                        SessionManager.setParticipatedFriendListInGameFromIntent(intent, friendDataArrayList);    // 게임에 참여한 친구
                        SessionManager.setPlayerDataArrayListFromIntent(intent, playerDataArrayList);             // 게임에 참여한 모든 플레이어 : 나 + 게임에_참여한_친구


                        DeveloperManager.printLogUserData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                userData
                        );

                        DeveloperManager.printLogBilliardData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                billiardData
                        );

                        DeveloperManager.printLogFriendData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                friendDataArrayList
                        );
                        DeveloperManager.printLogPlayerData(
                                CLASS_LOG_SWITCH,
                                CLASS_NAME,
                                playerDataArrayList
                        );

                        ((Activity) context).finish();
                        context.startActivity(intent);


                    }
                })
                .setNegativeButton(R.string.lva_billiardLvAdapter_modifyCheck_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [setClickListenerOfCountLinearLayout]


    /**
     * playerCountWrapper : click listener
     *
     * @param context
     * @param billiardData
     */
    private void setClickListenerOfPlayerCountWrapper(Context context, BilliardData billiardData) {
        final String METHOD_NAME = "[setClickListenerOfPlayerCountWrapper] ";

        // <과정>
        // 1. 게임에 참가한 플레이어 목록을 가져온다.
        // 2. 게임에 플레이어 정보를 보여준다.

        // <1>
        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {
                        playerDataArrayList.addAll(
                                playerDbManager2.loadAllContentByBilliardCount(billiardData.getCount())
                        );
                    }
                }
        );

        // <2>
        PlayerListDialog dialog = PlayerListDialog.newInstance(playerDataArrayList);
        dialog.setStyle(
                DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
        );
        dialog.show(fragmentManager, BilliardLvAdapter2.class.getSimpleName());

    } // End of method [setClickListenerOfPlayerCount]


}
