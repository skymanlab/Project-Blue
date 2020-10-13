package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.calendar.SameDateChecker;
import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

public class BilliardInfo {

    // constant
    private final String CLASS_NAME_LOG = "";

    // instance variable
    private HorizontalScrollView winOrLoss;
    private LinearLayout linearLayout;
    private TextView date;
    private TextView speciality;
    private TextView playTime;
    private TextView winner;
    private TextView score;
    private TextView cost;
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

        // [check 1] : dateChecker 의 size 가 0 보다 크다.
        if (this.sameDateChecker.getArraySize() != 0) {

            // [lv/C]ArrayList<DateChecker.WinLoss> : billiardDataArrayList 에서 index 번째의 billiardData 와 같은 날짜의 index 를 모두 표시하기
            ArrayList<SameDateChecker.SameDateItem> sameDateItemArrayList = this.sameDateChecker.getSameDateItemToIndex(this.index);

            // LinearLayout 에 들어가는 Button 의 설정
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, 110);
            params.setMargins(10,10,10,10);

            // [cycle 1] : dataChecker 의 getArraySize 만큼 순환하며
            for (int position=0 ; position < sameDateItemArrayList.size() ; position++){

                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog]Win or loss : " + sameDateItemArrayList.get(position).isWinner()  + " // billiard count : " + sameDateItemArrayList.get(position).getBilliardCountNumber());

                // [lv/C]Button : 버튼 동적으로 생성
                Button winOrLossButton = new Button(dialog.getContext());

                // [lv/C]Button : 위 의 버튼 설정 / 너비=110, 높이=110, layout_margin=10, id=position, textColor=#FFFFF
                winOrLossButton.setLayoutParams(params);
                winOrLossButton.setId(position);
                winOrLossButton.setTextColor(Color.parseColor("#FFFFFF"));

                // [check 2] : sameDateItemArrayList 의 isWinner 가 true 이다. 즉 승리이다.
                if (sameDateItemArrayList.get(position).isWinner()){
                    // 승리
                    // [lv/C]Button : 승리한 경우의 버튼 만들기 / color 도 R.color.colorBlue 로 변경
                    winOrLossButton.setText("승리");
                    winOrLossButton.setBackgroundResource(R.color.colorBlue);

                } else {
                    // 패배
                    // [lv/C]Button : 패배한 경우의 버튼 만들기 / color 도 R.color.colorBlue 로 변경
                    winOrLossButton.setText("패배");
                    winOrLossButton.setBackgroundResource(R.color.colorRed);
                } // [check 2]

                // [lv/i]billiardCount : winLossArrayList 에서 가져온 billiard 의 count 값을 index 의 값으로 만들기 위해 -1
                int billiardCount = (int)( sameDateItemArrayList.get(position).getBilliardCountNumber() -1);

                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] billiard count : " + billiardCount);

                // [lv/C]Button : winOrLossButton click listener
                winOrLossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        // [iv/C]TextView : date, speciality, playTime, winner, score, cost 를 billiardData 값으로 
                        date.setText(billiardDataArrayList.get(billiardCount).getDate());
                        speciality.setText(billiardDataArrayList.get(billiardCount).getSpeciality());
                        playTime.setText(billiardDataArrayList.get(billiardCount).getPlayTime() + "");
                        winner.setText(billiardDataArrayList.get(billiardCount).getWinner());
                        score.setText(billiardDataArrayList.get(billiardCount).getScore() +"");
                        cost.setText(billiardDataArrayList.get(billiardCount).getCost()+"");

                    }
                });
                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] date : " + billiardDataArrayList.get(billiardCount).getDate());
                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] speciality : " + billiardDataArrayList.get(billiardCount).getSpeciality());
                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] playTime : " + billiardDataArrayList.get(billiardCount).getPlayTime());
                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] winner : " + billiardDataArrayList.get(billiardCount).getWinner());
                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] score : " + billiardDataArrayList.get(billiardCount).getScore());
                DeveloperManager.displayLog("[Di]_BilliardInfo", "[setDialog] cost : " + billiardDataArrayList.get(billiardCount).getCost());



                // [iv/C]HorizontalScrollView : 위에서 생성한 버튼 넣기
                this.linearLayout.addView(winOrLossButton);

                // [lv/C]Toast : 버튼을 눌러달라는 메시지 출력
                Toast.makeText(dialog.getContext(), "확인을 할려면 버튼을 눌러주세요.", Toast.LENGTH_LONG).show();

            } // [cycle 1]

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
        this.speciality = (TextView) dialog.findViewById(R.id.c_di_billiard_info_speciality);

        // [iv/C]TextView : playTime mapping
        this.playTime = (TextView) dialog.findViewById(R.id.c_di_billiard_info_play_time);

        // [iv/C]TextView : winner mapping
        this.winner = (TextView) dialog.findViewById(R.id.c_di_billiard_info_winner);

        // [iv/C]TextView : score mapping
        this.score = (TextView) dialog.findViewById(R.id.c_di_billiard_info_score);

        // [iv/C]TextView : cost mapping
        this.cost = (TextView) dialog.findViewById(R.id.c_di_billiard_info_cost);

        // [iv/C]Button : close mapping
        this.close = (Button) dialog.findViewById(R.id.c_di_billiard_info_bt_close);

    } // End of method [mappingOfWidget]


}
