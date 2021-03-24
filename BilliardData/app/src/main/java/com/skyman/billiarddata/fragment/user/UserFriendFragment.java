package com.skyman.billiarddata.fragment.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.friend.ListView.FriendLvAdapter2;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager2;
import com.skyman.billiarddata.management.projectblue.database.AppDbManager;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFriendFragment extends Fragment implements SectionManager.Initializable {

    // constant
    private final String CLASS_NAME = UserFriendFragment.class.getSimpleName();

    // constant
    private static final String USER_DATA = "userData";

    // instance variable
    private UserData userData;

    // instance variable
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable
    private EditText name;
    private Button friendAdd;
    private ListView friendListView;

    // instance variable
    private FriendLvAdapter2 adapter2;

    // constructor
    public UserFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserFriend.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFriendFragment newInstance(UserData userData) {
        UserFriendFragment fragment = new UserFriendFragment();
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
        return inflater.inflate(R.layout.fragment_user_friend, container, false);
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
                "save/UserFriend",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        DeveloperManager.displayLog(
                                CLASS_NAME,
                                "=====> FragmentResultListener / User Friend"
                        );

                        DeveloperManager.displayLog(
                                CLASS_NAME,
                                "requestKey : " + requestKey
                        );

                        if (requestKey.equals("save/UserFriend")) {

                            userData = (UserData) result.get(UserData.class.getSimpleName());

                        }
                    }
                }
        );


        // user input fragment 의
        // delete 한 결과를 받는다.
        getParentFragmentManager().setFragmentResultListener(
                "delete/UserFriend",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        DeveloperManager.displayLog(
                                CLASS_NAME,
                                "=====> FragmentResultListener / User Friend"
                        );

                        DeveloperManager.displayLog(
                                CLASS_NAME,
                                "requestKey : " + requestKey
                        );

                        if (requestKey.equals("delete/UserFriend")) {

                            DeveloperManager.displayLog(
                                    CLASS_NAME,
                                    "======> 삭제 되었습니다. userData 객체는 : " + userData
                            );
                            DeveloperManager.displayToUserData(
                                    CLASS_NAME,
                                    userData
                            );

                            // 삭제된 내용을 반영하기 위해서
                            // userData 를 null 로 변경한다.
                            userData = null;

                            // 등록된 친구도 모두 삭제 되었으므로 listView 에 적용하기
                            friendDataArrayList.clear();
                            adapter2.notifyDataSetChanged();

                        }
                    }
                }
        );

    }


    @Override
    public void initAppDbManager() {
        appDbManager = ((UserManagerActivity) getActivity()).getAppDbManager();
    }

    @Override
    public void connectWidget() {

        // [iv/C]ListView : friendListView mapping
        this.friendListView = (ListView) getView().findViewById(R.id.F_userFriend_listSection_listView);

        // [iv/C]EditText : name mapping
        this.name = (EditText) getView().findViewById(R.id.F_userFriend_addSection_friendName);

        // [iv/C]Button : friendAdd mapping
        this.friendAdd = (Button) getView().findViewById(R.id.F_userFriend_addSection_button_addFriend);

    }

    @Override
    public void initWidget() {

        friendDataArrayList = new ArrayList<>();
        adapter2 = new FriendLvAdapter2(friendDataArrayList, appDbManager);

        if (userData != null) {

            appDbManager.requestFriendQuery(
                    new AppDbManager.FriendQueryRequestListener() {
                        @Override
                        public void requestQuery(FriendDbManager2 friendDbManager2) {

                            friendDataArrayList.addAll(
                                    friendDbManager2.loadAllContentByUserId(userData.getId())
                            );

                            friendListView.setAdapter(adapter2);

                        }
                    }
            );
        }


        // [iv/C]Button : friendAdd button click listener
        this.friendAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfFriendAddButton : 입력 받은 값으로 friend 데이터를 저장한다.
                setClickListenerOfFriendAddButton();

            }
        });

    }

    /**
     * [method] friendAdd button 의 click listener
     */
    private void setClickListenerOfFriendAddButton() {

        final String METHOD_NAME = "[setClickListenerOfFriendAddButton] ";

        // [check 1] : 입력한 user 데이터가 있다.
        if (this.userData != null) {

            // [check 2] : name EditText 에 입력한 값이 있다.
            if (!name.getText().toString().equals("")) {

                // <사용자 확인>
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.F_userFriend_dialog_friendAdd_title)
                        .setMessage(R.string.F_userFriend_dialog_friendAdd_message)
                        .setPositiveButton(R.string.F_userFriend_dialog_friendAdd_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "게임 데이터를 입력하기 위해서 MainActivity 로 이동합니다.");

                                appDbManager.requestFriendQuery(
                                        new AppDbManager.FriendQueryRequestListener() {
                                            @Override
                                            public void requestQuery(FriendDbManager2 friendDbManager2) {

                                                try {

                                                    String contentOfFriendName = removeWhitespaceOfName(name.getText().toString());

                                                    // 친구 추가
                                                    long rowNumber = friendDbManager2.saveContent(
                                                            userData.getId(),
                                                            contentOfFriendName,
                                                            0,
                                                            0,
                                                            0,
                                                            0,
                                                            0
                                                    );

                                                    if (rowNumber > 0) {

                                                        // 등록된 친구 정보를 가져와서
                                                        // friendDataArrayList 에 추가하기
                                                        friendDataArrayList.clear();
                                                        friendDataArrayList.addAll(
                                                                friendDbManager2.loadAllContentByUserId(userData.getId())
                                                        );

                                                        // adapter 에게 friendDataArrayList 가 변경되었다고 알려주기
                                                        adapter2.notifyDataSetChanged();

                                                        // widget : 입력한 이름 지우기
                                                        name.setText("");

                                                        // <사용자 알림>
                                                        Toast.makeText(
                                                                getContext(),
                                                                R.string.F_userFriend_noticeUser_friendAdd_success,
                                                                Toast.LENGTH_SHORT
                                                        ).show();

                                                    }

                                                } catch (Exception e) {

                                                    e.printStackTrace();

                                                    // <사용자 알림>
                                                    Toast.makeText(
                                                            getContext(),
                                                            R.string.F_userFriend_noticeUser_friendAdd_error,
                                                            Toast.LENGTH_SHORT
                                                    ).show();

                                                }

                                            }
                                        }
                                );

                                // [lv/C]FragmentTransaction : Fragment 화면을 갱신하기 위한 방법
//                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                                transaction.detach(UserFriendFragment.this).attach(UserFriendFragment.this).commit();

                            }
                        })
                        .setNegativeButton(R.string.F_userFriend_dialog_friendAdd_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            } else {

                // <사용자 알림>
                Toast.makeText(
                        getContext(),
                        R.string.F_userFriend_noticeUser_checkInputAllData,
                        Toast.LENGTH_SHORT
                ).show();

            } // [check 2]

        } else {

            // <사용자 알림>
            Toast.makeText(
                    getContext(),
                    R.string.F_userFriend_noticeUser_onUserData,
                    Toast.LENGTH_SHORT
            ).show();

        }

    } // End of method [setClickListenerOfFriendAddButton]


    /**
     * [method] user 의 name 문자열의 공백을 제거
     */
    private String removeWhitespaceOfName(String name) {

//        return name.replace(" ", "");
        return name.trim();
    } // End of method [removeWhitespaceOfName]


}