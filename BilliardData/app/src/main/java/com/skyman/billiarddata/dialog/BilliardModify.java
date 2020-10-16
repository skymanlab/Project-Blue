package com.skyman.billiarddata.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;


public class BilliardModify  {

    // constant
    private final String CLASS_NAME_LOG = "[Di]_BilliardModify";

    // instance variable
    private Context context;

    // instance variable
    private EditText count;             // 0. count
    private EditText userId;            // 1. user id
    private EditText targetScore;       // 3. target score
    private EditText speciality;        // 4. speciality
    private EditText playTime;          // 5. play time
    private EditText winner;            // 6. winner
    private EditText score;             // 7. score
    private EditText cost;              // 8. cost
    private Spinner dateYear;           // 2. date - year
    private Spinner dateMonth;          // 2. date - month
    private Spinner dateDay;            // 2. date - day
    private Button modify;
    private Button cancel;

    // instance variable
    private BilliardDBManager billiardDBManager;
    private BilliardData billiardData;

    // constructor
    public BilliardModify(Context context, BilliardDBManager billiardDBManager, BilliardData billiardData){
        this.context = context;
        this.billiardDBManager = billiardDBManager;
        this.billiardData = billiardData;
    }

    /**
     * [method] Dialog 객체를 생성하고, dialog 를 통해서 widget 을 mapping 한다.
     */
    public void setDialog() {

        // [lv/C]String : method name log
        final String METHOD_NAME_LOG = "[setDialog] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME_LOG + "dialog 를 시작합니다.");

        // [lv/C]Dialog : 객체 생성
        final Dialog dialog = new Dialog(this.context);

        // [lv/C]Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // [lv/C]Dialog : layout 을 custom_dialog_date_modify 으로 설정하기
        dialog.setContentView(R.layout.custom_dialog_billiard_modify);

        // [method]mappingOfWidget : custom_dialog_billiard_modify layout 의 widget mapping
        mappingOfWidget(dialog);

        // [method]setTextWithBilliardData : billiardData 의 값을 custom_dialog_billiard_modify 의 widget 의 초기값을 설정
        setTextWithBilliardData(dialog);

        // [iv/C]Button : modify click listener
        this.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogToCheckWhetherModify : 수정을 정말 진행할 건지 물어보는 AlertDialog 를 보여준다.
                showDialogToCheckWhetherModify();

            }
        });

        // [iv/C]Button : cancel click listener
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Dialog : dialog 종료
                dialog.dismiss();
            }
        });

        // [lv/C]Dialog : 위에서 설정 된 dialog 를 화면에 보여준다.
        dialog.show();

    } // End of method [setDialog]


    /**
     * [method] custom_dialog_billiard_modify layout 의 widget 들을 mapping 한다.
     *
     */
    public void mappingOfWidget(Dialog dialog) {

        // [iv/C]EditText : count mapping
        this.count  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_count);

        // [iv/C]EditText : userId mapping
        this.userId  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_user_id);

        // [iv/C]EditText : targetScore mapping
        this.targetScore  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_target_score);

        // [iv/C]EditText : speciality mapping
        this.speciality  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_speciality);

        // [iv/C]EditText : playTime mapping
        this.playTime  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_play_time);

        // [iv/C]EditText : winner mapping
        this.winner  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_winner);

        // [iv/C]EditText : score mapping
        this.score  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_score);

        // [iv/C]EditText : cost mapping
        this.cost  = (EditText) dialog.findViewById(R.id.c_di_billiard_modify_cost);

        // [iv/C]Spinner : dateYear mapping
        this.dateYear = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_date_year);

        // [iv/C]Spinner : dateMonth mapping
        this.dateMonth = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_date_month);

        // [iv/C]Spinner : dateDay mapping
        this.dateDay = (Spinner) dialog.findViewById(R.id.c_di_billiard_modify_date_day);

        // [iv/C]Button : modify mapping
        this.modify = (Button) dialog.findViewById(R.id.c_di_billiard_modify_bt_modify);

        // [iv/C]Button : cancel mapping
        this.cancel = (Button) dialog.findViewById(R.id.c_di_billiard_modify_bt_cancel);

    } // End of method [mappingOfWidget]


    /**
     * [method] billiardData 의 값을 EditText 에 초기값으로 출력한다.
     *
     */
    private void setTextWithBilliardData(Dialog dialog) {

        final String METHOD_NAME_LOG = "[setTextWithBilliardData] ";

        // [iv/C]EditText : count set text
        this.count.setText(this.billiardData.getCount()+"");

        // [iv/C]EditText : userId set text
        this.userId.setText(this.billiardData.getUserId()+"");

        // [iv/C]EditText : targetScore set text
        this.targetScore.setText(this.billiardData.getTargetScore()+"");

        // [iv/C]EditText : speciality set text
        this.speciality.setText(this.billiardData.getSpeciality());

        // [iv/C]EditText : playTime set text
        this.playTime.setText(this.billiardData.getPlayTime()+"");

        // [iv/C]EditText : winner set text
        this.winner.setText(this.billiardData.getWinner());

        // [iv/C]EditText : score set text
        this.score.setText(this.billiardData.getScore()+"");

        // [iv/C]EditText : cost set text
        this.cost.setText(this.billiardData.getCost()+"");

        // [lv/i]classificationDate : '####년 ##월 ##일' 형태의 날짜를 숫자만 구분하기
        int[] classificationDate = ProjectBlueDataFormatter.changeDateToIntArrayType(this.billiardData.getDate());

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME_LOG + " [0]=" + classificationDate[0] + " / [1]=" + classificationDate[1] + " / [2]=" + classificationDate[2]);

        // [lv/C]ArrayAdapter : R.array.year 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateYearAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.year, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateYear select / 위 에서 만든 dateYearAdapter 연결하기 / classificationDate 의 index=0 인 값을 선택값으로 만들기
        this.dateYear.setAdapter(dateYearAdapter);
        this.dateYear.setSelection(classificationDate[0]-2020);

        // [lv/C]ArrayAdapter : R.array.month 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateMonthAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.month, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateMonth select
        this.dateMonth.setAdapter(dateMonthAdapter);
        this.dateMonth.setSelection(classificationDate[1]-1);

        // [lv/C]ArrayAdapter : R.array.day 을 값을 spinner 에 연결하기 위한 Adapter 생성
        ArrayAdapter dateDayAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.day, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : dateDay select
        this.dateDay.setAdapter(dateDayAdapter);
        this.dateDay.setSelection(classificationDate[2]-1);

    } // End of method [setTextWithBilliardData]


    /**
     * [method] 정말 수정할 건지 물어보는 alert dialog 를 보여준다.
     *
     */
    private void showDialogToCheckWhetherModify() {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        // [lv/C]AlertDialog : Builder 초기값 설정
        builder.setTitle(R.string.ad_billiard_modify_check_modify_title)
                .setMessage(R.string.ad_billiard_modify_check_modify_message)
                .setPositiveButton(R.string.ad_billiard_modify_bt_check_modify_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method]updateAllData : 해당 count 로 billiard 테이블을 수정한다.
                        updateAllData();

                        // [lv/C]Dialog : dialog 종료
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.ad_billiard_modify_bt_check_modify_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Dialog : dialog 종료
                        dialog.dismiss();

                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherModify]
    
    
    /**
     * [method] 수정 된 값을 이용하여 해당 count 로 billiard 테이블을 수정한다.
     *
     */
    private void updateAllData() {

        final String METHOD_NAME_LOG = "[updateAllData] ";

        // [lv/i]resultMethod : billiardDBManager 에서 updateContentByCount 실행한 결과
        int resultMethod = billiardDBManager.updateContentByCount(
                Long.parseLong(count.getText().toString()),
                Long.parseLong(userId.getText().toString()),
                ProjectBlueDataFormatter.getFormatOfDate(dateYear.getSelectedItem().toString(), dateMonth.getSelectedItem().toString(), dateDay.getSelectedItem().toString()),
                Integer.parseInt(targetScore.getText().toString()),
                speciality.getText().toString(),
                Integer.parseInt(playTime.getText().toString()),
                winner.getText().toString(),
                score.getText().toString(),
                Integer.parseInt(cost.getText().toString())
        );

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME_LOG + "입력을 성공하였습니다. 결과값은 " + resultMethod);

    } // End of method [updateAllData]
}
