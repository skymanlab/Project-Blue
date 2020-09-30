package com.skyman.billiarddata.factivity.statistics;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.factivity.statistics.Utils.DrawableUtils;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // constant
    private final String DATE_DELIM = "년월일 ";

    // value : CalendarView
    private CalendarView calendarView;
    private List<EventDay>  eventDayList;

    // constructor
    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // CalendarView : applandeo material calendar view
        calendarView = (CalendarView) view.findViewById(R.id.f_calendar_cv_game_record);

        // CalendarView : 날짜 클릭 리스너
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
                String dateContent = formatter.format(eventDay.getCalendar().getTime());
                DeveloperManager.displayLog("[F] CalendarFragment", "[calendarView button] " + dateContent);
            }
        });

        // List<EventDay> : 객체 생성
        eventDayList = new ArrayList<>();

        // Test
        setEventDayToBilliardData();

    }

    /* method : 클래스 멤버 변수를 이용하여 calendar view 의 EventDay 를 셋팅하기 */
    private void setEventDayToBilliardData() {
        // BilliardDbManager : billiard 테이블에 모든 내용을 받아온다.
        BilliardDbManager billiardDbManager = new BilliardDbManager(getContext());
        billiardDbManager.init_db();

        // ArrayList<BilliardData> : 위 의 내용을 받을 배열
        ArrayList<BilliardData> billiardDataArrayList = null;

        // check : billiardDbManager 가 초기화 되었다.
        if (billiardDbManager.isInitDb()) {
            // ArrayList<BilliardData> : 내용을 받는다.
            billiardDataArrayList = billiardDbManager.load_contents();

            // check : billiardDataArrayList 의 size 가 0 이 아니다.
            if (billiardDataArrayList.size() != 0) {

                // cycle : billiardDataArrayList 를 size 만큼 돌면서 getDate 값을 확인
                for(int position=0; position < billiardDataArrayList.size(); position++) {
                    String dateContent = billiardDataArrayList.get(position).getDate();
                    DeveloperManager.displayLog("[F] CalendarFragment", "[getDateTokenToDate] 받은 내용 : " +dateContent);

                    // getDate : billiardData 의 date 값을 year, month, day 값으로 분리하기 - [0]:year, [1]:month, [2]:day
                    int[] dateTokenList = getIntDateToDateContent(dateContent);

                    // cycle : 분리된 값들을 이용하여 Calendar 를 set 하고 -> eventList 에 add 하기
                    for (int index=0 ; index < dateTokenList.length; index++) {
                        DeveloperManager.displayLog("[F] CalendarFragment", "[getDateTokenToDate] 받은 내용 [" + position + "] " + index + " 번 : " + dateTokenList[index]);

                        // Calendar : 해당 값들로 setting
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(dateTokenList[0], dateTokenList[1]-1, dateTokenList[2]);

                        // List<EventDay> : 위 의 값을 이용하여 'winner' 표시 - 지금의 값은 승리자와 패배자 구분 없이 그냥 다 넣었다.
                        eventDayList.add(new EventDay(calendar, DrawableUtils.getRedCircleDrawableWithText(getContext(), "W")));
                    }

                    // CalendarView : 위 의 eventDayList 를 setEvents 하기
                    calendarView.setEvents(eventDayList);
                }
            } else {
                DeveloperManager.displayLog("[F] CalendarFragment", "[setEventDayToBilliardData] billiard 테이블에 저장된 내용이 없습니다.");
            }
        } else {
            DeveloperManager.displayLog("[F] CalendarFragment", "[setEventDayToBilliardData] billiardDbManager 가 초기화 되지 않았습니다. : " + billiardDbManager.isInitDb());
        }
    }



    /* method W, method L : ArrayList<BilliardData> 와 UserData 는 한 번만 받아와서 매개변수로 받는다. */
    /* method W : ArrayList<BilliardData> 의 getWinner 값과 UserData 의 getName 값을 비교하여 승리한 날짜를 List<EventDay> 에 add 하여 파란색으로 setting */

    /* method L: ArrayList<BilliardData> 의 getWinner 값과 UserData 의 getName 값을 비교하여 패배한 날짜를 List<EventDay> 에 add 하여 빨간색으로 setting  */


    /* method : date 를 year, month, day 로 구분하여 ArrayList<String> 으로 반환하기 */
    private ArrayList<String> getDateTokenToDate(String date){

        /*
        * ========================================================================================
        * - 매개변수 형태 : "2020년 9월 30일"
        * - 방식 : StringTokenizer , "년|월|일| "
        *          (참고) '자바의 정석 1권' 514. StringTokenizer
        * */
        // ArrayList<String> : 분할 한 값 담을 객체 생성
        ArrayList<String> dateTokenList = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(date,DATE_DELIM);

        while(tokenizer.hasMoreTokens()){
            dateTokenList.add(tokenizer.nextToken());
        }

        return  dateTokenList;
    }

    /* method : date 를 year, month, day 로 구분하여 int 배열로 반환하기 */
    private int[] getIntDateToDateContent(String date){

        /*
         * ========================================================================================
         * - 매개변수 형태 : "2020년 9월 30일"
         * - 방식 : StringTokenizer , "년|월|일| "
         *          (참고) '자바의 정석 1권' 514. StringTokenizer
         * */
        // ArrayList<String> : 분할 한 값 담을 객체 생성
//        ArrayList<String> dateTokenList = new ArrayList<>();
        int[] dateTokenList = new int[3];
        StringTokenizer tokenizer = new StringTokenizer(date,DATE_DELIM);

        // cycle :
        for(int positon=0; tokenizer.hasMoreTokens(); positon++){
            dateTokenList[positon] = Integer.parseInt(tokenizer.nextToken());
        }

        return  dateTokenList;
    }
}