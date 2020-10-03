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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DateModify {

    // const : date format
    private final String DATE_FORMAT = "{0}년 {1}월 {2}일";

    // variable
    private Spinner year;
    private Spinner month;
    private Spinner day;

    // variable : Dialog 를 생성하는 곳의 Activity context
    private Context context;

    // constructor
    public DateModify(Context context) {
        this.context = context;
    }

    // method : 다이어로그 세팅 및 show, custom_dialog_date_modify.xml 의 widget 셋팅
    public void setDialog(final TextView date) {

        final Dialog dialog = new Dialog(context);

        // Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Dialog : 커스텀 한 layout 으로 setting
        dialog.setContentView(R.layout.custom_dialog_date_modify);

        // Dialog : show
        dialog.show();

        // Spinner : year
        year = (Spinner) dialog.findViewById(R.id.d_re_date_sp_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapter);

        // Spinner : month
        month = (Spinner) dialog.findViewById(R.id.d_re_date_sp_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.month, android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(monthAdapter);

        // Spinner : day
        day = (Spinner) dialog.findViewById(R.id.d_re_date_sp_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.day, android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayAdapter);

        // Date : 최근 날짜를 가져와 Spinner 를 최근 날짜로 setting

        // Button : modify
        Button dateModify = (Button) dialog.findViewById(R.id.d_re_date_bt_modify);
        dateModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView : dialog 를 부른 BilliardInputActivity 의 'date'의 값을 spinner 에서 선택 된 값으로 셋팅
                date.setText(
                        setDate(Integer.parseInt(year.getSelectedItem().toString()),
                                Integer.parseInt(month.getSelectedItem().toString()),
                                Integer.parseInt(day.getSelectedItem().toString()))
                );

                // Dialog : 셋팅 과 함께 dialog 를 dismiss (종료) 한다.
                dialog.dismiss();

            }
        });

        setNowDate(dialog);
    }


    /* method : Spinner 에서 선택한 값을 'yyyy년 ?M월 dd일'로 만들어 반환한다. */
    private String setDate(String year, String month, String day) {
        String[] arguments = {year, month, day};
        return MessageFormat.format(DATE_FORMAT, arguments);
    }

    /* method : Spinner 에서 선택한 값을 'yyyy년 MM월 dd일'로 만들어 반환한다. */
    private String setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);
        DeveloperManager.displayLog("DateModify", "[setDate] 변환 값은 : " + ProjectBlueDataFormatter.setFormatToDate(calendar.getTime()));

        return  ProjectBlueDataFormatter.setFormatToDate(calendar.getTime());
    }

    /* method : 현재 날짜를 Date 클래스로 부터 받아오고 spinner 를 초기값 설정*/
    private void setNowDate(Dialog dialog) {
        SimpleDateFormat nowDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = nowDateFormat.format(new Date());
        StringTokenizer nowDateToken = new StringTokenizer(nowDate, "-");
        DeveloperManager.displayLog("DateModify", "날짜 : " + nowDate);
        String[] date = new String[3];

        for (int index = 0; nowDateToken.hasMoreTokens(); index++) {
            date[index] = nowDateToken.nextToken();
            DeveloperManager.displayLog("DateModify", index + " = " + date[index]);
            if (index == 0) {
                year.setSelection(Integer.parseInt(date[index])-2020);
            } else if (index == 1) {
                DeveloperManager.displayLog("DateModify", index + " = " + Integer.parseInt(date[index]));
                month.setSelection(Integer.parseInt(date[index]) - 1);
            } else if(index ==2) {
                day.setSelection(Integer.parseInt(date[index])-1);
            }

        }
    }
}
