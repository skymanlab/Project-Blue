package com.skyman.billiarddata.management.friend.listview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.user.UserFriend;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.data.FriendDataFormatter;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;

import java.util.ArrayList;

public class FriendLvAdapter extends BaseAdapter {

    // ArrayList<FriendData> :
    private ArrayList<FriendData> friendDataArrayList = new ArrayList<>();
    private FriendDbManager friendDbManager = null;

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
        /* 'activity_select_item' Layout 을 inflate 하여 convertView 참조 획득*/
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_lv_friend_data, parent, false);
        }

        // TextView : id, name, gameRecord, recentPlayTime, totalPlayTime, totalCost
        TextView id = (TextView) convertView.findViewById(R.id.c_lv_friend_data_id);                                // 0. id
        TextView name = (TextView) convertView.findViewById(R.id.c_lv_friend_data_name);                            // 2. name
        TextView gameRecord = (TextView) convertView.findViewById(R.id.c_lv_friend_data_game_record);               // 3&4. game record
        TextView recentPlayDate = (TextView) convertView.findViewById(R.id.c_lv_friend_data_recent_play_date);      // 5. recent play date
        TextView totalPlayTime = (TextView) convertView.findViewById(R.id.c_lv_friend_data_total_play_time);        // 6. total play time
        TextView totalCost = (TextView) convertView.findViewById(R.id.c_lv_friend_data_total_cost);                 // 7. total cost

        // FriendData
        final FriendData friendData = (FriendData) getItem(position);

        // TextView : 내용 셋팅
        id.setText(Long.toString(friendData.getId()));
        name.setText(friendData.getName());
        gameRecord.setText(FriendDataFormatter.setFormatToGameRecord(friendData.getGameRecordWin(), friendData.getGameRecordLoss()));
        totalPlayTime.setText(FriendDataFormatter.setFormatToPlayTime(Integer.toString(friendData.getTotalPlayTime())));
        totalCost.setText(FriendDataFormatter.setFormatToCost(Integer.toString(friendData.getTotalCost())));

        // check : recentPlayDate 가 '-1' 이면, '최근 경기 없음'
        if(friendData.getRecentPlayDate().equals("-1")){
            recentPlayDate.setText("최근 경기 없음");
        } else {
            recentPlayDate.setText(friendData.getRecentPlayDate());
        }

        return convertView;
    }

    /* method : friend 테이블에서 읽어온 내용을 ArrayList<FriendData> 에 추가한다. */
    public void addItem(long id, long userId, String name, int gameRecordWin, int gameRecordLoss, String recentPlayDate, int totalPlayTime, int totalCost){
        // FriendData : 매개변수로 가져온 값을 friendData 에 넣기
        FriendData friendData = new FriendData();
        friendData.setId(id);
        friendData.setUserId(userId);
        friendData.setName(name);
        friendData.setGameRecordWin(gameRecordWin);
        friendData.setGameRecordLoss(gameRecordLoss);
        friendData.setRecentPlayDate(recentPlayDate);
        friendData.setTotalPlayTime(totalPlayTime);
        friendData.setTotalCost(totalCost);

        // ArrayList<FriendData> : 위 의 friendData 를 배열에 넣기
        friendDataArrayList.add(friendData);
    }

    /* method : AlertDialog */
    private void showAlert(final Context context, final long id) {
        // AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // AlertDialog : builder setting
        builder.setTitle("진짜 삭제 하겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // FriendDbManager : 해당 아이디로 친구 내용 삭제
                        FriendDbManager friendDbManager = new FriendDbManager(context);
                        friendDbManager.init_db();
                        friendDbManager.delete_content(id);
                        DeveloperManager.displayLog("FriendLvAdapter", id + " 번 친구를 삭제하였습니다.");
                    }
                })
                .setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}

