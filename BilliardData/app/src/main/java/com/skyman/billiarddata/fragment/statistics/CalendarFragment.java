package com.skyman.billiarddata.fragment.statistics;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.fragment.app.DialogFragment;import androidx.fragment.app.Fragment;import com.applandeo.materialcalendarview.CalendarView;import com.applandeo.materialcalendarview.EventDay;import com.applandeo.materialcalendarview.listeners.OnDayClickListener;import com.skyman.billiarddata.R;import com.skyman.billiarddata.developer.DeveloperManager;import com.skyman.billiarddata.dialog.GameRecordDialog;import com.skyman.billiarddata.etc.SectionManager;import com.skyman.billiarddata.table.billiard.data.BilliardData;import com.skyman.billiarddata.etc.calendar.SameDate;import com.skyman.billiarddata.etc.calendar.DrawableUtil;import com.skyman.billiarddata.etc.DataFormatUtil;import com.skyman.billiarddata.table.user.data.UserData;import java.util.ArrayList;import java.util.Calendar;import java.util.StringTokenizer;/** * A simple {@link Fragment} subclass. * Use the {@link CalendarFragment#newInstance} factory method to * create an instance of this fragment. */public class CalendarFragment extends Fragment implements SectionManager.Initializable {    // constant    private final String CLASS_NAME = CalendarFragment.class.getSimpleName();    private final String DATE_DELIMITER = "년월일 ";    // constant    private static final String USER_DATA = "userData";    private static final String BILLIARD_DATA_ARRAY_LIST = "billiardDataArrayList";    private static final String SAME_DATE_CHECKER = "sameDateChecker";    // instance variable    private UserData userData;    private ArrayList<BilliardData> billiardDataArrayList;    private SameDate sameDate;    // instance variable    private CalendarView calendarView;    // constructor    public CalendarFragment() {        // Required empty public constructor    }    /**     * Use this factory method to create a new instance of     * this fragment using the provided parameters.     *     * @return A new instance of fragment CalendarFragment.     */    // TODO: Rename and change types and number of parameters    public static CalendarFragment newInstance(UserData userData,                                               ArrayList<BilliardData> billiardDataArrayList,                                               SameDate sameDate) {        Bundle args = new Bundle();        args.putSerializable(USER_DATA, userData);        args.putSerializable(BILLIARD_DATA_ARRAY_LIST, billiardDataArrayList);        args.putSerializable(SAME_DATE_CHECKER, sameDate);        CalendarFragment fragment = new CalendarFragment();        fragment.setArguments(args);        return fragment;    }    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        if (getArguments() != null) {            userData = (UserData) getArguments().getSerializable(USER_DATA);            billiardDataArrayList = (ArrayList<BilliardData>) getArguments().getSerializable(BILLIARD_DATA_ARRAY_LIST);            sameDate = (SameDate) getArguments().getSerializable(SAME_DATE_CHECKER);        }    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {        // Inflate the layout for this fragment        return inflater.inflate(R.layout.fragment_calendar, container, false);    }    @Override    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {        super.onViewCreated(view, savedInstanceState);        // appDbManager        initAppDbManager();        // widget : connect -> init        connectWidget();        initWidget();    } // End of method [onViewCreated]    @Override    public void initAppDbManager() {    }    @Override    public void connectWidget() {        this.calendarView = (CalendarView) getView().                findViewById(R.id.F_calendar_calendarView_displayGameRecord);    }    @Override    public void initWidget() {        initWidgetOfCalendarView();    }    private void initWidgetOfCalendarView() {        // 초기 데이터 설정        setInitialDataOfCalendarView();        this.calendarView.setOnDayClickListener(new OnDayClickListener() {            @Override            public void onDayClick(EventDay eventDay) {                onDayClickOfCalendarView(eventDay);            }        });    }    private void setInitialDataOfCalendarView() {        final String METHOD_NAME = "[setInitialDataOfCalendarView] ";        // <1> 사용자 등록 확인        if ((this.userData != null)) {            // <2> 데이터 확인            if ((!this.billiardDataArrayList.isEmpty()) && (this.sameDate != null)) {                // <3>                // sameDateChecker 와 billiardDataArrayList 를 토대로                // sameDateChecker 의 기준 날짜들에 대한 EventDay 객체를 만들고                // 모든 객체를 eventDayArrayList 에 담기                ArrayList<EventDay> eventDayArrayList = createEventDayArrayList(sameDate, billiardDataArrayList);                // <4>                // eventDayArrayList 를 calendarView 에 모두 표시하기                calendarView.setEvents(eventDayArrayList);            } else {                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "userData 와 billiardDataArrayList 로 SameDateChecker 가 만들어지지 않았습니다.");            } // [check 2]        } else {            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "userData 와 billiardDataArrayList 가 없으므로 달력에 표시할 EventDay 가 없습니다.");        } // [check 1]    }    private void onDayClickOfCalendarView(EventDay eventDay) {        final String METHOD_NAME = "[onDayClickOfCalendarView] ";        if (sameDate == null) {            return;        }        // <1>        // 클릭한 날짜에 해당하는 EventDay 객체를 통해서 Calendar 객체를 가져오고        // 이 객체를 통해서 내가 원하는 data format 으로 변환한 문자열을 가져온다.        String selectedDate = DataFormatUtil.formatOfDate(eventDay.getCalendar().getTime());        // <2>        // selectedDate 와 같은 날짜를 billiardDataArrayList 에서 찾고        // 같은 날짜의 billiardData 의 index 를 가져온다.        // '-1'이 반환되면 selectedDate 가 billiardDataArrayList 에 없다는 말이다.        // 즉, 달력에서 클릭한 날짜에는 당구 게임을 하지 않았다는 말이다.        int indexOfBilliardData = getPositionOfEqualDate(selectedDate);        // <3>        // 기준 날의 sameDateItemArrayList 과 billiardDataArrayList 로        // 해당 날짜의 게임 기록을 dialog 로 표시        // 선택된 날짜에 당구 게임을 하지 않았으면 다이어로그를 띄울 필요가 없다.        if (sameDate.getArraySize() != 0 && indexOfBilliardData > -1) {            GameRecordDialog dialog = GameRecordDialog.newInstance(                    sameDate.getSameDateItemToIndex(indexOfBilliardData),                    billiardDataArrayList            );            dialog.setStyle(                    DialogFragment.STYLE_NO_TITLE,                    android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen            );            dialog.show(                    getParentFragmentManager(),                    GameRecordDialog.class.getSimpleName()            );        }    }    /**     * [method] date 값을 billiardDataArrayList 의 getDate 값과 비교 하여 같으면 position , 다르면 -1 반환     */    private int getPositionOfEqualDate(String date) {        final String METHOD_NAME = "[getPositionOfEqualDate] ";        for (int position = 0; position < billiardDataArrayList.size(); position++) {            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "getDate : " + date + " / getDate : " + billiardDataArrayList.get(position).getDate());            if (date.equals(billiardDataArrayList.get(position).getDate())) {                // sameDateChecker 에는 date 값이 없다.                // 그런데 sameDateChecker 와 billiardDataArrayList 와 1:1 대응하고                // 같은 날짜를 찾으면 그 날짜가 기준날짜이므로                // billiardDataArrayList 의 index 에 해당하는 sameDateChecker 를 찾으면                // 그 index 의 sameDateChecker 가 기준날짜이므로                // ArrayList<SameDateItem> 을 가져오면 sameDateItem 의 index 로 billiardDataArrayList 의 billiardData 를 찾을 수 있다.                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "getDate 와 date 가 일치하는 곳이 있군요.");                return position;            } else {                continue;            } // [check 1]        } // [cycle 1]        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "getDate 와 date 가 모두 다릅니다.");        return -1;    } // End of method [getPositionOfEqualDate]    // ================================================================= EventDay List make =========================================================================    /**     * [method] DateChecker 으로 EventDay 를 만들어 ArrayList<EventDay> 로 만들어서 반환한다.     */    private ArrayList<EventDay> createEventDayArrayList(SameDate sameDate, ArrayList<BilliardData> billiardDataArrayList) {        final String METHOD_NAME = "[createEventDayArrayList] ";        ArrayList<EventDay> eventDayArrayList = new ArrayList<>();        // sameDataChecker 데이터를 토대로 EventDay 객체 생성        for (int index = 0; index < sameDate.getArraySize(); index++) {            // <1>            // 해당 sameDataIChecker 가 기준 날짜인지 확인            if (sameDate.getStandardDateToIndex(index)) {                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + ">> 기준 날짜는 ? >> " + billiardDataArrayList.get(index).getDate() + " >> position : " + index);                // 기준 날짜에 해당하는 billiardData 를 가져오고                // 이 billiardData 의 date 를 이용하여 Calendar 객체를 생성한다.                // because) EventDay 객체를 만들기 위해서 해당 date 의 Calendar 객체가 필요함!                Calendar calendar = createCalendar(                        billiardDataArrayList.get(index).getDate()                );                EventDay eventDay = createEventDay(                        sameDate.getWinCountToIndex(index),                        sameDate.getLossCountToIndex(index),                        calendar                );                eventDayArrayList.add(eventDay);            } else {                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + ">> 기준 날짜가 아닙니다 >> " + index);            } // [check 1]        } // [cycle 1]        return eventDayArrayList;    } // End of method [createEventDayArrayList]    /**     * [method] year, month, day 로 Calendar 객체 만들기     *     * @return 해당 날짜로 만들어진 Calendar 객체     */    private Calendar createCalendar(String date) {        final String METHOD_NAME = "[createCalendar] ";        Calendar calendar = null;        int[] dateItem = changeDateToIntArrayType(date);        // [check 1] : dataItem 의 length 가 3 이다.        if (dateItem.length == 3) {            // calendar 객체 가져오기 ( singleton )            calendar = Calendar.getInstance();            // calendar 객체를 해당 date 로 변경하기            // (주의) month 는 0 ~ 11 이다. 즉, 0 이 1월이고 11이 12월이다.            calendar.set(dateItem[0], dateItem[1] - 1, dateItem[2]);        } else {            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "dateItem 의 length 는 3 이어야 합니다. 왜냐면 year, month, day 만 저장되어있는 array variable 이기 때문입니다.");        }        return calendar;    } // End of method [createCalendar]    /**     * [method] winCount, lossCount 가 값이 있는지로 EventDay 의 type 을 결정     */    private EventDay createEventDay(int winCount, int lossCount, Calendar calendar) {        final String METHOD_NAME = "[makeEventDayToWinAndLoss] ";        EventDay eventDay = null;        // [check 1] : WinCount, LossCount 을 구분한다.        if ((winCount > 0) && (lossCount > 0)) {            // 승리, 패배 모두 있다.            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + ">> 기준 날짜로 Blue & Red로 설정합니다. >>");            // [lv/C]ArrayList<EventDay> : calendar 으로 EventDay 생성하여 추가 - Blue & Red            eventDay = new EventDay(calendar, DrawableUtil.getBlueOrRedCircleDrawableWithText(getContext(), "" + winCount + "  " + lossCount));        } else if ((winCount > 0) && (lossCount == 0)) {            // 승리만 있다.            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + ">> 기준 날짜로 Blue 로 설정합니다. >> ");            // List<EventDay> : 위 의 calendar 으로 EventDay 생성하여 추가 - Blue            eventDay = new EventDay(calendar, DrawableUtil.getRedCircleDrawableWithText(getContext(), "" + winCount));        } else if ((winCount == 0) && (lossCount > 0)) {            // 패배만 있다.            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + ">> 기준 날짜로 Red 로 설정합니다. >>");            // List<EventDay> : 위 의 calendar 으로 EventDay 생성하여 추가 - Blue & Red            eventDay = new EventDay(calendar, DrawableUtil.getBlueCircleDrawableWithText(getContext(), "" + lossCount));        } else {            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + ">> 승리도 패배도 없는 날인데요. >>");        } // [check 1]        return eventDay;    } // End of method [makeEventDayToWinAndLoss]    /**     * [method] date 문자열을 StringTokenizer 로 구분한 뒤, 각 문자열을 int 로 parse 한 다음 배열에 담아 반환한다.     *     * @param date 날짜 문자열     * @return 분할 된 날짜가 integer 로 변환되어 담긴 배열     */    private int[] changeDateToIntArrayType(String date) {        // example)        // '2020년 01월 01일' 형태의 date 를        // dateItem[0] = 2020        // dateItem[1] = 1        // dateItem[2] = 1        // 로 변환한다.        // [lv/i]dateTokenList : 매개변수 문자열이 분할되어 integer 로 parsing 된 다음 저장될 변수        int[] dateTokenList = new int[3];        // [lv/C]StringTokenizer : 매개변수 문자열을 '년월일 ' 로 나누기 / DATE_DELIMITER : '년월일 ' / delimiter : 구분자        StringTokenizer tokenizer = new StringTokenizer(date, DATE_DELIMITER);        // [cycle 1] : 구분된 값들이 있을 때까지 해당 값을 배열에 넣기        for (int positon = 0; tokenizer.hasMoreTokens(); positon++) {            // [lv/i]dateTokenList : 분할 된 토큰을 year, month, day 순으로 integer 로 parsing 한 값을 담는다.            dateTokenList[positon] = Integer.parseInt(tokenizer.nextToken());        } // [cycle 1]        return dateTokenList;    } // End of method [changeDateToIntArrayType]}