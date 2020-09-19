package com.skyman.billiarddata.factivity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.data.UserDataFomatter;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // value : activity 의 widget 객체 선언
    private TextView userName;
    private TextView targetScore;
    private TextView speciality;
    private TextView gameRecord;
    private TextView totalPlayTime;
    private TextView totalCost;

    // SQLite DB Helper Manager 객체 선언
    private UserDbManager userDbManager;

    public UserInfo() {
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
    public static UserInfo newInstance(String param1, String param2) {
        UserInfo fragment = new UserInfo();
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
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        userName = (TextView) view.findViewById(R.id.f_userinfo_user_name);
        targetScore = (TextView) view.findViewById(R.id.f_userinfo_target_score);
        speciality = (TextView) view.findViewById(R.id.f_userinfo_speciality);
        gameRecord = (TextView) view.findViewById(R.id.f_userinfo_game_record);
        totalPlayTime = (TextView) view.findViewById(R.id.f_userinfo_total_play_time);
        totalCost = (TextView) view.findViewById(R.id.f_userinfo_total_cost);

        userDbManager = new UserDbManager(view.getContext());
        userDbManager.init_tables();
        ArrayList<UserData> userDataArrayList = userDbManager.load_contents();

        if(userDataArrayList != null){
            for(int position=0; position < userDataArrayList.size() ; position++) {
                userName.setText(userDataArrayList.get(position).getUserName());
                targetScore.setText(Integer.toString(userDataArrayList.get(0).getTargetScore()));
                speciality.setText(userDataArrayList.get(position).getSpeciality());
                gameRecord.setText(UserDataFomatter.setFormatToGameRecord(userDataArrayList.get(position).getGameRecordWin(), userDataArrayList.get(position).getGameRecordLoss()));
                totalPlayTime.setText(Integer.toString(userDataArrayList.get(0).getTotalPlayTime()));
                totalCost.setText(Integer.toString(userDataArrayList.get(0).getTotalCost()));
                DeveloperManager.displayLog("UserInfo", "내용 가져오기 성공");
            }

        } else {
            DeveloperManager.displayLog("UserInfo", "내용 가져오기 실패");
        }
        return view;
    }
}