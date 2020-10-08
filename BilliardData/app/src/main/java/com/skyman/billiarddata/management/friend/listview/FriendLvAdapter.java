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
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.data.FriendDataFormatter;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;

import java.util.ArrayList;

public class FriendLvAdapter extends BaseAdapter {

    // instance variable
    private FriendDbManager friendDbManager;
    private ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

    // constructor
    public FriendLvAdapter(FriendDbManager friendDbManager) {
        this.friendDbManager = friendDbManager;
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

        // [lv/C]TextView : 매핑된 count, data, targetScore, speciality, playTime, winner, score, cost 값 셋팅
        id.setText(Long.toString(friendData.getId()));
        name.setText(friendData.getName());
        gameRecord.setText(FriendDataFormatter.getFormatOfGameRecord(friendData.getGameRecordWin(), friendData.getGameRecordLoss()));
        totalPlayTime.setText(FriendDataFormatter.getFormatOfPlayTime(Integer.toString(friendData.getTotalPlayTime())));
        totalCost.setText(FriendDataFormatter.getFormatOfCost(Integer.toString(friendData.getTotalCost())));

        // check : recentPlayDate 가 '-1' 이면, '최근 경기 없음'

        // [check 2] : recentPlayDate 가 초기값 '-1' 로 되어있다.
        if(friendData.getRecentPlayDate().equals("-1")){

            // [lv/C]TextView : recentPlayData 를 초기값 '-1' 이 아닌 '최근 경기 없음' 으로 출력한다.
            recentPlayDate.setText("최근 경기 없음");

        } else {

            // [lv/C]Text : recentPlayDate 에 초기값이 아닌 추가된 내용이 있을 때는 그 값으로 출력한다.
            recentPlayDate.setText(friendData.getRecentPlayDate());

        } // [check 2]

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
     * @param recentPlayDate    [5] 최근 게임 날짜
     * @param totalPlayTime     [6] 총 게임 시간
     * @param totalCost         [7] 총 비용
     * */
    public void addItem(long id, long userId, String name, int gameRecordWin, int gameRecordLoss, String recentPlayDate, int totalPlayTime, int totalCost){

        // [lv/C]FriendData : 매개변수로 받은 데이터를 FriendData 담는다.
        FriendData friendData = new FriendData();
        friendData.setId(id);
        friendData.setUserId(userId);
        friendData.setName(name);
        friendData.setGameRecordWin(gameRecordWin);
        friendData.setGameRecordLoss(gameRecordLoss);
        friendData.setRecentPlayDate(recentPlayDate);
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

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // [lv/C]AlertDialog : 초기값 설정
        builder.setTitle("진짜 삭제 하겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [iv/C]FriendDbManager : 해당 id 의 friend 의 데이터를 삭제한다.
                        friendDbManager.deleteContentById(id);
                        DeveloperManager.displayLog("[LvA]_FriendLvAdapter", "[showDialogToCheckWhetherToDeleteFriendData] " + id + " 번 친구를 삭제하였습니다.");
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

