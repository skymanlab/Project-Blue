package com.skyman.billiarddata.factivity.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.BilliardInputActivity;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbHelper;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInput extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // value : activity 의 widget 객체 선언
    EditText name;
    EditText targetScore;
    RadioGroup speciality;
    Button save;
    Button modify;
    Button delete;

    // value : userData - 화면에 뿌릴 데이터를 담을 객체 선언
    UserDbManager userDbManager;
    UserData userData;
    ViewPager userPager;

    // constructor
    public UserInput(UserDbManager userDbManager, UserData userData, ViewPager userPager) {
        this.userDbManager = userDbManager;
        this.userData = userData;
        this.userPager = userPager;
    }

    // constructor
    public UserInput() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInputData.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInput newInstance(String param1, String param2) {
        UserInput fragment = new UserInput();
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
        // view : view setting
        final View view = inflater.inflate(R.layout.fragment_user_input, container, false);

        // TextView : userName setting
        name = (EditText) view.findViewById(R.id.f_user_input_name);

        // TextView : userTargetScore setting
        targetScore = (EditText) view.findViewById(R.id.f_user_input_target_score);

        // RadioGroup : userSpeciality setting
        speciality = (RadioGroup) view.findViewById(R.id.f_user_input_speciality);

        // Button : save setting
        save = (Button) view.findViewById(R.id.f_user_input_bt_save);
        // check : save OnClickListener setting - userData
        if (userData == null) {
            // userData 가 없으면 save
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeveloperManager.displayLog("F UserInput", "** save click listener - method is executing.......");

                    // RadioButton : selectedSpeciality - userSpeciality 에서 선택 된 RadioButton 의 text 값을 가져온다.
                    RadioButton selectedSpeciality = (RadioButton) view.findViewById(speciality.getCheckedRadioButtonId());

                    // check : userTargetScore - 입력 된 값이 있을 때만 실행
                    if (!name.getText().toString().equals("") &&                            // 1. name
                            !targetScore.getText().toString().equals("") &&                 // 2. target score
                            !selectedSpeciality.getText().toString().equals("")             // 3. speciality
                    ) {

                        // UserDbManager : save_content method execute - 입력 된 정보를 저장하기
                        userDbManager.save_content(
                                name.getText().toString(),                                          // 0. name
                                Integer.parseInt(targetScore.getText().toString()),                 // 1. target score
                                selectedSpeciality.getText().toString(),                            // 2. speciality
                                0,                                                   // 3. game record win
                                0,                                                   // 4. game record loss
                                0,                                                      // 5. total play time
                                0                                                          // 6. total cost
                        );

                        // move : activity move - 지금 activity 는 stack 에서 제거하고, 현재 페이지를 다시 road 한다.
                        Intent intent = new Intent(view.getContext(), UserManagerActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    } else {
                        DeveloperManager.displayLog("F userInput", "일단 수지를 입력해주세요.");
                    }

                    DeveloperManager.displayLog("F UserInput", "** save click listener - method is complete.");
                }
            });

        } else {
            // userData 가 있으면 disable
            // set : 나의 정보가 표시하고
            name.setText(userData.getName());
            targetScore.setText(Integer.toString(userData.getTargetScore()));
            RadioButton specialityThreeCushion = (RadioButton) view.findViewById(R.id.f_user_input_speciality_three_cushion);
            RadioButton specialityFourBall = (RadioButton) view.findViewById(R.id.f_user_input_speciality_four_ball);
            RadioButton specialityPocketBall = (RadioButton) view.findViewById(R.id.f_user_input_speciality_pocket_ball);
            String specialityContent = userData.getSpeciality();
            if (specialityContent.equals("3구")) {
                specialityThreeCushion.setChecked(true);
            } else if (specialityContent.equals("4구")) {
                specialityFourBall.setChecked(true);
            } else if (specialityContent.equals("포켓볼")) {
                specialityPocketBall.setChecked(true);
            }

            // set : enable false setting - 나의 정보가 저장 된 것이 있을 경우 입력 하지 못하도록 설정
            name.setEnabled(false);
            targetScore.setEnabled(false);
            speciality.setEnabled(false);
            save.setEnabled(false);
        }

        // Button : modify setting
        modify = (Button) view.findViewById(R.id.f_user_input_bt_modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UserDbManager :
                UserDbManager userDbManager = new UserDbManager(view.getContext());
                userDbManager.init_db();
            }
        });

        // Button : delete setting
        delete = (Button) view.findViewById(R.id.f_user_input_bt_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UserDbManager : delete_contents method - user table 의 모든 내용 삭제
                if (userData != null) {
                    DeveloperManager.displayLog("UserInput", "데이터가 있어서 삭제 시작");
                    userDbManager.delete_contents();

                    // set : 다시 입력해야 함으로 최초의 상태로 setting
                    name.setText(R.string.f_user_input_name_hint);
                    targetScore.setText(R.string.f_user_input_target_score_hint);
                    RadioButton selectedSpeciality = (RadioButton) view.findViewById(R.id.f_user_input_speciality_three_cushion);
                    selectedSpeciality.setChecked(true);

                    // set : name, targetScore, save - enable
                    name.setEnabled(true);
                    targetScore.setEnabled(true);
                    save.setEnabled(true);

                    // 삭제 된 데이터는 생성할 때 넘어온 값이므로 null로 만들어야 함.
                    userData = null;
                } else {
                    DeveloperManager.displayLog("UserInput", "데이터가 없어서 삭제 안함");
                }
            }
        });

        // return : view - 해당 뷰를 셋팅하고 넘겨주기
        return view;
    }
}