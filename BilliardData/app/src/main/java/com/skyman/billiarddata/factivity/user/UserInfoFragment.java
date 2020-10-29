package com.skyman.billiarddata.factivity.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.data.UserDataFormatter;

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
    // constant
    private final String CLASS_NAME_LOG = "[F]_UserInfoFragment";
    // instance variable
    private TextView id;
    private TextView name;
    private TextView targetScore;
    private TextView speciality;
    private TextView gameRecord;
    private TextView totalPlayTime;
    private TextView totalCost;
    private TextView recentPlayDate;
    // instance variable
    private BilliardDbManager billiardDbManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // constructor
    public UserInfoFragment(BilliardDbManager billiardDbManager, UserData userData, ArrayList<FriendData> friendDataArrayList) {
        this.billiardDbManager = billiardDbManager;
        this.userData = userData;
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

        final String METHOD_NAME = "[getFormatOfCost] ";

        // [method]mappingOfWidget : fragment_user_info layout 의 widget 을 mapping
        mappingOfWidget(view);

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // [lv/C]BilliardData : userData 의 recentGameBilliardCount 로 해당 billiard 데이터 가져오기
            BilliardData billiardData = this.billiardDbManager.loadAllContentByCount(this.userData.getRecentGameBilliardCount());

            // [method]displayUserData : userData 값을 widget 을 setText 하기
            setInitialData(billiardData);

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없으므로 초기 세팅으로");
        } // [check 1]

    } // End of method [onViewCreated]


    /**
     * [method] widget mapping
     */
    private void mappingOfWidget(View view) {

        // [iv/C]TextView : id mapping
        this.id = (TextView) view.findViewById(R.id.f_user_info_id);

        // [iv/C]TextView : name mapping
        this.name = (TextView) view.findViewById(R.id.f_user_info_name);

        // [iv/C]TextView : targetScore mapping
        this.targetScore = (TextView) view.findViewById(R.id.f_user_info_target_score);

        // [iv/C]TextView : speciality mapping
        this.speciality = (TextView) view.findViewById(R.id.f_user_info_speciality);

        // [iv/C]TextView : gameRecord mapping
        this.gameRecord = (TextView) view.findViewById(R.id.f_user_info_game_record);

        // [iv/C]TextView : recentPlayDate mapping
        this.recentPlayDate = (TextView) view.findViewById(R.id.f_user_info_recent_play_date);

        // [iv/C]TextView : totalPlayTime mapping
        this.totalPlayTime = (TextView) view.findViewById(R.id.f_user_info_total_play_time);

        // [iv/C]TextView : totalCost mapping
        this.totalCost = (TextView) view.findViewById(R.id.f_user_info_total_cost);

    } // End of method [mappingOfWidget]


    /**
     * [method] userData 의 값들을 mapping 된 widget 에 출력한다.
     */
    private void setInitialData(BilliardData billiardData) {

        DeveloperManager.displayToUserData(CLASS_NAME_LOG, userData);
        // [iv/C]TextView : id 를 userData 의 getId 으로
        id.setText(userData.getId() + "");

        // [iv/C]TextView : name 를 userData 의 getName 으로
        name.setText(userData.getName());

        // [iv/C]TextView : targetScore 를 userData 의 getTargetScore 으로
        targetScore.setText(userData.getTargetScore() + "");

        // [iv/C]TextView : speciality 를 userData 의 getSpeciality 으로
        speciality.setText(userData.getSpeciality());

        // [iv/C]TextView : gameRecord 를 userData 의 getGameRecordWin 와 getGameRecordLoss 를 특정 문자열 형태로 바꾼 값으로
        gameRecord.setText(UserDataFormatter.getFormatOfGameRecord(userData.getGameRecordWin(), userData.getGameRecordLoss()));

        // [iv/C]TextView : totalPlayTime 를 userData 의 getTotalPlayTime 을 특정 문자열로 바꾼 값으로
        totalPlayTime.setText(UserDataFormatter.getFormatOfPlayTime(userData.getTotalPlayTime()));

        // [iv/C]TextView : totalCost 를 userData 의 getTotalCost 을 특정 문자열로 바꾼 값으로
        totalCost.setText(UserDataFormatter.getFormatOfCost(userData.getTotalCost()));

        // [check 1] : 참가한 게임이 있다.
        if (billiardData != null) {

            // [iv/C]TextView : recentPlayData 를 billiardData 의 date 로 설정
            this.recentPlayDate.setText(billiardData.getDate());

        } else {

            // [iv/C]TextView : recentPlayData 를 billiardData 의 date 로 설정
            this.recentPlayDate.setText("참가 게임 없음!");

        } // [check 1]


    } // End of method [setInitialData]
}