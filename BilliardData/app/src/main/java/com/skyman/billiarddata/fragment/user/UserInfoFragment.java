package com.skyman.billiarddata.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.etc.DataFormatUtil;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "UserInfoFragment";

    // constant
    private static final String USER_DATA = "userData";

    // instance variable
    private UserData userData;

    // instance variable
    private BilliardData billiardData;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable
    private TextView id;
    private TextView name;
    private TextView targetScore;
    private TextView speciality;
    private TextView gameRecord;
    private TextView totalPlayTime;
    private TextView totalCost;
    private TextView recentPlayDate;

    // constructor
    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(UserData userData) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final String METHOD_NAME = "[onViewCreated] ";
        super.onViewCreated(view, savedInstanceState);

        // appDbManager
        initAppDbManager();

        // widget : connect -> init
        connectWidget();
        initWidget();

        // user input fragment 의
        // delete 한 결과를 받는다.
        getParentFragmentManager().setFragmentResultListener(
                "save/UserInfo",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (requestKey.equals("save/UserInfo")) {

                            userData = (UserData) result.get(UserData.class.getSimpleName());
                            initWidgetWithUserDataAndBilliardData();
                        }
                    }
                }
        );

        // user input fragment 의
        // userData 를 modify 한 결과를 받는다.
        getParentFragmentManager().setFragmentResultListener(
                "modify/UserInfo",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (requestKey.equals("modify/UserInfo")) {
                            initWidgetWithUserDataAndBilliardData();
                        }

                    }
                }
        );

        // user input fragment 의
        // delete 한 결과를 받는다.
        getParentFragmentManager().setFragmentResultListener(
                "delete/UserInfo",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (requestKey.equals("delete/UserInfo")) {

                            // userData 내용이 삭제되었으므로 userData 도 null 로 변경
                            // (주의) userInput 에서 userData 에 null 로 변경하였더라도
                            //        여기의 userData 는 여전히 UserManagerActivity 의 userData 를 가리키고 있으므로
                            //        만약 이 Fragment 에서 userData 로 하는 작업이 있다면 UserManagerActivity 의 userData 를 사용하면 안되므로
                            //        UserInfoFragment 의 userData 는 null 로 바꾸어 주어야 한다.
                            userData = null;

                            // 화면 내용 초기화
                            id.setText("");
                            name.setText("");
                            targetScore.setText("");
                            speciality.setText("");
                            gameRecord.setText("");
                            totalPlayTime.setText("");
                            totalCost.setText("");
                            recentPlayDate.setText("");

                        }
                    }
                }
        );


    } // End of method [onViewCreated]


    @Override
    public void initAppDbManager() {

        appDbManager = ((UserManagerActivity) getActivity()).getAppDbManager();

    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : id mapping
        this.id = (TextView) getView().findViewById(R.id.F_userInfo_id);

        // [iv/C]TextView : name mapping
        this.name = (TextView) getView().findViewById(R.id.F_userInfo_name);

        // [iv/C]TextView : targetScore mapping
        this.targetScore = (TextView) getView().findViewById(R.id.F_userInfo_targetScore);

        // [iv/C]TextView : speciality mapping
        this.speciality = (TextView) getView().findViewById(R.id.F_userInfo_speciality);

        // [iv/C]TextView : gameRecord mapping
        this.gameRecord = (TextView) getView().findViewById(R.id.F_userInfo_gameRecord);

        // [iv/C]TextView : totalPlayTime mapping
        this.totalPlayTime = (TextView) getView().findViewById(R.id.F_userInfo_totalPlayTime);

        // [iv/C]TextView : totalCost mapping
        this.totalCost = (TextView) getView().findViewById(R.id.F_userInfo_totalCost);

        // [iv/C]TextView : recentPlayDate mapping
        this.recentPlayDate = (TextView) getView().findViewById(R.id.F_userInfo_recentPlayDate);

    }

    @Override
    public void initWidget() {
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "[initWidget() 수행 중]");

        // [check 1] : userData 가 있다.
        if (this.userData != null) {

            appDbManager.requestBilliardQuery(
                    new AppDbManager.BilliardQueryRequestListener() {
                        @Override
                        public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                            // 가장 최근에 한 게임에 대한 정보를 가져오기 위해서
                            // userData 의 recentGameBilliardCount 에 해당하는
                            // billiardData 가져오기
                            billiardData = billiardDbManager2.loadContentByCount(userData.getRecentGameBilliardCount());

                            // userData 와 billiardData 로 widget 내용 채우기
                            initWidgetWithUserDataAndBilliardData();

                        }
                    }
            );

        } else {
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "userData 가 null 입니다. : 초기 셋팅 필요 없음.");
        } // [check 1]

    }


    /**
     * [method] userData 의 값들을 mapping 된 widget 에 출력한다.
     */
    private void initWidgetWithUserDataAndBilliardData() {

        // [iv/C]TextView : id 를 userData 의 getId 으로
        id.setText(userData.getId() + "");

        // [iv/C]TextView : name 를 userData 의 getName 으로
        name.setText(userData.getName());

        // [iv/C]TextView : targetScore 를 userData 의 getTargetScore 으로
        targetScore.setText(userData.getTargetScore() + "");

        // [iv/C]TextView : speciality 를 userData 의 getSpeciality 으로
        speciality.setText(userData.getSpeciality());

        // [iv/C]TextView : gameRecord 를 userData 의 getGameRecordWin 와 getGameRecordLoss 를 특정 문자열 형태로 바꾼 값으로
        gameRecord.setText(DataFormatUtil.formatOfGameRecord(userData.getGameRecordWin(), userData.getGameRecordLoss()));

        // [iv/C]TextView : totalPlayTime 를 userData 의 getTotalPlayTime 을 특정 문자열로 바꾼 값으로
        totalPlayTime.setText(DataFormatUtil.formatOfPlayTime(userData.getTotalPlayTime()));

        // [iv/C]TextView : totalCost 를 userData 의 getTotalCost 을 특정 문자열로 바꾼 값으로
        totalCost.setText(DataFormatUtil.formatOfCost(userData.getTotalCost()));

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