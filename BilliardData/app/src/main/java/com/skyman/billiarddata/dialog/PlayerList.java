package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Debug;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.player.data.PlayerData;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlayerList {

    // constant
    private final String CLASS_NAME_LOG = "[Di]_BilliardModify";

    // instance variable
    private Context context;

    // instance variable
    private LinearLayout[] section;
    private TextView[] name;
    private TextView[] id;
    private TextView[] targetScore;
    private TextView[] score;
    private ImageView close;

    // instance variable
    private ArrayList<PlayerData> playerDataArrayList;

    // constructor
    public PlayerList(Context context) {
        this.context = context;
    }


    /**
     * [method] dialog 생성 및 초기값 설정
     *
     */
    public void setDialog() {
        final String METHOD_NAME_LOG = "[setDialog] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME_LOG + "dialog 를 시작합니다.");

        // [lv/C]Dialog : 객체 생성
        final Dialog dialog = new Dialog(this.context);

        // [lv/C]Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // [lv/C]Dialog : layout 을 custom_dialog_date_modify 으로 설정하기
        dialog.setContentView(R.layout.custom_dialog_player_list);

        // [method] : widget mapping
        mappingOfWidget(dialog);

        // [method] : widget 의 초기값 설정
        setInitialData(this.playerDataArrayList.size());

        // [iv/C]Button : close click listener
        this.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Dialog : dialog dismiss
                dialog.dismiss();
            }
        });

        dialog.show();
    } // End of method [setDialog]


    /**
     * [method] custom_dialog_player_list layout 의 widget 을 mapping
     *
     */
    private void mappingOfWidget(Dialog dialog) {

        // [iv/C]LinearLayout : section 은 4개
        this.section = new LinearLayout[4];

        // [iv/C]LinearLayout : 모든 section mapping
        this.section[0] = (LinearLayout) dialog.findViewById(R.id.custom_dialog_playerList_section_0);
        this.section[1] = (LinearLayout) dialog.findViewById(R.id.custom_dialog_playerList_section_1);
        this.section[2] = (LinearLayout) dialog.findViewById(R.id.custom_dialog_playerList_section_2);
        this.section[3] = (LinearLayout) dialog.findViewById(R.id.custom_dialog_playerList_section_3);

        // [iv/C]TextView : name 은 4개
        this.name = new TextView[4];

        // [iv/C]TextView : 모든 name mapping
        this.name[0] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_name_0);
        this.name[1] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_name_1);
        this.name[2] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_name_2);
        this.name[3] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_name_3);

        // [iv/C]TextView : id 는 4개
        this.id = new TextView[4];

        // [iv/C]TextView : 모든 id mapping
        this.id[0] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_id_0 );
        this.id[1] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_id_1 );
        this.id[2] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_id_2 );
        this.id[3] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_id_3 );

        // [iv/C]TextView : targetScore 는 4개
        this.targetScore = new TextView[4];

        // [iv/C]TextView : 모든 targetScore mapping
        this.targetScore[0] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_target_score_0);
        this.targetScore[1] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_target_score_1);
        this.targetScore[2] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_target_score_2);
        this.targetScore[3] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_target_score_3);

        // [iv/C]TextView : score 는 4개
        this.score = new TextView[4];

        // [iv/C]TextView : 모든 score mapping
        this.score[0] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_score_0);
        this.score[1] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_score_1);
        this.score[2] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_score_2);
        this.score[3] = (TextView) dialog.findViewById(R.id.custom_dialog_playerList_score_3);

        // [iv/C]Button : close mapping
        this.close = (ImageView) dialog.findViewById(R.id.custom_dialog_playerList_button_close);

    } // End of method [mappingOfWidget]


    /**
     * [method] playerDataArrayList 초기 설정
     *
     */
    public void setPlayerDataArrayList(ArrayList<PlayerData> playerDataArrayList) {

         this.playerDataArrayList = playerDataArrayList;

    } // End of method [setPlayerDataArrayList]


    /**
     * [method] playerDataArrayList 로 widget 에 초기값 설정
     *
     */
    private void setInitialData (int playerCount) {

        // [cycle 1] : player 의 수 만큼
        for (int index=0; index<playerCount; index++) {

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
        for (int index=playerCount; index <4; index++) {

            // [iv/C]LinearLayout : section 을 gone 으로 바꾸기
            this.section[index].setVisibility(LinearLayout.GONE);

        } // [cycle 2]

    } // End of method [setInitialData]
}
