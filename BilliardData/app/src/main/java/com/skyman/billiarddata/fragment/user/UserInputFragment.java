package com.skyman.billiarddata.fragment.user;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;
import com.skyman.billiarddata.table.user.database.UserDbManager2;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInputFragment extends Fragment implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "UserInputFragment";
    private final int MIN_RANGE = 0;            // 최소 범위
    private final int MAX_RANGE = 50;           // 최대 범위

    // constant
    private static final String USER_DATA = "userData";

    // instance variable
    private UserData userData;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable
    private EditText name;
    private EditText targetScore;
    private RadioGroup speciality;
    private RadioButton specialityThreeCushion;
    private RadioButton specialityFourBall;
    private RadioButton specialityPocketBall;
    private MaterialButton save;
    private MaterialButton modify;
    private MaterialButton delete;

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
    public static UserInputFragment newInstance(UserData userData) {

        Bundle args = new Bundle();

        UserInputFragment fragment = new UserInputFragment();
        args.putSerializable(USER_DATA, userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userData = (UserData) getArguments().getSerializable(USER_DATA);
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

        // appDbManager
        initAppDbManager();

        // widget : connect, init
        connectWidget();
        initWidget();


        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "======================================>>>>>>>>>>>>>>>>>>>>> User input fragment"
        );

        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "userData Object = " + userData
        );

        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "appDbManager Object = " + appDbManager
        );

    }

    @Override
    public void initAppDbManager() {

        appDbManager = ((UserManagerActivity) getActivity()).getAppDbManager();

    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : name mapping
        name = (EditText) getView().findViewById(R.id.F_userInput_name);

        // [iv/C]TextView : targetScore mapping
        targetScore = (EditText) getView().findViewById(R.id.F_userInput_targetScore);

        // [iv/c]RadioGroup : speciality mapping
        speciality = (RadioGroup) getView().findViewById(R.id.F_userInput_speciality);
        specialityThreeCushion = (RadioButton) getView().findViewById(R.id.F_userInput_speciality_threeCushion);
        specialityFourBall = (RadioButton) getView().findViewById(R.id.F_userInput_speciality_fourBall);
        specialityPocketBall = (RadioButton) getView().findViewById(R.id.F_userInput_speciality_pocketBall);

        // [iv/C]Button : save mapping
        save = (MaterialButton) getView().findViewById(R.id.F_userInput_button_save);

        // [iv/C]Button : modify mapping
        modify = (MaterialButton) getView().findViewById(R.id.F_userInput_button_modify);

        // [iv/C]Button : delete mapping
        delete = (MaterialButton) getView().findViewById(R.id.F_userInput_button_delete);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]Button : save button click listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfSaveButton : widget 의 입력 값으로 user 테이블에 데이터를 저장한다.
                setClickListenerOfSaveButton();

            }
        });

        // [iv/C]Button : modify button click listener
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfModifyButton : widget 의 입력 값으로 이미 저장된 user 데이터를 갱신한다.
                setClickListenerOfModifyButton();

            }
        });

        // Button : 'delete' on click listener
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfDeleteButton : user, friend, billiard 테이블의 데이터 중 현재 user 의 userId 값인 데이터를 삭제한다.
                setClickListenerOfDeleteButton();
            }
        });

        // userData 로 화면 표시
        initWidgetWithUserData();

    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

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
    private void initWidgetWithUserData() {
        final String METHOD_NAME = "[initWidgetWithUserData] ";

        if (userData != null) {

            // userData 의 내용을 화면에 표시하기

            // <1> name widget : userData 의 getName 으로 셋팅 / 변경하지 못 하도록 설정
            this.name.setText(this.userData.getName());
            this.name.setEnabled(false);

            // <2> targetScore widget : userData 의 getTargetScore 으로 셋팅 / targetScore 의 variable type 는 int 이다. setText 는 String 으로 해야 하므로 +"" 으로 String 으로 변경한다.
            this.targetScore.setText(this.userData.getTargetScore() + "");

            // <3> speciality widget : userData 의 getSpeciality 로 셋팅
            setCheckedRadioButtonWithUserData(this.userData.getSpeciality());

            // <4> save widget :  disable 하고, R.color.colorWidgetDisable 로 비활성화 상태로 변경
            this.save.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorWidgetDisable));
            this.save.setEnabled(false);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "save button 을 비활성화 상태로 변경하였습니다.");

            // <5> modify widget : enable 하고, R.color.colorBackgroundPrimary 로 활성화 상태로 변경
            this.modify.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBackgroundPrimary));
            this.modify.setEnabled(true);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "modify button 을 활성화 상태로 변경하였습니다.");

            // <6> delete widget : enable / R.color.colorBackgroundPrimary 로 상태 변경
            this.delete.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBackgroundPrimary));
            this.delete.setEnabled(true);

        }

    } // End of method [initWidgetWithUserData]


    /**
     * [method] [set] userData 의 getSpeciality 값으로 RadioButton 을 찾아서 check 하기
     *
     * @param specialityContent userData 의 getSpeciality 값
     */
    private void setCheckedRadioButtonWithUserData(String specialityContent) {
        final String METHOD_NAME = "[setCheckedRadioButtonWithUserData] ";

        // [check 1] : specialityContent 를 구분하여 그에 맞는 RadioButton 을 체크된 상태로 바꾸기
        switch (specialityContent) {
            case "3구":
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "3구가 선택되었습니다.");
                specialityThreeCushion.setChecked(true);
                break;
            case "4구":
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "4구가 선택되었습니다.");
                specialityFourBall.setChecked(true);
                break;
            case "포켓볼":
                DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "포켓볼이 선택되었습니다.");
                specialityPocketBall.setChecked(true);
                break;
        } // [check 1]

    } // End of method [setCheckedRadioButtonWithUserData]


    // =========================================================== button click listener ===========================================================

    /**
     * [method] [set] save button 의 click listener 을 설정
     */
    private void setClickListenerOfSaveButton() {
        final String METHOD_NAME = "[setClickListenerOfSaveButton] ";

        if (this.userData == null) {
            if (checkInputOfAllData()) {
                if (checkRangeOfTargetScore()) {

                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.F_userInput_dialog_saveCheck_title)
                            .setMessage(R.string.F_userInput_dialog_saveCheck_message)
                            .setPositiveButton(
                                    R.string.F_userInput_dialog_saveCheck_positive,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                            RadioButton selectedSpeciality = (RadioButton) getView().findViewById(speciality.getCheckedRadioButtonId());

                                            // widget : 입력된 내용 가져오기
                                            String contentOfName = removeWhitespaceOfName(name.getText().toString());
                                            int contentOfTargetScore = Integer.parseInt(targetScore.getText().toString());
                                            String contentOfSpeciality = selectedSpeciality.getText().toString();

                                            appDbManager.requestUserQuery(
                                                    new AppDbManager.UserQueryRequestListener() {
                                                        @Override
                                                        public void requestQuery(UserDbManager2 userDbManager2) {

                                                            try {

                                                                long rowNumber = userDbManager2.saveContent(
                                                                        contentOfName,
                                                                        contentOfTargetScore,
                                                                        contentOfSpeciality,
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        0
                                                                );

                                                                // 데이터베이스에
                                                                if (rowNumber > 0) {

                                                                    // Success (= 저장 성공!)

                                                                    // 기존의 userData 는 null 이고
                                                                    // 위에서 저장된 userData 를 가져온다.
                                                                    userData = userDbManager2.loadContent(rowNumber);

                                                                    // userData 를 다른 Fragment 에 넘기기 위해서
                                                                    // Bundle 에 데이터를 넣는다.
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putSerializable(UserData.class.getSimpleName(), userData);

                                                                    // 다른 Fragment 에 알리기
                                                                    getParentFragmentManager().setFragmentResult("save/UserInfo", bundle);
                                                                    getParentFragmentManager().setFragmentResult("save/UserFriend", bundle);

                                                                    // 지금 Fragment( UserInputFragment ) 에
                                                                    // 위에서 가져온 userData 의 내용을
                                                                    // 화면에 표시하기
                                                                    initWidgetWithUserData();

                                                                    // <사용자 알림>
                                                                    Toast.makeText(
                                                                            getContext(),
                                                                            R.string.F_userInput_noticeUser_save_success,
                                                                            Toast.LENGTH_SHORT
                                                                    ).show();


                                                                }

                                                            } catch (Exception e) {

                                                                // Fail (=저장 실패)

                                                                e.printStackTrace();

                                                                // widget 의 내용 지우기
                                                                name.setText("");
                                                                targetScore.setText("");
                                                                specialityThreeCushion.setChecked(true);

                                                                // <사용자 알림>
                                                                Toast.makeText(
                                                                        getContext(),
                                                                        R.string.F_userInput_noticeUser_save_fail,
                                                                        Toast.LENGTH_SHORT
                                                                ).show();
                                                            }

                                                        }
                                                    }
                                            );

                                        }
                                    }
                            )
                            .setNegativeButton(
                                    R.string.F_userInput_dialog_saveCheck_negative,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }
                            )
                            .show();

                } else {

                    // <사용자 알림>
                    Toast.makeText(
                            getContext(),
                            R.string.F_userInput_noticeUser_checkRangeTargetScore,
                            Toast.LENGTH_SHORT
                    ).show();

                } // [check 3]

            } else {

                // <사용자 알림>
                Toast.makeText(
                        getContext(),
                        R.string.F_userInput_noticeUser_checkInputAllData,
                        Toast.LENGTH_SHORT
                ).show();

            } // [check 2]

        }

    } // End of method [setClickListenerOfSaveButton]


    /**
     * [method] [set] modify button 의 click listener 을 설정
     */
    private void setClickListenerOfModifyButton() {
        final String METHOD_NAME = "[setClickListenerOfModifyButton] ";

        if (this.userData != null) {
            if (checkInputOfAllData()) {
                if (checkRangeOfTargetScore()) {

                    // <사용자 확인>
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.F_userInput_dialog_modifyCheck_title)
                            .setMessage(R.string.F_userInput_dialog_modifyCheck_message)
                            .setPositiveButton(
                                    R.string.F_userInput_dialog_modifyCheck_positive,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            try {
                                                // [lv/C]RadioButton : speciality 로 선택된 Button 의 id 값으로 RadioButton 을 mapping 한다.
                                                RadioButton selectedSpeciality = (RadioButton) getView().findViewById(speciality.getCheckedRadioButtonId());

                                                // 수정된 데이터 widget 에서 가져오기
                                                int contentOfTargetScore = Integer.parseInt(targetScore.getText().toString());
                                                String contentOfSpeciality = selectedSpeciality.getText().toString();

                                                // user : 수정된 userData 를 엡데이트하기
                                                appDbManager.requestUserQuery(
                                                        new AppDbManager.UserQueryRequestListener() {
                                                            @Override
                                                            public void requestQuery(UserDbManager2 userDbManager2) {

                                                                int numberOfUpdatedRows = userDbManager2.updateContentById(
                                                                        userData.getId(),
                                                                        contentOfTargetScore,
                                                                        contentOfSpeciality
                                                                );

                                                                DeveloperLog.printLog(CLASS_LOG_SWITCH,
                                                                        CLASS_NAME,
                                                                        "Database 에 저장된 데이터 가져오기"
                                                                );
                                                                DeveloperLog.printLogUserData(CLASS_LOG_SWITCH,
                                                                        CLASS_NAME,
                                                                        userDbManager2.loadContent(userData.getId())
                                                                );

                                                                if (numberOfUpdatedRows == 1) {

                                                                    // Success (=업데이트 성공!)

                                                                    // 업데이트 완료되었으므로 userData 에 반영하기
                                                                    // 기존의 userData 에 반영해야 하므로 setter 메소드를 이용하여 입력한다.
                                                                    userData.setTargetScore(contentOfTargetScore);
                                                                    userData.setSpeciality(contentOfSpeciality);

                                                                    // 다른 Fragment 에 알리기
                                                                    getParentFragmentManager().setFragmentResult("modify/UserInfo", null);

                                                                    // 현재 Fragment( UserInputFragment ) 에
                                                                    // 변경된 userData 의 데이터를 화면에 표시하기
                                                                    initWidgetWithUserData();

                                                                    // <사용자 알림>
                                                                    Toast.makeText(
                                                                            getContext(),
                                                                            R.string.F_userInput_noticeUser_modify_success,
                                                                            Toast.LENGTH_SHORT
                                                                    ).show();

                                                                }
                                                            }
                                                        }
                                                );

                                            } catch (Exception e) {

                                                // Fail (= 업데이트 실패!)

                                                // 실패하였으므로 기존의 userData 의 내용을 화면에 다시 표시한다.
                                                initWidgetWithUserData();

                                                // <사용자 알림>
                                                Toast.makeText(
                                                        getContext(),
                                                        R.string.F_userInput_noticeUser_modify_fail,
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                            }


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

                    // <사용자 알림>
                    Toast.makeText(
                            getContext(),
                            R.string.F_userInput_noticeUser_checkRangeTargetScore,
                            Toast.LENGTH_SHORT
                    ).show();

                } // [check 3]

            } else {

                // <사용자 알림>
                Toast.makeText(
                        getContext(),
                        R.string.F_userInput_noticeUser_checkInputAllData,
                        Toast.LENGTH_SHORT
                ).show();

            } // [check 2]


        }

    } // End of method [setClickListenerOfModifyButton]


    /**
     * [method] [set] delete button 의 click listener 을 설정
     */
    private void setClickListenerOfDeleteButton() {
        final String METHOD_NAME = "[setClickListenerOfDeleteButton] ";

        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                CLASS_NAME,
                "==================>?>> click"
        );

        // [check 1] : userData 가 있다.
        if (this.userData != null) {
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "데이터가 있어서 삭제 시작");

            // <사용자 확인>
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.F_userInput_dialog_deleteCheck_title)
                    .setMessage(R.string.F_userInput_dialog_deleteCheck_message)
                    .setPositiveButton(
                            R.string.F_userInput_dialog_deleteCheck_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {

                                        // UserDbManager : 테이블의 모든 내용 삭제
                                        // [lv/i]resultDeleteOfUser : 해당 userId 의 user 데이터를 삭제한 결과
                                        appDbManager.requestUserQuery(
                                                new AppDbManager.UserQueryRequestListener() {
                                                    @Override
                                                    public void requestQuery(UserDbManager2 userDbManager2) {

                                                        userDbManager2.deleteContentById(userData.getId());
                                                    }
                                                }
                                        );

                                        // FriendDbManager : 테이블의 모든 내용 삭제
                                        // [lv/i]resultDeleteOfUser : 해당 userId 의 friend 데이터를 모두 삭제한 결과
                                        appDbManager.requestFriendQuery(
                                                new AppDbManager.FriendQueryRequestListener() {
                                                    @Override
                                                    public void requestQuery(FriendDbManager2 friendDbManager2) {
                                                        friendDbManager2.deleteContentByUserId(userData.getId());
                                                    }
                                                }
                                        );

                                        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();
                                        appDbManager.requestPlayerQuery(
                                                new AppDbManager.PlayerQueryRequestListener() {
                                                    @Override
                                                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

                                                        playerDataArrayList.addAll(
                                                                playerDbManager2.loadAllContentByPlayerIdAndPlayerName(
                                                                        userData.getId(),
                                                                        userData.getName()
                                                                )
                                                        );

                                                        for (int index = 0; index < playerDataArrayList.size(); index++) {

                                                            playerDbManager2.deleteContentByBilliardCount(
                                                                    playerDataArrayList.get(index).getBilliardCount()
                                                            );

                                                        }

                                                    }
                                                }
                                        );

                                        appDbManager.requestBilliardQuery(
                                                new AppDbManager.BilliardQueryRequestListener() {
                                                    @Override
                                                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                                                        for (int index = 0; index < playerDataArrayList.size(); index++) {
                                                            billiardDbManager2.deleteContentByCount(
                                                                    playerDataArrayList.get(index).getBilliardCount()
                                                            );
                                                        }
                                                    }
                                                }
                                        );

                                        // 기존의 userData 가 삭제되었으므로
                                        // userData 를 null 로
                                        userData = null;

                                        // 다른 Fragment 에 알려주기
                                        getParentFragmentManager().setFragmentResult("delete/UserInfo", null);
                                        getParentFragmentManager().setFragmentResult("delete/UserFriend", null);

                                        // widget : 내용 지우기
                                        name.setText("");
                                        name.setEnabled(true);
                                        targetScore.setText("");
                                        specialityThreeCushion.setChecked(true);
                                        save.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBackgroundPrimary));
                                        save.setEnabled(true);
                                        modify.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorWidgetDisable));
                                        modify.setEnabled(false);
                                        delete.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorWidgetDisable));
                                        delete.setEnabled(false);

                                        // <사용자 알림>
                                        Toast.makeText(
                                                getContext(),
                                                R.string.F_userInput_noticeUser_delete_success,
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        DeveloperLog.printLog(CLASS_LOG_SWITCH,
                                                CLASS_NAME,
                                                "======================>>>> 데이터 확인"
                                        );


                                        appDbManager.requestQuery(
                                                new AppDbManager.QueryRequestListener() {
                                                    @Override
                                                    public void requestUserQuery(UserDbManager2 userDbManager2) {
                                                        DeveloperLog.printLogUserData(
                                                                CLASS_LOG_SWITCH,
                                                                CLASS_NAME,
                                                                userData
                                                        );
                                                    }

                                                    @Override
                                                    public void requestFriendQuery(FriendDbManager2 friendDbManager2) {
                                                        DeveloperLog.printLogFriendData(
                                                                CLASS_LOG_SWITCH,
                                                                CLASS_NAME,
                                                                friendDbManager2.loadAllContentByUserId(1)
                                                        );
                                                    }

                                                    @Override
                                                    public void requestBilliardQuery(BilliardDbManager2 billiardDbManager2) {
                                                        DeveloperLog.printLogBilliardData(
                                                                CLASS_LOG_SWITCH,
                                                                CLASS_NAME,
                                                                billiardDbManager2.loadAllContent()
                                                        );
                                                    }

                                                    @Override
                                                    public void requestPlayerQuery(PlayerDbManager2 playerDbManager2) {
                                                        DeveloperLog.printLogPlayerData(
                                                                CLASS_LOG_SWITCH,
                                                                CLASS_NAME,
                                                                playerDbManager2.loadAllContent()
                                                        );
                                                    }
                                                }
                                        );

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                        // <사용자 알림>
                                        Toast.makeText(
                                                getContext(),
                                                R.string.F_userInput_noticeUser_delete_fail,
                                                Toast.LENGTH_SHORT
                                        ).show();

                                    }

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
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "데이터가 없어서 삭제 안함");
        } // [check 1]

    } // End of method [setClickListenerOfDeleteButton]


    // =========================================================== ETC ===========================================================

    /**
     * [method] [check] name, targetScore, speciality 의 값이 모두 입력되었는지 검사하여 모두 입력 하였을 경우 true 를 반환한다.
     *
     * @return 모든 값이 입력되고 선택되었을 때 true 를 반환한다.
     */
    private boolean checkInputOfAllData() {
        final String METHOD_NAME = "[checkInputOfAllData] ";

        // [lv/C]RadioButton : selectedSpeciality mapping
        RadioButton selectedSpeciality = (RadioButton) getView().findViewById(this.speciality.getCheckedRadioButtonId());

        // [check 1] : name, targetScore, specialityContent 가 모두 입력되었다.
        if (!this.name.getText().toString().equals("")
                && !this.targetScore.getText().toString().equals("")
                && !selectedSpeciality.getText().toString().equals("")) {

            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "모든 값이 입력되었습니다.");
            return true;

        } else {

            // return : 모든 값이 입력 안 되었으면
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "모든 값이 입력되지 않았습니다. 모두 입력해야 합니다.");
            return false;

        } // [check 1]

    } // End of method [checkInputOfAllData]


    /**
     * [method] [check] targetScore 의 입력 값이 MIN_RANGE < targetScore < MAX_RANGE 사이인지 확인하여
     * 이 범위 안이면 true 를 반환한다.
     *
     * @return targetScore 가 범위안에 있으면 true 이다.
     */
    private boolean checkRangeOfTargetScore() {
        final String METHOD_NAME = "[checkRangeOfTargetScore] ";
        // targetScore : 입력 된 값이  0 < targetScore < 50  확인하기 위한

        int targetScoreContent = Integer.parseInt(this.targetScore.getText().toString());

        // [check 1] : MIN_RANGE < targetScore < MAX_RANGE 안에 있다.
        if ((MIN_RANGE < targetScoreContent) && (targetScoreContent < MAX_RANGE)) {

            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "targetScore 가 범위 안에 있습니다.");
            return true;

        } else {
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "targetScore 가 범위 안에 없습니다.");
            return false;
        } // [check 1]

    } // End of method [checkRangeOfTargetScore]


    /**
     * [method] user 의 name 문자열의 공백을 제거
     */
    private String removeWhitespaceOfName(String name) {

//        return name.replace(" ", "");
        return name.trim();
    } // End of method [removeWhitespaceOfName]


}