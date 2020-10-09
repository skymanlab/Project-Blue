package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class DateModify {

    // constant
    private final String DATE_FORMAT = "{0}년 {1}월 {2}일";

    // instance variable
    private Spinner year;
    private Spinner month;
    private Spinner day;

    // instance variable
    private Context context;

    // constructor
    public DateModify(Context context) {
        this.context = context;
    }


    /**
     * [method] 다이어로그 세팅 및 show, custom_dialog_date_modify.xml 의 widget 셋팅
     */
    public void setDialog(final TextView date) {

        // [lv/C]Dialog : 객체 생성
        final Dialog dialog = new Dialog(context);

        // [lv/C]Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // [lv/C]Dialog : layout 을 custom_dialog_date_modify 으로 설정하기
        dialog.setContentView(R.layout.custom_dialog_date_modify);


        // [iv/C]Spinner : year mapping
        this.year = (Spinner) dialog.findViewById(R.id.d_re_date_sp_year);

        // [lv/C]ArrayAdapter : 위 의 spinner 와 R.array.year 을 연결하기 위한 adapter 생성
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.year, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 최종적으로 year 을 위 의 adapter 와 연결한다.
        this.year.setAdapter(yearAdapter);


        // [iv/C]Spinner : month mapping
        this.month = (Spinner) dialog.findViewById(R.id.d_re_date_sp_month);

        // [lv/C]ArrayAdapter : 위 의 spinner 와 R.array.month 을 연결하기 위한 adapter 생성
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.month, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 최종적으로 month 을 위 의 adapter 와 연결한다.
        this.month.setAdapter(monthAdapter);


        // [iv/C]Spinner : day mapping
        this.day = (Spinner) dialog.findViewById(R.id.d_re_date_sp_day);

        // [lv/C]ArrayAdapter : 위 의 spinner 와 R.array.day 을 연결하기 위한 adapter 생성
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.day, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 최종적으로 day 을 위 의 adapter 와 연결한다.
        this.day.setAdapter(dayAdapter);

        // [method]setNowDate : 현재 날짜를 기준으로 spinner(year, month, day) 의 초기값이 설정되도록 한다.
        setNowDate();

        // [lv/C]Button : dateModify mapping
        Button dateModify = (Button) dialog.findViewById(R.id.d_re_date_bt_modify);
        
        // [lv/C]Button : dateModify click listener 
        dateModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                // [lvP/C]TextView : date 를 spinner 에서 선택한 값으로 설정하기
                date.setText(
                        ProjectBlueDataFormatter.getFormatOfDate(Integer.parseInt(year.getSelectedItem().toString()),
                                Integer.parseInt(month.getSelectedItem().toString()),
                                Integer.parseInt(day.getSelectedItem().toString()))
                );

                // [lv/C]Dialog : 현재의 Dialog 를 dismiss(종료) 한다.
                dialog.dismiss();

            }
        });

        // [lv/C]Dialog : 위에서 설정 된 dialog 를 화면에 보여준다.
        dialog.show();

    } // End of method [setDialog]


    /**
     * [method] Date 클래스를 이용하여 현재 날짜를 받아오고, 이 날짜를 년, 월, 일 단위로 분해한다.
     * 그리고 년, 월, 일 을 int 로 변환하여 spinner 의 위치값을 구한 뒤
     * spinner 의 초기값을 정한다.
     */
    private void setNowDate() {

        // [lv/C]SimpleDateFormat : '####-##-##' 형태의 포맷 만들기
        SimpleDateFormat nowDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // [lv/C]String : 위 의 포맷으로 현재날짜 만들기
        String nowDate = nowDateFormat.format(new Date());

        // [lv/C]StringTokenizer : 위 에서 만든 날짜를 '-' 로 나누기
        StringTokenizer nowDateToken = new StringTokenizer(nowDate, "-");
        DeveloperManager.displayLog("[Di]_DateModify", "[setNowDate] 현재 날짜 : " + nowDate);


        // [cycle 1] : nowDateToken 에 값이 있을 때까지
        for (int position = 0; nowDateToken.hasMoreTokens(); position++) {

            // [lv/C]String : nowDateToken 에서 받아온 값을 담는 임시 변수
            String temp = nowDateToken.nextToken();
            DeveloperManager.displayLog("DateModify", position + " = " + temp);

            // [check 1] : position 의 값에 따라 다른 spinner widget 설정
            if (position == 0) {

                // [iv/C]Spinner : 첫 번째는 '년' 이다. / 2020 년부터 시작하므로 position 은 -2020 한다.
                year.setSelection(Integer.parseInt(temp) - 2020);

            } else if (position == 1) {

                // [iv/C]Spinner : 두 번째는 '월' 이다. / 1월부터 시작하므로 position 은 -1 한다.
                month.setSelection(Integer.parseInt(temp) - 1);

            } else if (position == 2) {

                // [iv/C]Spinner : 세 번째는 '일' 이다. / 1일부터 시작하므로 position 은 -1 한다.
                day.setSelection(Integer.parseInt(temp) - 1);

            } // [check 1]

        } // [cycle 1]

    } // End of method [setNowDate]

}
