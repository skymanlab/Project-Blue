package com.skyman.billiarddata.factivity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

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

    // constant
    private final int MIN_RANGE = 0;            // 최소 범위
    private final int MAX_RANGE = 50;           // 최대 번위

    // value : activity 의 widget 객체 선언
    private EditText name;
    private EditText targetScore;
    private RadioGroup speciality;
    private Button save;
    private Button modify;
    private Button delete;

    // value : userData - 화면에 뿌릴 데이터를 담을 객체 선언
    private UserDbManager userDbManager;
    private UserData userData;
    private ViewPager userPager;

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
        // view 를 초기화 하기 때문에 findViewById를 이용하는 행위는 ==> onViewCreated 에서 할 것!
        return inflater.inflate(R.layout.fragment_user_input, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TextView : userName setting
        name = (EditText) view.findViewById(R.id.f_user_input_name);

        // TextView : userTargetScore setting
        targetScore = (EditText) view.findViewById(R.id.f_user_input_target_score);

        // RadioGroup : userSpeciality setting
        speciality = (RadioGroup) view.findViewById(R.id.f_user_input_speciality);

        // Button : save setting
        save = (Button) view.findViewById(R.id.f_user_input_bt_save);

        // Button : modify setting
        modify = (Button) view.findViewById(R.id.f_user_input_bt_modify);

        // Button : delete setting
        delete = (Button) view.findViewById(R.id.f_user_input_bt_delete);

        // check : 기존(userData)의 내용이 있는지 검사
        if (userData != null) {
            // userData is exist.
            setWidget(view);
        } else {
            // userData is not exist.
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check : 기존(userData)의 내용이 있는지 검사
                if (userData == null) {
                    DeveloperManager.displayLog("F UserInput", "** save click listener - method is executing.......");

                    // RadioButton : selectedSpeciality - userSpeciality 에서 선택 된 RadioButton 의 text 값을 가져온다.
                    RadioButton selectedSpeciality = (RadioButton) view.findViewById(speciality.getCheckedRadioButtonId());
                    String specialityContent = selectedSpeciality.getText().toString();

                    // check : name, targetScore, specialityContent 모든 데이터 입력 되었나요?
                    if (checkedInputAllData(specialityContent)) {
                        // check : 0 < targetScore < 50
                        if (checkTargetScore()) {
                            // check : userDbManager
                            if (userDbManager != null) {
                                // UserDbManager : save_content method execute - 입력 된 정보를 저장하기
                                userDbManager.save_content(
                                        name.getText().toString(),                                          // 1. name
                                        Integer.parseInt(targetScore.getText().toString()),                 // 2. target score
                                        selectedSpeciality.getText().toString(),                            // 3. speciality
                                        0,                                                   // 4. game record win
                                        0,                                                   // 5. game record loss
                                        0,                                                 // 6. recent game player id
                                        "첫번째 입력",                                                    // 7. recent play date
                                        0,                                                      // 8. total play time
                                        0                                                          // 9. total cost
                                );
                                // Intent : UserManagerActivity 에 업데이트된 내용 반영을 위해
                                Intent intent = new Intent(view.getContext(), UserManagerActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                            } else {
                                DeveloperManager.displayLog("F UserInput", "데이터베이스 준비가 되지 않았습니다.");
                            }
                        } else {
                            DeveloperManager.displayLog("F UserInput", "0< targetScore <50 인 값을 입력해주세요.");
                        }
                    } else {
                        DeveloperManager.displayLog("F UserInput", "name, targetScore, speciality 의 모든 값을 입력해주세요.");
                    }
                } else {
                    DeveloperManager.displayLog("F UserInput", "userData 가 있으므로 저장할 필요가 없습니다.");
                }
                DeveloperManager.displayLog("F UserInput", "** save click listener - method is complete.");
            }
        });

        // Button : 'modify' on click listener
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : 기존(userData)의 내용이 있는지 검사
                if (userData != null) {

                    // check : userDbManager 객체가 생성되었는지 검사
                    if (userDbManager != null) {
                        // 변경 할 값 받아오기
                        String nameContent = name.getText().toString();
                        int targetScoreContent = Integer.parseInt(targetScore.getText().toString());
                        RadioButton selectedSpeciality = (RadioButton) view.findViewById(speciality.getCheckedRadioButtonId());
                        String specialityContent = selectedSpeciality.getText().toString();

                        // UserDbManager : update_content method 실행
                        int result = userDbManager.update_content(nameContent, targetScoreContent, specialityContent);

                        // check : result 값으로 update 잘 되었는지 판다
                        if (result == 1) {
                            DeveloperManager.displayLog("UserInput", "1번재 내용이 수정되었습니다.");
                        } else {
                            DeveloperManager.displayLog("UserInput", "1번째 내용을 수정하는데 실패하였습니다.");
                        }
                    } else {
                        DeveloperManager.displayLog("UserInput", "userDbManager 가 없으므로 데이터베이스 메니저를 생성해주세요.");
                    }
                } else {
                    DeveloperManager.displayLog("UserInput", "userData 가 없으므로 수정할 필요가 없습니다. 첫 입력부터 해주세요.");
                }
                DeveloperManager.displayLog("UserInput", "** update_content is complete. - result : ");
            }
        });

        // Button : 'delete' on click listener
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UserDbManager : delete_contents method - user table 의 모든 내용 삭제

                // check : 기존(userData)의 내용이 있는지 검사
                if (userData != null) {
                    DeveloperManager.displayLog("UserInput", "데이터가 있어서 삭제 시작");

                    // check : userDbManager 가 생성되었는지
                    if (userDbManager != null) {
                        // UserDbManager : 테이블의 모든 내용 삭제
                        userDbManager.delete_contents();

                        // move : activity move - 지금 activity 는 stack 에서 제거하고, 현재 페이지를 다시 road 한다.
                        Intent intent = new Intent(view.getContext(), UserManagerActivity.class);
                        getActivity().finish();
                        startActivity(intent);

                        // 삭제 된 데이터는 생성할 때 넘어온 값이므로 null로 만들어야 함.
                        userData = null;
                    } else {
                        DeveloperManager.displayLog("UserInput", "데이터를 가져올 DB를 가져오지 못했습니다.");
                    }
                } else {
                    DeveloperManager.displayLog("UserInput", "데이터가 없어서 삭제 안함");
                }

            }
        });

    }

    /*                                      private method
     *   ============================================================================================
     *  */

    /* method : display, toast 메시지 출력 */
    private void toastHandler(View view, String content) {
        Toast myToast = Toast.makeText(view.getContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

    /* method : userData 이 null 이 아닐 때  */
    private void setWidget(View view) {
        // TextView : name - userData 의 name 으로 채우기
        name.setText(userData.getName());

        // TextView : targetScore - userData 의 targetScore 으로 채우기
        targetScore.setText(Integer.toString(userData.getTargetScore()));

        // RadioGroup : userData 에 저장된 값과 같은 RadioButton 선택
        setCheckToRadioButton(view, userData.getSpeciality());
        DeveloperManager.displayLog("UserInput", "선택 된 주종목 : " + userData.getSpeciality());

        // Button : save disable setting - background 와 enabled 를 true
        save.setBackgroundResource(R.color.colorActivityDisable);
        save.setEnabled(false);

        // Button : modify enable setting - background 와 enabled 를 false
        modify.setBackgroundResource(R.color.colorPrimaryBackground);
        modify.setEnabled(true);

        DeveloperManager.displayLog("F UserInput", "모든 셋팅이 완료 되었습니다.");
    }

    /* method : targetScore 의 입력 값이 MIN_RANGE < targetScore < MAX_RANGE 사이인지 확인 */
    private boolean checkTargetScore() {
        // targetScore : 입력 된 값이  0 < targetScore < 50  확인하기 위한
        int targetScoreContent = Integer.parseInt(targetScore.getText().toString());

        if ((MIN_RANGE < targetScoreContent) && (targetScoreContent < MAX_RANGE)) {
            return true;
        } else {
            return false;
        }
    }

    /* method : name, targetScore, speciality 에 모든 값이 입력 되었는지 확인 */
    private boolean checkedInputAllData(String specialityContent) {
        // check : 매개변수로 입력 되었는지 확인
        if (!name.getText().toString().equals("") && !targetScore.getText().toString().equals("") && !specialityContent.equals("")) {
            // return : 모든 값이 입력 되었으면
            return true;
        } else {
            // return : 모든 값이 입력 안 되었으면
            return false;
        }
    }

    /* method : RadioGroup 에서 특정 RadioButton 에 setCheck(true) 로 만들기*/
    private void setCheckToRadioButton(View view, String specialityContent) {
        RadioButton specialityThreeCushion = (RadioButton) view.findViewById(R.id.f_user_input_speciality_three_cushion);
        RadioButton specialityFourBall = (RadioButton) view.findViewById(R.id.f_user_input_speciality_four_ball);
        RadioButton specialityPocketBall = (RadioButton) view.findViewById(R.id.f_user_input_speciality_pocket_ball);

        switch (specialityContent) {
            case "3구":
                DeveloperManager.displayLog("F UserInput", "3구가 선택되었습니다.");
                specialityThreeCushion.setChecked(true);
                break;
            case "4구":
                DeveloperManager.displayLog("F UserInput", "4구가 선택되었습니다.");
                specialityFourBall.setChecked(true);
                break;
            case "포켓볼":
                DeveloperManager.displayLog("F UserInput", "포켓볼이 선택되었습니다.");
                specialityPocketBall.setChecked(true);
                break;
        }
    }
}