package com.skyman.billiarddata.factivity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.skyman.billiarddata.MainActivity;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.friend.listview.FriendLvManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFriend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFriend extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // value : widget
    private ListView allFriendData;
    private EditText name;
    private Button friendAdd;

    // value : DbManager
    private UserDbManager userDbManager;
    private FriendDbManager friendDbManager;

    // value : Data
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;

    // constructor
    public UserFriend(UserDbManager userDbManager, UserData userData, FriendDbManager friendDbManager) {
        this.userDbManager = userDbManager;
        this.userData = userData;
        this.friendDbManager = friendDbManager;
    }

    public UserFriend() {
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
    public static UserFriend newInstance(String param1, String param2) {
        UserFriend fragment = new UserFriend();
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
        // FriendDbManager :
        friendDbManager = new FriendDbManager(view.getContext());
        if (friendDbManager != null) {
            friendDbManager.init_db();
        }
        // ListView : allFriendData setting
        allFriendData = (ListView) view.findViewById(R.id.f_user_friend_lv_list);

        // EditText : name setting
        name = (EditText) view.findViewById(R.id.f_user_friend_friend_name);

        // Button : friendAdd setting
        friendAdd = (Button) view.findViewById(R.id.f_user_friend_bt_friend_add);
        friendAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userData != null) {
                    // check : friendDbManager  null?
                    if (friendDbManager != null) {
                        // check : name
                        if (!name.getText().toString().equals("")) {

                            DeveloperManager.displayLog("UserFriend", "모든 조건이 통과되었습니다. : " + userData.getId());
                            // FriendDbManager : save_content method executing
                            long newRowId = friendDbManager.save_content(
                                    userData.getId(),
                                    name.getText().toString(),
                                    0,
                                    0,
                                    "-1",
                                    0,
                                    0);

                            // reset : name 리셋
                            resetWidget();

                            // check : newRowId 체크
                            if(newRowId == -2) {
                                DeveloperManager.displayLog("UserFriend", "name 이 입력되지 않았습니다.");
                            } else if(newRowId == -1) {
                                DeveloperManager.displayLog("UserFriend", "데이터베이스 insert 를 실패하였습니다.");
                            } else if(newRowId == 0) {
                                DeveloperManager.displayLog("UserFriend", "수행이 되지 않았습니다.");
                            } else if(newRowId  == 1){
                                DeveloperManager.displayLog("UserFriend", "첫 번째 친구를 입력했습니다.");
                                showAlertFirstFriendAdd();
                            } else if(newRowId >1) {
                                DeveloperManager.displayLog("UserFriend", newRowId + " 번째 친구를 입력했습니다.");
                            }

                            // FragmentTransaction : 추가 된 내용 갱신을 위한
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.detach(UserFriend.this).attach(UserFriend.this).commit();
                        } else {
                            DeveloperManager.displayLog("UserFriend", "name 이 입력되지 않았습니다.");
                        }
                    } else {
                        DeveloperManager.displayLog("UserFriend", "userDbManager 가 없습니다.");
                    }
                } else {
                    DeveloperManager.displayLog("UserFriend", "userData 가 없습니다.");
                }
            }
        });

        // ListView : 받아온 내용을 FriendLvManager 를 이용하여
        FriendLvManager friendLvManager = new FriendLvManager(allFriendData);
        friendDataArrayList = friendDbManager.load_contents();

        // ArrayList<FriendData> : 내용을 넣기
        if(friendDataArrayList.size() != 0){
            friendLvManager.setListViewToAllFriendData(friendDataArrayList);
        } else {
            DeveloperManager.displayLog("F UserFriend", "저장 된 친구가 없습니다.");
        }
    }

    /* method : reset */
    private void resetWidget() {
        name.setText("");
    }

    /* method : AlertDialog - 등록된 친구가 없어서 화면이동을 물어보는  */
    private void showAlertFirstFriendAdd(){
        // AlertDialog.Builder :
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("다음 화면 이동")
                .setMessage("모든 정보가 입력되었습니다. 게임 데이터를 등록하겠습니까?")
                .setPositiveButton("다음화면", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeveloperManager.displayLog("MainActivity", "게임 데이터를 입력하기 위해서 MainActivity 로 이동합니다.");
                        // Intent : pageNumber 에 해당 페이지 번호 값을 넣어서 화면 이동
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}