package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.player.data.PlayerData;

import java.util.ArrayList;

public class PlayerListDialog extends DialogFragment implements SectionManager.Initializable {

    // constant
    private static final String PLAYER_DATA_ARRAY_LIST = "playerDataArrayList";

    // instance variable
    private ArrayList<PlayerData> playerDataArrayList;

    // instance variable
    private LinearLayout[] section;
    private TextView[] name;
    private TextView[] id;
    private TextView[] targetScore;
    private TextView[] score;
    private ImageView close;

    // constructor
    public PlayerListDialog() {

    }

    // newInstance
    public static PlayerListDialog newInstance(ArrayList<PlayerData> playerDataArrayList) {

        Bundle args = new Bundle();
        args.putSerializable(PLAYER_DATA_ARRAY_LIST, playerDataArrayList);

        PlayerListDialog playerListDialog = new PlayerListDialog();
        playerListDialog.setArguments(args);

        return playerListDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerDataArrayList = (ArrayList<PlayerData>) getArguments().getSerializable(PLAYER_DATA_ARRAY_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_player_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // widget
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

    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : section 은 4개
        this.section = new LinearLayout[4];

        // [iv/C]LinearLayout : 모든 section mapping
        this.section[0] = (LinearLayout) getView().findViewById(R.id.d_playerList_section_0);
        this.section[1] = (LinearLayout) getView().findViewById(R.id.d_playerList_section_1);
        this.section[2] = (LinearLayout) getView().findViewById(R.id.d_playerList_section_2);
        this.section[3] = (LinearLayout) getView().findViewById(R.id.d_playerList_section_3);

        // [iv/C]TextView : name 은 4개
        this.name = new TextView[4];

        // [iv/C]TextView : 모든 name mapping
        this.name[0] = (TextView) getView().findViewById(R.id.d_playerList_name_0);
        this.name[1] = (TextView) getView().findViewById(R.id.d_playerList_name_1);
        this.name[2] = (TextView) getView().findViewById(R.id.d_playerList_name_2);
        this.name[3] = (TextView) getView().findViewById(R.id.d_playerList_name_3);

        // [iv/C]TextView : id 는 4개
        this.id = new TextView[4];

        // [iv/C]TextView : 모든 id mapping
        this.id[0] = (TextView) getView().findViewById(R.id.d_playerList_id_0);
        this.id[1] = (TextView) getView().findViewById(R.id.d_playerList_id_1);
        this.id[2] = (TextView) getView().findViewById(R.id.d_playerList_id_2);
        this.id[3] = (TextView) getView().findViewById(R.id.d_playerList_id_3);

        // [iv/C]TextView : targetScore 는 4개
        this.targetScore = new TextView[4];

        // [iv/C]TextView : 모든 targetScore mapping
        this.targetScore[0] = (TextView) getView().findViewById(R.id.d_playerList_target_score_0);
        this.targetScore[1] = (TextView) getView().findViewById(R.id.d_playerList_target_score_1);
        this.targetScore[2] = (TextView) getView().findViewById(R.id.d_playerList_target_score_2);
        this.targetScore[3] = (TextView) getView().findViewById(R.id.d_playerList_target_score_3);

        // [iv/C]TextView : score 는 4개
        this.score = new TextView[4];

        // [iv/C]TextView : 모든 score mapping
        this.score[0] = (TextView) getView().findViewById(R.id.d_playerList_score_0);
        this.score[1] = (TextView) getView().findViewById(R.id.d_playerList_score_1);
        this.score[2] = (TextView) getView().findViewById(R.id.d_playerList_score_2);
        this.score[3] = (TextView) getView().findViewById(R.id.d_playerList_score_3);

        // [iv/C]Button : close mapping
        this.close = (ImageView) getView().findViewById(R.id.d_playerList_button_close);

    }

    @Override
    public void initWidget() {

        // player 표시
        setInitialData(playerDataArrayList.size());

        // [iv/C]Button : close click listener
        this.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }


    /**
     * [method] playerDataArrayList 로 widget 에 초기값 설정
     */
    private void setInitialData(int playerCount) {

        // [cycle 1] : player 의 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [iv/C]TextView : name set initial text
            this.name[index].setText(this.playerDataArrayList.get(index).getPlayerName());

            // [iv/C]TextView : id set initial text
            this.id[index].setText(this.playerDataArrayList.get(index).getPlayerId() + "");

            // [iv/C]TextView : targetScore set initial text
            this.targetScore[index].setText(this.playerDataArrayList.get(index).getTargetScore() + "");

            // [iv/C]TextView : score set initial text
            this.score[index].setText(this.playerDataArrayList.get(index).getScore() + "");

        } // [cycle 1]

        // [cycle 2] : player 이외의 section 의 수
        for (int index = playerCount; index < 4; index++) {

            // [iv/C]LinearLayout : section 을 gone 으로 바꾸기
            this.section[index].setVisibility(LinearLayout.GONE);

        } // [cycle 2]

    } // End of method [setInitialData]

}
