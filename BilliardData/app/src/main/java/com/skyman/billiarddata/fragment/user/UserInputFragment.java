package com.skyman.billiarddata.fragment.user;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInputFragment extends Fragment {

    // constant
    private final String CLASS_NAME_LOG = "[F]_UserInputFragment";
    private final int MIN_RANGE = 0;            // 최소 범위
    private final int MAX_RANGE = 50;           // 최대 범위

    // instance variable
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;
    private BilliardDbManager billiardDbManager;
    private PlayerDbManager playerDbManager;
    private UserData userData;

    // instance variable
    private EditText name;
    private EditText targetScore;
    private RadioGroup speciality;
    private Button save;
    private Button modify;
    private Button delete;

    // constructor
    public UserInputFragment(UserDbManager userDbManager, FriendDbManager friendDbManager, BilliardDbManager billiardDbManager, PlayerDbManager playerDbManager, UserData userData) {
        this.userDbManager = userDbManager;
        this.friendDbManager = friendDbManager;
        this.billiardDbManager = billiardDbManager;
        this.playerDbManager = playerDbManager;
        this.userData = userData;
    }

    // constructor
    public UserInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInputData.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInputFragment newInstance() {
        UserInputFragment fragment = new UserInputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_input, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final String METHOD_NAME = "[onViewCreated] ";
        super.onViewCreated(view, savedInstanceState);

        // [method]mappingOfWidget : fragment_user_input.xml layout 의 widget mapping
        mappingOfWidget(view);

        // [check 1] : user 의 데이터가 있다.
        if (userData != null) {

            // [method]setWidgetWithUserData : userData 로 widget 초기값 셋팅
            setWidgetWithUserData(view);

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "나의 userData 데이터가 없습니다.");
        } // [check 1]

        // [iv/C]Button : save button click listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfSaveButton : widget 의 입력 값으로 user 테이블에 데이터를 저장한다.
                setClickListenerOfSaveButton(view);

            }
        });

        // [iv/C]Button : modify button click listener
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfModifyButton : widget 의 입력 값으로 이미 저장된 user 데이터를 갱신한다.
                setClickListenerOfModifyButton(view);

            }
        });

        // Button : 'delete' on click listener
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfDeleteButton : user, friend, billiard 테이블의 데이터 중 현재 user 의 userId 값인 데이터를 삭제한다.
                setClickListenerOfDeleteButton(view);
            }
        });

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    /**
     * [method] [set] activity_billiard_input.xml 의 widget 을 mapping(뜻: 하나의 값을 다른 값으로 대응시키는 것을 말한다.)
     */
    private void mappingOfWidget(View view) {

        // [iv/C]TextView : name mapping
        this.name = (EditText) view.findViewById(R.id.F_userInput_name);

        // [iv/C]TextView : targetScore mapping
        this.targetScore = (EditText) view.findViewById(R.id.F_userInput_targetScore);

        // [iv/c]RadioGroup : speciality mapping
        this.speciality = (RadioGroup) view.findViewById(R.id.F_userInput_speciality);

        // [iv/C]Button : save mapping
        this.save = (Button) view.findViewById(R.id.F_userInput_button_save);

        // [iv/C]Button : modify mapping
        this.modify = (Button) view.findViewById(R.id.F_userInput_button_modify);

        // [iv/C]Button : delete mapping
        this.delete = (Button) view.findViewById(R.id.F_userInput_button_delete);

    } // End of method [mappingOfWidget]


    /**
     * [method] [set] UserData 가 있어서 화면 재설정한다. 입력 값들은 userData 의 값으로, 버튼들은 비활성화 활성화
     *
     * <p>
     * UserData 의 getName 을 name EditText 에 설정한다.
     * UserData 의 getTargetScore 을 targetScore EditText 에 설정한다.
     * UserData 의 getSpeciality 을 speciality Spinner 의 선택 값으로 설정한다.
     * save Button 은 새로운 UserData 를 만들지 못하게 한다(버튼 비활성화). disable 로 만들고, R.color.colorWidgetDisable 색으로 변경한다.
     * modify Button 은 UserData 의 값을 변경할 수 있도록 한다(버튼 활성화). enable 로 만들고, R.color.colorBackgroundPrimary 색으로 변경한다.
     *
     * <p>
     * 버튼 활성화 색 : R.color.colorBackgroundPrimary
     * 버튼 비활성화 색 : R.color.colorWidgetDisable
     */
    private void setWidgetWithUserData(View view) {

        final String METHOD_NAME = "[setWidgetWithUserData] ";

        // [iv/C]Text : userData 의 getName 으로 셋팅 / 변경하지 못 하도록 설정
        this.name.setText(this.userData.getName());
        this.name.setEnabled(false);

        // [iv/C]Text : userData 의 getTargetScore 으로 셋팅 / targetScore 의 variable type 는 int 이다. setText 는 String 으로 해야 하므로 +"" 으로 String 으로 변경한다.
        this.targetScore.setText(this.userData.getTargetScore() + "");

        // [iv/C]Text : userData 의 getSpeciality 로 셋팅
        setCheckedRadioButtonWithUserData(view, this.userData.getSpeciality());

        // [iv/C]Button : save 버튼을 disable 하고, R.color.colorWidgetDisable 로 비활성화 상태로 변경
        this.save.setBackgroundResource(R.color.colorWidgetDisable);
        this.save.setEnabled(false);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "save button 을 비활성화 상태로 변경하였습니다.");


        // [iv/C]Button : modify 버튼을 enable 하고, R.color.colorBackgroundPrimary 로 활성화 상태로 변경
        this.modify.setBackgroundResource(R.color.colorBackgroundPrimary);
        this.modify.setEnabled(true);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "modify button 을 활성화 상태로 변경하였습니다.");

        // delete 버튼 : enable / R.color.colorBackgroundPrimary 로 상태 변경
        this.delete.setBackgroundResource(R.color.colorBackgroundPrimary);
        this.delete.setEnabled(true);

    } // End of method [setWidget]


    /**
     * [method] [set] userData 의 getSpeciality 값으로 RadioButton 을 찾아서 check 하기
     *
     * @param view              onViewCreated 의 view 매개변수
     * @param specialityContent userData 의 getSpeciality 값
     */
    private void setCheckedRadioButtonWithUserData(View view, String specialityContent) {

        final String METHOD_NAME = "[setCheckedRadioButtonWithUserData] ";

        // [lv/C]RadioButton : RadioGroup 에 속해있는 RadioButton widget mapping 하기
        RadioButton specialityThreeCushion = (RadioButton) view.findViewById(R.id.F_userInput_speciality_threeCushion);
        RadioButton specialityFourBall = (RadioButton) view.findViewById(R.id.F_userInput_speciality_fourBall);
        RadioButton specialityPocketBall = (RadioButton) view.findViewById(R.id.F_userInput_speciality_pocketBall);

        // [check 1] : specialityContent 를 구분하여 그에 맞는 RadioButton 을 체크된 상태로 바꾸기
        switch (specialityContent) {
            case "3구":
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "3구가 선택되었습니다.");
                specialityThreeCushion.setChecked(true);
                break;
            case "4구":
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "4구가 선택되었습니다.");
                specialityFourBall.setChecked(true);
                break;
            case "포켓볼":
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "포켓볼이 선택되었습니다.");
                specialityPocketBall.setChecked(true);
                break;
        } // [check 1]

    } // End of method [setCheckedRadioButtonWithUserData]


    /**
     * [method] [set] save button 의 click listener 을 설정
     */
    private void setClickListenerOfSaveButton(View view) {

        final String METHOD_NAME = "[setClickListenerOfSaveButton] ";

        // [check 1] : userData 가 없다.
        if (this.userData == null) {

            // [lv/C]RadioButton : selectedSpeciality mapping
            RadioButton selectedSpeciality = (RadioButton) view.findViewById(this.speciality.getCheckedRadioButtonId());

            // [check 2] : name, targetScore, speciality 가 모두 입력되었다.
            if (checkInputAllData(selectedSpeciality.getText().toString())) {

                // [check 3] :  < targetScore < 50 이다.
                if (checkTargetScoreRange(view)) {

                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "공백 제거 완료 = {" + removeWhitespaceOfName(name.getText().toString()) + "}");

                    // [lv/l]resultSave : userDbManager 를 통해 첫 나의 데이터(userData)를 입력한다. / 그 결과값으로 1 를 얻는다.
                    long resultSave = this.userDbManager.saveContent(
                            this.name.getText().toString(),                                          // 1. name
                            Integer.parseInt(this.targetScore.getText().toString()),                 // 2. target score
                            selectedSpeciality.getText().toString(),                            // 3. speciality
                            0,                                                   // 4. game record win
                            0,                                                   // 5. game record loss
                            0,                                             // 6. recent game billiard count
                            0,                                                      // 7. total play time
                            0                                                          // 8. total cost
                    );

                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "저장이 완료 되었습니다. 해당 유저의 id 는 " + resultSave + " 입니다.");
//                    toastHandler(view, "당신의 아이디는 <" + resultSave + "> 입니다.");

                    // [iv/C]UserData : 위에서 갱신한 데이터를 가져오기
                    this.userData = this.userDbManager.loadContent(resultSave);
                    DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);

                    // [method]moveUserManagerActivity : UserManagerActivity 로 이동
                    moveUserManagerActivity(view);

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "0< targetScore <50 인 값을 입력해주세요.");
                } // [check 3]

            } else {
                // [method]toastHandler : 모든 값을 입력해달라고 사용자에게 요구한다.
                toastHandler(view, "모든 값을 입력해주세요.");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "name, targetScore, speciality 의 모든 값을 입력해주세요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 있으므로 저장할 필요가 없습니다.");
        } // [check 1]

    } // End of method [setClickListenerOfSaveButton]


    /**
     * [method] [set] modify button 의 click listener 을 설정
     */
    private void setClickListenerOfModifyButton(View view) {

        final String METHOD_NAME = "[setClickListenerOfModifyButton] ";

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            // <사용자 확인>
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.F_userInput_dialog_modifyCheck_title)
                    .setMessage(R.string.F_userInput_dialog_modifyCheck_message)
                    .setPositiveButton(
                            R.string.F_userInput_dialog_modifyCheck_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // [lv/C]RadioButton : speciality 로 선택된 Button 의 id 값으로 RadioButton 을 mapping 한다.
                                    RadioButton selectedSpeciality = (RadioButton) view.findViewById(speciality.getCheckedRadioButtonId());

                                    // [lv/i]resultUpdate :  변경할 값을 updateContent method 를 이용하여 갱신하기
                                    int resultUpdate = userDbManager.updateContent(
                                            userData.getId(),
                                            Integer.parseInt(targetScore.getText().toString()),
                                            selectedSpeciality.getText().toString());

                                    // [check 2] : result 값으로 update 잘 되었는지 판다
                                    if (resultUpdate == 1) {
                                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "1번재 내용이 수정되었습니다.");
                                    } else {
                                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "1번째 내용을 수정하는데 실패하였습니다.");
                                    } // [check 2]

                                    // [iv/C]UserData : 위에서 갱신된 userData 를 user 메니저를 통해서 가져오기
                                    userData = userDbManager.loadContent(userData.getId());

                                    // [method]moveUserManagerActivity : UserManagerActivity 로 이동
                                    moveUserManagerActivity(view);
                                }
                            }
                    )
                    .setNegativeButton(
                            R.string.F_userInput_dialog_modifyCheck_negative,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .show();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없으므로 수정할 필요가 없습니다. 첫 입력부터 해주세요.");
        } // [check 1]

    } // End of method [setClickListenerOfModifyButton]


    /**
     * [method] [set] delete button 의 click listener 을 설정
     */
    private void setClickListenerOfDeleteButton(View view) {

        final String METHOD_NAME = "[setClickListenerOfDeleteButton] ";

        // [check 1] : userData 가 있다.
        if (this.userData != null) {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "데이터가 있어서 삭제 시작");

            // <사용자 확인>
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.F_userInput_dialog_deleteCheck_title)
                    .setMessage(R.string.F_userInput_dialog_deleteCheck_message)
                    .setPositiveButton(
                            R.string.F_userInput_dialog_deleteCheck_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // UserDbManager : 테이블의 모든 내용 삭제
                                    // [lv/i]resultDeleteOfUser : 해당 userId 의 user 데이터를 삭제한 결과
                                    int resultDeleteOfUser = userDbManager.deleteContent(userData.getId());

                                    // FriendDbManager : 테이블의 모든 내용 삭제
                                    // [lv/i]resultDeleteOfUser : 해당 userId 의 friend 데이터를 모두 삭제한 결과
                                    int resultDeleteOfFriend = friendDbManager.deleteContentByUserId(userData.getId());

                                    // [lv/C]ArrayList<playerData> : userData 의 id 와 name 으로 참가한 게임 목록 가져오기
                                    ArrayList<PlayerData> playerDataArrayList = playerDbManager.loadAllContentByPlayerIdAndPlayerName(userData.getId(), userData.getName());

                                    // [cycle 1] : 게임에 참가한 player 수 만큼
                                    for (int index = 0; index < playerDataArrayList.size(); index++) {

                                        billiardDbManager.deleteContentByCount(playerDataArrayList.get(index).getBilliardCount());

                                        playerDbManager.deleteContentByBilliardCount(playerDataArrayList.get(index).getBilliardCount());

                                    } // [cycle 1]

                                    // [iv/C]UserData : user 데이터를 삭제하여 null 값으로 변경
                                    userData = null;

                                    // [method]moveUserManagerActivity : UserManagerActivity 로 이동
                                    moveUserManagerActivity(view);
                                }
                            }
                    )
                    .setNegativeButton(
                            R.string.F_userInput_dialog_deleteCheck_negative,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }
                    )
                    .show();


        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "데이터가 없어서 삭제 안함");
        } // [check 1]

    } // End of method [setClickListenerOfDeleteButton]


    /**
     * [method] [check] name, targetScore, speciality 의 값이 모두 입력되었는지 검사하여 모두 입력 하였을 경우 true 를 반환한다.
     *
     * @return 모든 값이 입력되고 선택되었을 때 true 를 반환한다.
     */
    private boolean checkInputAllData(String speciality) {

        final String METHOD_NAME = "[checkInputAllData] ";

        // [check 1] : name, targetScore, specialityContent 가 모두 입력되었다.
        if (!this.name.getText().toString().equals("")
                && !this.targetScore.getText().toString().equals("")
                && !speciality.equals("")) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 값이 입력되었습니다.");
            return true;

        } else {

            // return : 모든 값이 입력 안 되었으면
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 값이 입력되지 않았습니다. 모두 입력해야 합니다.");
            return false;

        } // [check 1]

    } // End of method [checkInputAllData]


    /**
     * [method] [check] targetScore 의 입력 값이 MIN_RANGE < targetScore < MAX_RANGE 사이인지 확인하여
     * 이 범위 안이면 true 를 반환한다.
     *
     * @return targetScore 가 범위안에 있으면 true 이다.
     */
    private boolean checkTargetScoreRange(View view) {
        // targetScore : 입력 된 값이  0 < targetScore < 50  확인하기 위한

        final String METHOD_NAME = "[checkTargetScoreRange] ";

        int targetScoreContent = Integer.parseInt(this.targetScore.getText().toString());

        // [check 1] : MIN_RANGE < targetScore < MAX_RANGE 안에 있다.
        if ((MIN_RANGE < targetScoreContent) && (targetScoreContent < MAX_RANGE)) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "targetScore 가 범위 안에 있습니다.");
            return true;

        } else {

            toastHandler(view, MIN_RANGE + " < 수지 < " + MAX_RANGE + " 의 값을 입력해주세요.");
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "targetScore 가 범위 밖의 값입니다. ");
            return false;

        } // [check 1]

    } // End of method [checkTargetScoreRange]


    /**
     * [method] user 의 name 문자열의 공백을 제거
     */
    private String removeWhitespaceOfName(String name) {

//        return name.replace(" ", "");
        return name.trim();
    } // End of method [removeWhitespaceOfName]


    /**
     * [method] [move] UserManagerActivity 로 이동
     */
    private void moveUserManagerActivity(View view) {

        // [lv/C]Intent : UserManagerActivity 로 이동하기 위한 Intent 를 생성
        Intent intent = new Intent(view.getContext(), UserManagerActivity.class);

        // [lv/C]Intent : SessionManager 를 통해 intent 에 'userData' 담는다.
        SessionManager.setUserDataFromIntent(intent, this.userData);

        // [method]getActivity : 해당 activity 를 stack 에서 삭제하기
        getActivity().finish();

        // [method]startActivity : 위 의 intent 를 이용하여 activity 이동하기
        startActivity(intent);

    } // End of method [moveUserManagerActivity]


    /**
     * [method] 해당 문자열을 toast 로 보여준다.
     */
    private void toastHandler(View view, String content) {

        // [lv/C]Toast : toast 객체 생성
        Toast myToast = Toast.makeText(view.getContext(), content, Toast.LENGTH_SHORT);

        // [lv/C]Toast : 위에서 생성한 객체를 보여준다.
        myToast.show();

    } // End of method [toastHandler]

}