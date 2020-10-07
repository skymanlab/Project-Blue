package com.skyman.billiarddata.factivity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInputFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // constant
    private final int MIN_RANGE = 0;            // 최소 범위
    private final int MAX_RANGE = 50;           // 최대 번위
    // instance variable
    private EditText name;
    private EditText targetScore;
    private RadioGroup speciality;
    private Button save;
    private Button modify;
    private Button delete;
    // instance variable
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // constructor
    public UserInputFragment(UserDbManager userDbManager, UserData userData, FriendDbManager friendDbManager, ArrayList<FriendData> friendDataArrayList) {
        this.userDbManager = userDbManager;
        this.userData = userData;
        this.friendDbManager = friendDbManager;
        this.friendDataArrayList = friendDataArrayList;
    }

    // constructor
    public UserInputFragment() {
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
    public static UserInputFragment newInstance(String param1, String param2) {
        UserInputFragment fragment = new UserInputFragment();
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

        // [method]mappingOfWidget : fragment_user_input.xml layout 의 widget mapping
        mappingOfWidget(view);

        // [check 1] : user 의 데이터가 있다.
        if (userData != null) {
            setWidgetWithUserData(view);
        } else {
            DeveloperManager.displayLog("[F]_UserInputFragment", "[onViewCreated] 나의 userData 데이터가 없습니다.");
        } // [check 1]


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check : 기존(userData)의 내용이 있는지 검사
                if (userData == null) {
                    // RadioButton : selectedSpeciality - userSpeciality 에서 선택 된 RadioButton 의 text 값을 가져온다.
                    RadioButton selectedSpeciality = (RadioButton) view.findViewById(speciality.getCheckedRadioButtonId());
                    String specialityContent = selectedSpeciality.getText().toString();

                    // check : name, targetScore, specialityContent 모든 데이터 입력 되었나요?
                    if (checkInputAllData(specialityContent)) {
                        // check : 0 < targetScore < 50
                        if (checkTargetScoreRange()) {
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
                                        "-1",                                                  // 7. recent play date
                                        0,                                                      // 8. total play time
                                        0                                                          // 9. total cost
                                );
                                // Intent : UserManagerActivity 에 업데이트된 내용 반영을 위해
                                Intent intent = new Intent(view.getContext(), UserManagerActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                            } else {
                                DeveloperManager.displayLog("[F] UserInput", "[save button] 데이터베이스 준비가 되지 않았습니다.");
                            }
                        } else {
                            DeveloperManager.displayLog("[F] UserInput", "[save button] 0< targetScore <50 인 값을 입력해주세요.");
                        }
                    } else {
                        DeveloperManager.displayLog("[F] UserInput", "[save button] name, targetScore, speciality 의 모든 값을 입력해주세요.");
                    }
                } else {
                    DeveloperManager.displayLog("[F] UserInput", "[save button] userData 가 있으므로 저장할 필요가 없습니다.");
                }
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
                            DeveloperManager.displayLog("[F] UserInput", "[modify button] 1번재 내용이 수정되었습니다.");
                        } else {
                            DeveloperManager.displayLog("[F] UserInput", "[modify button] 1번째 내용을 수정하는데 실패하였습니다.");
                        }

                        // move : activity move - 지금 activity 는 stack 에서 제거하고, 현재 페이지를 다시 road 한다.
                        Intent intent = new Intent(view.getContext(), UserManagerActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    } else {
                        DeveloperManager.displayLog("[F] UserInput", "[modify button] userDbManager 가 없으므로 데이터베이스 메니저를 생성해주세요.");
                    }
                } else {
                    DeveloperManager.displayLog("[F] UserInput", "[modify button] userData 가 없으므로 수정할 필요가 없습니다. 첫 입력부터 해주세요.");
                }
            }
        });

        // Button : 'delete' on click listener
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UserDbManager : delete_contents method - user table 의 모든 내용 삭제

                // check : 기존(userData)의 내용이 있는지 검사
                if (userData != null) {
                    DeveloperManager.displayLog("[F] UserInput", "[delete button] 데이터가 있어서 삭제 시작");

                    // check : userDbManager 가 생성되었는지
                    if (userDbManager != null) {
                        // UserDbManager : 테이블의 모든 내용 삭제
                        userDbManager.delete_contents();

                        // FriendDbManager : 테이블의 모든 내용 삭제
                        friendDbManager.delete_contents();

                        // 삭제 된 데이터는 생성할 때 넘어온 값이므로 null로 만들어야 함.
                        userData = null;

                        // move : activity move - 지금 activity 는 stack 에서 제거하고, 현재 페이지를 다시 road 한다.
                        Intent intent = new Intent(view.getContext(), UserManagerActivity.class);
                        getActivity().finish();
                        startActivity(intent);

                    } else {
                        DeveloperManager.displayLog("[F] UserInput", "[delete button] 데이터를 가져올 DB를 가져오지 못했습니다.");
                    }
                } else {
                    DeveloperManager.displayLog("[F] UserInput", "[delete button] 데이터가 없어서 삭제 안함");
                }

            }
        });

    }

    /*                                      private method
     *   ============================================================================================
     *  */

    /**
     * [method] activity_billiard_input.xml 의 widget 을 mapping(뜻: 하나의 값을 다른 값으로 대응시키는 것을 말한다.)
     */
    private void mappingOfWidget(View view) {

        // [iv/C]TextView : name mapping
        name = (EditText) view.findViewById(R.id.f_user_input_name);

        // [iv/C]TextView : targetScore mapping
        targetScore = (EditText) view.findViewById(R.id.f_user_input_target_score);

        // [iv/c]RadioGroup : speciality mapping
        speciality = (RadioGroup) view.findViewById(R.id.f_user_input_speciality);

        // [iv/C]Button : save mapping
        save = (Button) view.findViewById(R.id.f_user_input_bt_save);

        // [iv/C]Button : modify mapping
        modify = (Button) view.findViewById(R.id.f_user_input_bt_modify);

        // [iv/C]Button : delete mapping
        delete = (Button) view.findViewById(R.id.f_user_input_bt_delete);

    } // End of method [mappingOfWidget]


    /**
     * [method] UserData 가 있어서 화면 재설정한다. 입력 값들은 userData 의 값으로, 버튼들은 비활성화 활성화
     *
     * <p>
     * UserData 의 getName 을 name EditText 에 설정한다.
     * UserData 의 getTargetScore 을 targetScore EditText 에 설정한다.
     * UserData 의 getSpeciality 을 speciality Spinner 의 선택 값으로 설정한다.
     * save Button 은 새로운 UserData 를 만들지 못하게 한다(버튼 비활성화). disable 로 만들고, R.color.colorWidgetDisable 색으로 변경한다.
     * modify Button 은 UserData 의 값을 변경할 수 있도록 한다(버튼 활성화). enable 로 만들고, R.color.colorBackgroundPrimary 색으로 변경한다.
     *
     * <p>
     *     버튼 활성화 색 : R.color.colorBackgroundPrimary
     *     버튼 비활성화 색 : R.color.colorWidgetDisable
     */
    private void setWidgetWithUserData(View view) {

        // [iv/C]Text : userData 의 getName 으로 셋팅
        name.setText(userData.getName());

        // [iv/C]Text : userData 의 getTargetScore 으로 셋팅 / targetScore 의 variable type 는 int 이다. setText 는 String 으로 해야 하므로 +"" 으로 String 으로 변경한다.
        targetScore.setText(userData.getTargetScore() +"");

        // [iv/C]Text : userData 의 getSpeciality 로 셋팅
        setCheckedRadioButtonWithUserData(view, userData.getSpeciality());

        // [iv/C]Button : save 버튼을 disable 하고, R.color.colorWidgetDisable 로 비활성화 상태로 변경
        save.setBackgroundResource(R.color.colorWidgetDisable);
        save.setEnabled(false);
        DeveloperManager.displayLog("[F]_UserInputFragment", "[setWidget] save button 을 비활성화 상태로 변경하였습니다.");


        // [iv/C]Button : modify 버튼을 enable 하고, R.color.colorBackgroundPrimary 로 활성화 상태로 변경
        modify.setBackgroundResource(R.color.colorBackgroundPrimary);
        modify.setEnabled(true);
        DeveloperManager.displayLog("[F]_UserInputFragment", "[setWidget] modify button 을 활성화 상태로 변경하였습니다.");

    } // End of method [setWidget]


    /**
     * [method] userData 의 getSpeciality 값으로 RadioButton 을 찾아서 check 하기
     *
     * @param view onViewCreated 의 view 매개변수
     * @param specialityContent userData 의 getSpeciality 값
     *
     */
    private void setCheckedRadioButtonWithUserData(View view, String specialityContent) {

        // [lv/C]RadioButton : RadioGroup 에 속해있는 RadioButton widget mapping 하기
        RadioButton specialityThreeCushion = (RadioButton) view.findViewById(R.id.f_user_input_speciality_three_cushion);
        RadioButton specialityFourBall = (RadioButton) view.findViewById(R.id.f_user_input_speciality_four_ball);
        RadioButton specialityPocketBall = (RadioButton) view.findViewById(R.id.f_user_input_speciality_pocket_ball);

        // [check 1] : specialityContent 를 구분하여 그에 맞는 RadioButton 을 체크된 상태로 바꾸기
        switch (specialityContent) {
            case "3구":
                DeveloperManager.displayLog("[F] UserInput", "[setCheckedRadioButton] 3구가 선택되었습니다.");
                specialityThreeCushion.setChecked(true);
                break;
            case "4구":
                DeveloperManager.displayLog("[F] UserInput", "[setCheckedRadioButton] 4구가 선택되었습니다.");
                specialityFourBall.setChecked(true);
                break;
            case "포켓볼":
                DeveloperManager.displayLog("[F] UserInput", "[setCheckedRadioButton] 포켓볼이 선택되었습니다.");
                specialityPocketBall.setChecked(true);
                break;
        } // [check 1]
        
    } // End of method [setCheckedRadioButtonWithUserData]


    /**
     * [method] UserData 가 없을 때(=null) 모든 값을 입력 받아 user 테이블에 해당 userData 의 초기값으로 저장한다.
     *
     */
    



    /* method : targetScore 의 입력 값이 MIN_RANGE < targetScore < MAX_RANGE 사이인지 확인 */
    private boolean checkTargetScoreRange() {
        // targetScore : 입력 된 값이  0 < targetScore < 50  확인하기 위한
        int targetScoreContent = Integer.parseInt(targetScore.getText().toString());

        if ((MIN_RANGE < targetScoreContent) && (targetScoreContent < MAX_RANGE)) {
            return true;
        } else {
            return false;
        }
    }

    /* method : name, targetScore, speciality 에 모든 값이 입력 되었는지 확인 */
    private boolean checkInputAllData(String specialityContent) {
        // check : 매개변수로 입력 되었는지 확인
        if (!name.getText().toString().equals("") && !targetScore.getText().toString().equals("") && !specialityContent.equals("")) {
            // return : 모든 값이 입력 되었으면
            return true;
        } else {
            // return : 모든 값이 입력 안 되었으면
            return false;
        }
    }




    /* method : display, toast 메시지 출력 */
    private void toastHandler(View view, String content) {
        Toast myToast = Toast.makeText(view.getContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

}