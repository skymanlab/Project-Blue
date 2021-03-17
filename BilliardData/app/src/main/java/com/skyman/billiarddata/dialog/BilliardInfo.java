package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.statistics.SameDateChecker;
import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

public class BilliardInfo {

    // constant
    private final String CLASS_NAME_LOG = "[Di]_BilliardInfo";

    // instance variable
    private LinearLayout linearLayout;
    private TextView date;                      // 1. date
    private TextView gameMode;                  // 2. game mode
    private TextView winnerName;                // 5. winner name
    private TextView playTime;                  // 6. play time
    private TextView score;                     // 7. score
    private TextView cost;                      // 8. cost
    private Button close;

    // instance variable
    private Context context;

    // instance variable
    private SameDateChecker sameDateChecker;
    private ArrayList<BilliardData> billiardDataArrayList;
    private int index;

    // constructor
    public BilliardInfo(Context context, SameDateChecker sameDateChecker, ArrayList<BilliardData> billiardDataArrayList, int index) {
        this.context = context;
        this.sameDateChecker = sameDateChecker;
        this.billiardDataArrayList = billiardDataArrayList;
        this.index = index;
    }


    /**
     * [method] Dialog 객체를 생성하고, dialog 를 통해서 widget 을 mapping 한다.
     */
    public void setDialog() {

        final String METHOD_NAME = "[setDialog] ";

        // [lv/C]Dialog : 객체 생성
        final Dialog dialog = new Dialog(this.context);

        // [lv/C]Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // [lv/C]Dialog : layout 을 custom_dialog_date_modify 으로 설정하기
        dialog.setContentView(R.layout.custom_dialog_billiard_info);

        // [method]mappingOfWidget : custom_dialog_billiard_info layout 의 모든 widget 을  mapping 한다.
        mappingOfWidget(dialog);

        // [iv/C]Button : close click listener
        this.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // [lv/C]Button : 같은 날짜에 속해있는 billiardData 를 보기위한 버튼을 생성하기
        Button[] billiardInfo;

        // [check 1] : dateChecker 의 size 가 0 보다 크다.
        if (this.sameDateChecker.getArraySize() != 0) {

            // [lv/C]Button : 같은 날짜에 속해있는 billiardData 의 개수 만큼
            billiardInfo = new Button[this.sameDateChecker.getArraySize()];

            // [lv/C]ArrayList<SameDateChecker.SameDateItem> : billiardDataArrayList 에서 index 번째의 billiardData 와 같은 날짜의 index 를 모두 표시하기
            ArrayList<SameDateChecker.SameDateItem> sameDateItemArrayList = this.sameDateChecker.getSameDateItemToIndex(this.index);

            // LinearLayout 에 들어가는 Button 의 설정
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);

            // [cycle 1] : dataChecker 의 getArraySize 만큼 순환하며
            for (int position = 0; position < sameDateItemArrayList.size(); position++) {

                // [lv/C]Button : 버튼 동적으로 생성
                billiardInfo[position] = new Button(dialog.getContext());

                // [lv/C]Button : billiardInfo 버튼을 설정
                billiardInfo[position].setLayoutParams(params);
                billiardInfo[position].setId(100 + position);
                billiardInfo[position].setTextColor(Color.parseColor("#FFFFFF"));

                // [check 2] : sameDateItemArrayList 의 isWinner 가 true 이다. 즉 승리이다.
                if (sameDateItemArrayList.get(position).isWinner()) {
                    // 승리

                    // [lv/C]Button : 승리한 경우의 버튼 만들기 / color 도 R.color.colorBlue 로 변경
                    billiardInfo[position].setText("승리");
                    billiardInfo[position].setBackgroundResource(R.color.colorRed);

                } else {
                    // 패배

                    // [lv/C]Button : 패배한 경우의 버튼 만들기 / color 도 R.color.colorBlue 로 변경
                    billiardInfo[position].setText("패배");
                    billiardInfo[position].setBackgroundResource(R.color.colorBlue);

                } // [check 2]

                // [lv/i] : winLossArrayList 에서 가져온 billiard 가 몇 번째
                int indexOfBilliardArray = (int) sameDateItemArrayList.get(position).getIndex();

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "*********** 승리 여부는 SameDateChecker.SameDateItem 에 winner 에 boolean 값으로 있습니다. *********");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "1. 승리여부는 = " + sameDateItemArrayList.get(position).isWinner());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "2. 승리한 이 게임의 count = " + sameDateItemArrayList.get(position).getBilliardCount());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiardData> 의 정보를 확인할께요!!!!!!!");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "1. date = " + billiardDataArrayList.get(indexOfBilliardArray).getDate());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "2. gameMode = " + billiardDataArrayList.get(indexOfBilliardArray).getGameMode());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "5. winnerId = " + billiardDataArrayList.get(indexOfBilliardArray).getWinnerName());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "6. playTime = " + billiardDataArrayList.get(indexOfBilliardArray).getPlayTime());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "7. score = " + billiardDataArrayList.get(indexOfBilliardArray).getScore());
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "8. cost = " + billiardDataArrayList.get(indexOfBilliardArray).getCost());

                billiardInfo[position].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // [iv/C]TextView : date, gameMode, playTime, winner, score, cost 를 billiardData 값으로
                        date.setText(billiardDataArrayList.get(indexOfBilliardArray).getDate());                   // 1. date
                        gameMode.setText(billiardDataArrayList.get(indexOfBilliardArray).getGameMode());           // 2. game mode
                        winnerName.setText(billiardDataArrayList.get(indexOfBilliardArray).getWinnerName());       // 5. winner name
                        playTime.setText(billiardDataArrayList.get(indexOfBilliardArray).getPlayTime() + "");      // 6. play time
                        score.setText(billiardDataArrayList.get(indexOfBilliardArray).getScore() + "");             // 7. score
                        cost.setText(billiardDataArrayList.get(indexOfBilliardArray).getCost() + "");                // 8. cost
                    }
                });
                // [iv/C]LinearLayout : 위에서 생성한 버튼 넣기
                this.linearLayout.addView(billiardInfo[position]);

                // [lv/C]Toast : 버튼을 눌러달라는 메시지 출력
                Toast.makeText(dialog.getContext(), "확인을 할려면 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();

            } // [cycle 1]

            billiardInfo[0].setClickable(true);
        } else {

        } // [check 1]


        // [lv/C]Dialog : 위에서 설정 된 dialog 를 화면에 보여준다.
        dialog.show();

    } // End of method [setDialog]


    /**
     * [method] custom_dialog_billiard_info layout 의 모든 widget 을 mapping 한다.
     */
    public void mappingOfWidget(Dialog dialog) {

        // [iv/C]HorizontalScrollView : winOfLoss mapping
        this.linearLayout = (LinearLayout) dialog.findViewById(R.id.c_di_billiard_info_win_or_loss);

        // [iv/C]TextView : date mapping
        this.date = (TextView) dialog.findViewById(R.id.c_di_billiard_info_date);

        // [iv/C]TextView : speciality mapping
        this.gameMode = (TextView) dialog.findViewById(R.id.c_di_billiard_info_speciality);

        // [iv/C]TextView : playTime mapping
        this.playTime = (TextView) dialog.findViewById(R.id.c_di_billiard_info_play_time);

        // [iv/C]TextView : winner mapping
        this.winnerName = (TextView) dialog.findViewById(R.id.c_di_billiard_info_winner);

        // [iv/C]TextView : score mapping
        this.score = (TextView) dialog.findViewById(R.id.c_di_billiard_info_score);

        // [iv/C]TextView : cost mapping
        this.cost = (TextView) dialog.findViewById(R.id.c_di_billiard_info_cost);

        // [iv/C]Button : close mapping
        this.close = (Button) dialog.findViewById(R.id.c_di_billiard_info_bt_close);

    } // End of method [mappingOfWidget]


}
