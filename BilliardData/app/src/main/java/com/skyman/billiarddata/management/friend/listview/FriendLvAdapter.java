package com.skyman.billiarddata.management.friend.listview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.data.FriendDataFormatter;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;

import java.util.ArrayList;

public class FriendLvAdapter extends BaseAdapter {

    // constant
    private final String CLASS_NAME_LOG = "[LvA]_FriendLvAdapter";

    // instance variable
    private FriendDbManager friendDbManager;
    private BilliardDbManager billiardDbManager;
    private ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

    // constructor
    public FriendLvAdapter(FriendDbManager friendDbManager, BilliardDbManager billiardDbManager) {
        this.friendDbManager = friendDbManager;
        this.billiardDbManager = billiardDbManager;
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

        // [lv/C]Context : context 가져오기
        Context context = parent.getContext();

        // [check 1] : convertView 가 있을 때
        if(convertView == null){

            // [lv/C]LayoutInflater : ????
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // [lv/C]View : custom_lv_friend_data layout 을 가져오기
            convertView = inflater.inflate(R.layout.custom_lv_friend_data, parent, false);

        } // [check 1]

        // [lv/C]TextView : id, name, gameRecord, recentPlayTime, totalPlayTime, totalCost mapping
        TextView id = (TextView) convertView.findViewById(R.id.c_lv_friend_data_id);                                // 0. id
        TextView name = (TextView) convertView.findViewById(R.id.c_lv_friend_data_name);                            // 2. name
        TextView gameRecord = (TextView) convertView.findViewById(R.id.c_lv_friend_data_game_record);               // 3&4. game record
        TextView recentPlayDate = (TextView) convertView.findViewById(R.id.c_lv_friend_data_recent_play_date);      // 5. recent play date
        TextView totalPlayTime = (TextView) convertView.findViewById(R.id.c_lv_friend_data_total_play_time);        // 6. total play time
        TextView totalCost = (TextView) convertView.findViewById(R.id.c_lv_friend_data_total_cost);                 // 7. total cost

        // [lv/C]FriendData : 각 리스트에 뿌려줄 아이템을 받아오는데 FriendData 재활용
        FriendData friendData = (FriendData) getItem(position);

        // [lv/C]BilliardData : friendData 의 recentGameBilliardCount 로 billiardData 가져오기
        BilliardData billiardData = this.billiardDbManager.loadAllContentByCount(friendData.getRecentGameBilliardCount());

        // [lv/C]TextView : 매핑된 count, data, targetScore, speciality, playTime, winner, score, cost 값 셋팅
        id.setText(Long.toString(friendData.getId()));
        name.setText(friendData.getName());
        gameRecord.setText(FriendDataFormatter.getFormatOfGameRecord(friendData.getGameRecordWin(), friendData.getGameRecordLoss()));
        totalPlayTime.setText(FriendDataFormatter.getFormatOfPlayTime(Integer.toString(friendData.getTotalPlayTime())));
        totalCost.setText(FriendDataFormatter.getFormatOfCost(Integer.toString(friendData.getTotalCost())));

        // [check 1] : 참가한 게임이 있다.
        if (billiardData != null) {
            recentPlayDate.setText(billiardData.getDate());
        } else {
            recentPlayDate.setText("참가 게임 없음!");
        } // [check 1]
        return convertView;
    }


    /**
     * [method] 매개 변수로 받은 값을 FriendData 로 만들어 friendDataArrayList 에 추가한다.
     *
     * @param id    [0] id
     * @param userId            [1] user id ( friend 를 추가한 주체 )
     * @param name              [2] 이름
     * @param gameRecordWin     [3] 승리 수
     * @param gameRecordLoss    [4] 패배 수
     * @param recentGameBilliardCount    [5] 최근 게임의 billiard count
     * @param totalPlayTime     [6] 총 게임 시간
     * @param totalCost         [7] 총 비용
     * */
    public void addItem(long id, long userId, String name, int gameRecordWin, int gameRecordLoss, long recentGameBilliardCount, int totalPlayTime, int totalCost){

        // [lv/C]FriendData : 매개변수로 받은 데이터를 FriendData 담는다.
        FriendData friendData = new FriendData();
        friendData.setId(id);                                           // 0. id
        friendData.setUserId(userId);                                   // 1. user id
        friendData.setName(name);                                       // 2. name
        friendData.setGameRecordWin(gameRecordWin);                     // 3.
        friendData.setGameRecordLoss(gameRecordLoss);
        friendData.setRecentGameBilliardCount(recentGameBilliardCount);
        friendData.setTotalPlayTime(totalPlayTime);
        friendData.setTotalCost(totalCost);

        // [iv/C]ArrayList<BilliardData> : 위 에서 만든 데이터를 배열에 담는다.
        this.friendDataArrayList.add(friendData);

    } // End of method [addItem]


    /**
     * [method] 매개 변수로 받은 값을 FriendData 로 만들어 friendDataArrayList 에 추가한다.
     *
     * @param friendData friend 정보가 담긴
     * */
    public void addItem(FriendData friendData){

        // [iv/C]ArrayList<BilliardData> : 위 에서 만든 데이터를 배열에 담는다.
        this.friendDataArrayList.add(friendData);

    } // End of method [addItem]


    /**
     * [method] 삭제 버튼을 눌렀을 때 해당 id 의 friend 데이터를 삭제할지 물어보는 dialog 를 보여준다.
     *
     */
    private void showDialogToCheckWhetherToDeleteFriendData(final Context context, final long id) {

        final String METHOD_NAME= "[showDialogToCheckWhetherToDeleteFriendData] ";

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // [lv/C]AlertDialog : 초기값 설정
        builder.setTitle("진짜 삭제 하겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [iv/C]FriendDbManager : 해당 id 의 friend 의 데이터를 삭제한다.
                        friendDbManager.deleteContentById(id);
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + id + " 번 친구를 삭제하였습니다.");
                    }
                })
                .setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    } // End of method [showAlert]


}

