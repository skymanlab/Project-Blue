package com.skyman.billiarddata.factivity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.skyman.billiarddata.MainActivity;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.friend.listview.FriendLvManager;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFriendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // instance variable
    private FriendDbManager friendDbManager;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;
    // instance variable
    private ListView allFriendData;
    private EditText name;
    private Button friendAdd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // constructor
    public UserFriendFragment(FriendDbManager friendDbManager, UserData userData, ArrayList<FriendData> friendDataArrayList) {
        this.friendDbManager = friendDbManager;
        this.userData = userData;
        this.friendDataArrayList = friendDataArrayList;
    }

    public UserFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFriend.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFriendFragment newInstance(String param1, String param2) {
        UserFriendFragment fragment = new UserFriendFragment();
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
        return inflater.inflate(R.layout.fragment_user_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [method]mappingOfWidget : fragment_user_friend layout 의 widget mapping
        mappingOfWidget(view);

        // [iv/C]Button : friendAdd button click listener
        this.friendAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setClickListenerOfFriendAddButton : 입력 받은 값으로 friend 데이터를 저장한다.
                setClickListenerOfFriendAddButton();

            }
        });

        // ListView : 받아온 내용을 FriendLvManager 를 이용하여
        // [lv/C]FriendLvManager : ListView 와 Adapter 를 관리하는 ListView 메니저 생성
        FriendLvManager friendLvManager = new FriendLvManager(allFriendData, this.friendDbManager);

        // [iv/C]ArrayList<FriendData> : 해당 userId 로 모든 친구들의 데이터를 가져오기
        friendDataArrayList = friendDbManager.loadAllContentByUserId(this.userData.getId());

        // [check 1] : friendDataArrayList 에 데이터가 있다.
        if (friendDataArrayList.size() != 0) {

            // [lv/C]FriendLvManager : 위에서 받은 friendDataArrayList 를 adapter 에 멤버 변수에 mapping
            friendLvManager.setListViewOfFriendData(friendDataArrayList);

        } else {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[onViewCreated] friendDataArrayList 에 있는 친구 데이터가 없습니다.");
        }
    }


    /**
     * [method] widget mapping
     */
    private void mappingOfWidget(View view) {

        // [iv/C]ListView : allFriendData mapping
        this.allFriendData = (ListView) view.findViewById(R.id.f_user_friend_lv_list);

        // [iv/C]EditText : name mapping
        this.name = (EditText) view.findViewById(R.id.f_user_friend_friend_name);

        // [iv/C]Button : friendAdd mapping
        this.friendAdd = (Button) view.findViewById(R.id.f_user_friend_bt_friend_add);

    } // End of method [mappingOfWidget]


    /**
     * [method] friendAdd button 의 click listener
     */
    private void setClickListenerOfFriendAddButton() {

        // [check 1] : 입력한 user 데이터가 있다.
        if (this.userData != null) {

            // [check 2] : name EditText 에 입력한 값이 있다.
            if (!name.getText().toString().equals("")) {

                // [lv/l]newRowId : friend 테이블에 친구를 추가 하고, 결과값을 받는다.
                long newRowId = friendDbManager.saveContent(
                        userData.getId(),
                        name.getText().toString(),
                        0,
                        0,
                        "-1",
                        0,
                        0);

                // [method]checkNewRowId : newRowId 를 확인하여 역할 수행
                checkNewRowId(newRowId);

                // [iv/C]EditText : name 을 없앤다.
                this.name.setText("");

                // [lv/C]FragmentTransaction : Fragment 화면을 갱신하기 위한 방법
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.detach(UserFriendFragment.this).attach(UserFriendFragment.this).commit();

            } else {
                DeveloperManager.displayLog("[F]_UserFriendFragment", "[setClickListenerOfFriendAddButton] name 이 입력되지 않았습니다.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[setClickListenerOfFriendAddButton] 저장된 user 데이터가 없습니다.");
        } // [check 1]

    } // End of method [setClickListenerOfFriendAddButton]


    /**
     * [method] friend 를 추가하고 나온 결과값 newRowId 확인
     */
    private void checkNewRowId(long newRowId) {

        // [check 1] : newRowId 는 어떤 값일까?
        if (newRowId == -2) {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[checkNewRowId] name 이 입력되지 않았습니다.");
        } else if (newRowId == -1) {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[checkNewRowId] 데이터베이스 insert 를 실패하였습니다.");
        } else if (newRowId == 0) {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[checkNewRowId] 수행이 되지 않았습니다.");
        } else if (newRowId == 1) {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[checkNewRowId] 첫 번째 친구를 입력했습니다.");
            showDialogToCheckWhetherFirstGameDataInput();
        } else if (newRowId > 1) {
            DeveloperManager.displayLog("[F]_UserFriendFragment", "[checkNewRowId] " + newRowId + " 번째 친구를 입력했습니다.");
        } // [check 1]

    } // End of method [checkNewRowId]

    /* method : AlertDialog - 등록된 친구가 없어서 화면이동을 물어보는  */

    /**
     * [method] 첫 friend 를 추가하였으므로 게임 데이터 입력을 할 건지 물어보는 dialog 를 보여준다.
     */
    private void showDialogToCheckWhetherFirstGameDataInput() {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // [lv/C]AlertDialog : 초기값 셋팅
        builder.setTitle(R.string.ad_user_friend_first_friend_add_title)
                .setMessage(R.string.ad_user_friend_first_friend_add_message)
                .setPositiveButton(R.string.ad_user_friend_first_bt_friend_add_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DeveloperManager.displayLog("[F]_UserFriendFragment", "[showDialogToCheckWhetherFirstGameDataInput] 게임 데이터를 입력하기 위해서 MainActivity 로 이동합니다.");

                        // Intent : pageNumber 에 해당 페이지 번호 값을 넣어서 화면 이동
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.ad_user_friend_first_bt_friend_add_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherFirstGameDataInput]

}