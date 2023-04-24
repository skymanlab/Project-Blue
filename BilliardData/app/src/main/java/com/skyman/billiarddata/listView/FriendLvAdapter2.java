package com.skyman.billiarddata.listView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.etc.DataFormatUtil;
import com.skyman.billiarddata.etc.database.AppDbManager;

import java.util.ArrayList;

public class FriendLvAdapter2 extends BaseAdapter {

    // constant
    private final String CLASS_NAME = FriendLvAdapter2.class.getSimpleName();

    // instance variable
    private ArrayList<FriendData> friendDataArrayList;
    private AppDbManager appDbManager;

    // constructor
    public FriendLvAdapter2(ArrayList<FriendData> friendDataArrayList, AppDbManager appDbManager) {
        this.friendDataArrayList = friendDataArrayList;
        this.appDbManager = appDbManager;
    }

    @Override
    public int getCount() {
        return friendDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // inflate
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_lv_friend_data, parent, false);

        // FriendData 가져오기
        FriendData friendData = (FriendData) getItem(position);


        // 0. id (primary key/autoincrement)
        // 1. user id
        // 2. name
        // 3. game record win
        // 4. game record loss
        // 5. recent game billiard count
        // 6. total play time
        // 7. total cost

        // connect widget
        TextView id = (TextView) convertView.findViewById(R.id.clv_friend_data_id);                                // 0. id
        TextView name = (TextView) convertView.findViewById(R.id.clv_friend_data_name);                            // 2. name
        TextView gameRecord = (TextView) convertView.findViewById(R.id.clv_friend_data_game_record);               // 3&4. game record
        TextView recentPlayDate = (TextView) convertView.findViewById(R.id.clv_friend_data_recent_play_date);      // 5. recent play date
        TextView totalPlayTime = (TextView) convertView.findViewById(R.id.clv_friend_data_total_play_time);        // 6. total play time
        TextView totalCost = (TextView) convertView.findViewById(R.id.clv_friend_data_total_cost);                 // 7. total cost

        // init widget
        id.setText(Long.toString(friendData.getId()));
        name.setText(friendData.getName());
        gameRecord.setText(DataFormatUtil.formatOfGameRecord(friendData.getGameRecordLoss(), friendData.getGameRecordWin()));
        totalPlayTime.setText(DataFormatUtil.formatOfPlayTime(Integer.toString(friendData.getTotalPlayTime())));
        totalCost.setText(DataFormatUtil.formatOfCost(Integer.toString(friendData.getTotalCost())));

        appDbManager.requestBilliardQuery(
                new AppDbManager.BilliardQueryRequestListener() {
                    @Override
                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                        // 최근에 게임을 하면
                        // 그 게임의 count(countOfBilliardData) 가 입력 되었을 것이고
                        // count 는 0 보다 큰 값이 입력 된다.
                        if (friendData.getRecentGameBilliardCount() > 0) {

                            // 최근 참가한 경기 Date 를 가져오기 위해서
                            BilliardData billiardData = billiardDbManager2.loadContentByCount(friendData.getRecentGameBilliardCount());

                            // 화면에 표시
                            recentPlayDate.setText(billiardData.getDate());

                        } else {

                            // 화면에 표시
                            recentPlayDate.setText(R.string.F_userFriend_noParticipatedRecentGame);

                        }

                    }
                }
        );

        return convertView;
    }


    /**
     * [method] 삭제 버튼을 눌렀을 때 해당 id 의 friend 데이터를 삭제할지 물어보는 dialog 를 보여준다.
     */
    private void showDialogOfDeleteFriendData(Context context, long id) {
        final String METHOD_NAME = "[showDialogOfDeleteFriendData] ";

        new AlertDialog.Builder(context)
                .setTitle("진짜 삭제 하겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogOfDeleteFriendData]


}

