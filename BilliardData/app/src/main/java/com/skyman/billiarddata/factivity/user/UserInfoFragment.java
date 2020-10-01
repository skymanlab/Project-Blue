package com.skyman.billiarddata.factivity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.data.UserDataFormatter;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // value : activity 의 widget 객체 선언
    private TextView name;
    private TextView targetScore;
    private TextView speciality;
    private TextView gameRecord;
    private TextView totalPlayTime;
    private TextView totalCost;
    private TextView recentGamePlayerId;
    private TextView recentPlayDate;

    // value : DbManager
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;

    // value : Data
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    // constructor
    public UserInfoFragment(UserDbManager userDbManager, UserData userData, FriendDbManager friendDbManager, ArrayList<FriendData> friendDataArrayList) {
        this.userDbManager = userDbManager;
        this.userData = userData;
        this.friendDbManager = friendDbManager;
        this.friendDataArrayList = friendDataArrayList;
    }

    // constructor
    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TextView : name, targetScore, speciality, gameRecord, totalPlayTime, totalCost setting - id 값을 가져온다.
        name = (TextView) view.findViewById(R.id.f_user_info_name);
        targetScore = (TextView) view.findViewById(R.id.f_user_info_target_score);
        speciality = (TextView) view.findViewById(R.id.f_user_info_speciality);
        gameRecord = (TextView) view.findViewById(R.id.f_user_info_game_record);
        totalPlayTime = (TextView) view.findViewById(R.id.f_user_info_total_play_time);
        totalCost = (TextView) view.findViewById(R.id.f_user_info_total_cost);
        recentGamePlayerId = (TextView) view.findViewById(R.id.f_user_info_recent_game_player_id);
        recentPlayDate = (TextView) view.findViewById(R.id.f_user_info_recent_play_date);

        // check : userData 의 참조 값이 있냐?
        if (userData != null) {
            // display : userData 내용 뿌려주기
            displayUserData();
        } else {

        }
    }

    /* method : userData 가 있으면 화면에 뿌려준다.*/
    private void displayUserData() {
        // String : nameContent, targetScoreContent, specialityContent, gameRecordContent, totalPlayTimeContent, totalCostContent - userData 에서 가져온 내용을 적절한 형태로 변경하기
        String nameContent = userData.getName();
        String targetScoreContent = Integer.toString(userData.getTargetScore());
        String specialityContent = userData.getSpeciality();
        String gameRecordContent = UserDataFormatter.setFormatToGameRecord(userData.getGameRecordWin(), userData.getGameRecordLoss());
        String totalPlayTimeContent = Integer.toString(userData.getTotalPlayTime());
        String totalCostContent = Integer.toString(userData.getTotalCost());
        String recentPlayDateContent = userData.getRecentPlayDate();

        DeveloperManager.displayToUserData("[F] UserInfo", userData);

        // TextView : name, targetScore, speciality, gameRecord, totalPlayTime, totalCost - 위 의 내용을 화면에 뿌려준다.
        name.setText(nameContent);
        targetScore.setText(targetScoreContent);
        speciality.setText(specialityContent);
        gameRecord.setText(gameRecordContent);
        totalPlayTime.setText(UserDataFormatter.setFormatToPlayTime(totalPlayTimeContent));
        totalCost.setText(UserDataFormatter.setFormatToCost(totalCostContent));

        // check : userData 의 recentPlayDate 가 "-1" 이 아닐 때
        if (userData.getRecentPlayDate().equals("-1")) {
            recentPlayDate.setText("최근 경기 없음");
        } else {
            recentPlayDate.setText(recentPlayDateContent);
        }

        // check : userData 의 recentGamePlayerId 가 0 이상 일때
        if (userData.getRecentGamePlayerId() > 0) {
            recentGamePlayerId.setText(friendDataArrayList.get(((int) userData.getRecentGamePlayerId() - 1)).getName());
        } else {
            recentGamePlayerId.setText("최근 경기 없음");
        }
    }
}