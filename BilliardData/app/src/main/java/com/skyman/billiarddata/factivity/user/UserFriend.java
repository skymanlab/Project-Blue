package com.skyman.billiarddata.factivity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;

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

    // value : FirendDbManager
    private FriendDbManager friendDbManager;

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
        if(friendDbManager != null){
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
                // check : friendDbManager  null?
                if(friendDbManager != null) {
                    // check : name
                    if(!name.getText().toString().equals("")) {
                        // FriendDbManager : save_content method executing
                        friendDbManager.save_content(name.getText().toString());

                        // FriendDbManager : load_contents method executing
                        friendDbManager.load_contents();
                    } else {

                    }
                } else {

                }
            }
        });
    }
}