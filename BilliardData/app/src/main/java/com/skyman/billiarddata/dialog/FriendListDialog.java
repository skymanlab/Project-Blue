package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.UserManagerActivity;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.listView.FriendLvAdapter2;
import com.skyman.billiarddata.table.friend.data.FriendData;

import java.util.ArrayList;

public class FriendListDialog extends DialogFragment implements SectionManager.Initializable {

    // constructor
    private static final String CLASS_NAME = FriendListDialog.class.getSimpleName();

    // constructor
    private static final String FRIEND_DATA_ARRAY_LIST = "friendDataArrayList";

    // instance variable
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable
    private ImageView close;
    private ListView listView;
    private FriendLvAdapter2 adapter2;

    // constructor
    private FriendListDialog() {
    }

    public static FriendListDialog newInstance(ArrayList<FriendData> friendDataArrayList) {
        Bundle args = new Bundle();
        args.putSerializable(FRIEND_DATA_ARRAY_LIST, friendDataArrayList);

        FriendListDialog dialog = new FriendListDialog();
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            friendDataArrayList = (ArrayList<FriendData>) getArguments().getSerializable(FRIEND_DATA_ARRAY_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_friend_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // appDbManager
        initAppDbManager();

        // widget : connect -> init
        connectWidget();
        initWidget();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void initAppDbManager() {
        appDbManager = ((UserManagerActivity) getActivity()).getAppDbManager();
    }

    @Override
    public void connectWidget() {

        close = (ImageView) getView().findViewById(R.id.custom_dialog_friendList_button_close);

        listView = (ListView) getView().findViewById(R.id.custom_dialog_friendList_listView);

    }

    @Override
    public void initWidget() {

        // widget (close) : click listener
        close.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        adapter2 = new FriendLvAdapter2(friendDataArrayList, appDbManager);
        listView.setAdapter(adapter2);

    }
}
