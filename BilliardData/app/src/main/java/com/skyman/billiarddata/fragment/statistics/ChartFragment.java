package com.skyman.billiarddata.fragment.statistics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.etc.DataFormatUtil;
import com.skyman.billiarddata.etc.statistics.MonthStatistics;
import com.skyman.billiarddata.etc.calendar.SameDate;
import com.skyman.billiarddata.listView.MonthStatisticsLvAdapter;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment implements SectionManager.Initializable {

    // constant
    private static final Display CLASS_LOG_SWITCH = Display.OFF;
    private static final String CLASS_NAME = "ChartFragment";

    // constant
    private static final String USER_DATA = "userData";
    private static final String BILLIARD_DATA_ARRAY_LIST = "billiardDataArrayList";
    private static final String SAME_DATE_CHECKER = "sameDateChecker";

    // instance variable
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDate sameDate;

    // instance variable
    private TextView myTotalGameRecord;
    private ListView monthStatistics;

    // instance variable
    private ArrayList<MonthStatistics> monthStatisticsArrayList;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(UserData userData,
                                            ArrayList<BilliardData> billiardDataArrayList,
                                            SameDate sameDate) {
        Bundle args = new Bundle();
        args.putSerializable(USER_DATA, userData);
        args.putSerializable(BILLIARD_DATA_ARRAY_LIST, billiardDataArrayList);
        args.putSerializable(SAME_DATE_CHECKER, sameDate);

        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userData = (UserData) getArguments().getSerializable(USER_DATA);
            billiardDataArrayList = (ArrayList<BilliardData>) getArguments().getSerializable(BILLIARD_DATA_ARRAY_LIST);
            sameDate = (SameDate) getArguments().getSerializable(SAME_DATE_CHECKER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
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

    @Override
    public void initAppDbManager() {

    }

    @Override
    public void connectWidget() {

        this.myTotalGameRecord = (TextView) getView().findViewById(R.id.F_chart_myTotalGameRecord);

        this.monthStatistics = (ListView) getView().findViewById(R.id.F_chart_monthStatistics);

    }

    @Override
    public void initWidget() {
        /*
        1. 모든 게임 내용이 담긴 billiardDataArrayList
         */
        if (userData != null) {
            if (!billiardDataArrayList.isEmpty()) {

                // month statistics 의 data 생성하기
                monthStatisticsArrayList = new ArrayList<>();

                createMonthStatisticsData();

                /*
                1. 월별로 정리된 통계 데이터가 담긴 monthStaticsArrayList를 ListView와 연결시킬 adapter를 생성
                2. ListView monthStatics 에 1번 adapter를 연결 -> 월별 전적 표시
                3. userData 의 승리, 패배로 총 전적 표시
                 */
                // ListView adapter 생성
                MonthStatisticsLvAdapter adapter = new MonthStatisticsLvAdapter(reverseOrder(monthStatisticsArrayList));

                // ListView : monthStatistics
                this.monthStatistics.setAdapter(adapter);

                // TextView : myTotalGameRecord
                this.myTotalGameRecord.setText(
                        DataFormatUtil.formatOfGameRecord(
                                userData.getGameRecordWin(),
                                userData.getGameRecordLoss()
                        )
                );

            }
        }

    }


    /**
     * SameDateChecker 을 통해서 같은 year, month 를 구분하여 그 year, month 의 winCount, lossCount 를 구한다.
     */
    private void createMonthStatisticsData() {

        for (int index = 0; index < sameDate.getArraySize(); index++) {

            // sameDateChecker 의 첫번째 index 는 무조건 standardDate 이므로 추가한다.
            if (monthStatisticsArrayList.size() == 0) {

                MonthStatistics monthStatistics = new MonthStatistics();
                monthStatistics.setYear(sameDate.getYearToIndex(index));
                monthStatistics.setMonth(sameDate.getMonthToIndex(index));
                monthStatistics.setWinCount(sameDate.getWinCountToIndex(index));
                monthStatistics.setLossCount(sameDate.getLossCountToIndex(index));
                monthStatistics.setBilliardDataArrayList(getBilliardDataArrayListToSameDateItemArrayList(sameDate.getSameDateItemToIndex(index)));

                monthStatisticsArrayList.add(monthStatistics);
                continue;
            }

            // sameDateChecker 의 index 번째가 standardDate 이면
            // monthStatisticsDataArrayList 에서 같은 year, month 가 있는지 검사한다.
            if (sameDate.getStandardDateToIndex(index)) {

                boolean isSame = false;

                for (int innerIndex = 0; innerIndex < monthStatisticsArrayList.size(); innerIndex++) {

                    // sameDateChecker 의 데이터가 monthStatisticsDateArrayList 에서 같은 year, month 가 발견되면
                    // monthStatisticsDateArrayList 의 winCount, lossCount 에 sameDateChecker 의 winCount, lossCount 값을 더한다.
                    if (sameDate.getYearToIndex(index) == monthStatisticsArrayList.get(innerIndex).getYear()
                            && sameDate.getMonthToIndex(index) == monthStatisticsArrayList.get(innerIndex).getMonth()) {

                        // year, month 가 같은 날이 있으면
                        // winCount, lossCount 를 기존의 값에 더한다.
                        int winCount = monthStatisticsArrayList.get(innerIndex).getWinCount() + sameDate.getWinCountToIndex(index);
                        int lossCount = monthStatisticsArrayList.get(innerIndex).getLossCount() + sameDate.getLossCountToIndex(index);

                        monthStatisticsArrayList.get(innerIndex).setWinCount(winCount);
                        monthStatisticsArrayList.get(innerIndex).setLossCount(lossCount);
                        monthStatisticsArrayList.get(innerIndex).setBilliardDataArrayList(getBilliardDataArrayListToSameDateItemArrayList(sameDate.getSameDateItemToIndex(index)));

                        isSame = true;

                    }

                }

                // 만약 위의 for 구문에서 같은 year, month 를 찾지 못 하면
                // MonthStatisticsData 를 생성하여 monthStatisticsDataArrayList 에 추가한다.
                if (!isSame) {

                    MonthStatistics monthStatistics = new MonthStatistics();
                    monthStatistics.setYear(sameDate.getYearToIndex(index));
                    monthStatistics.setMonth(sameDate.getMonthToIndex(index));
                    monthStatistics.setWinCount(sameDate.getWinCountToIndex(index));
                    monthStatistics.setLossCount(sameDate.getLossCountToIndex(index));
                    monthStatistics.setBilliardDataArrayList(getBilliardDataArrayListToSameDateItemArrayList(sameDate.getSameDateItemToIndex(index)));

                    monthStatisticsArrayList.add(monthStatistics);

                }
            }
        }

    }

    private ArrayList<BilliardData> getBilliardDataArrayListToSameDateItemArrayList(ArrayList<SameDate.SameDateItem> sameDateItemArrayList) {

        ArrayList<BilliardData> billiardDataArrayListOfMonth = new ArrayList<>();

        for (int index = 0; index < sameDateItemArrayList.size(); index++) {

            billiardDataArrayListOfMonth.add(
                    this.billiardDataArrayList.get(sameDateItemArrayList.get(index).getIndex())
            );

        }

        return billiardDataArrayListOfMonth;
    }


    /**
     * monthStaticsArrayList를 역순으로 바꾼다.
     */
    private ArrayList<MonthStatistics> reverseOrder(ArrayList<MonthStatistics> monthStatisticsArrayList) {
        ArrayList<MonthStatistics> reverseOrderArrayList = new ArrayList<>();

        for (int index = monthStatisticsArrayList.size() - 1; index >= 0; index--) {
            reverseOrderArrayList.add(monthStatisticsArrayList.get(index));
        }

        printLog(reverseOrderArrayList);
        return reverseOrderArrayList;
    }

    /**
     * MonthStatisticsData 를 로그로 출력한다.
     */
    private void printLog(ArrayList<MonthStatistics> monthStatisticsArrayList) {
        if (DeveloperManager.PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON) {
                Log.d(CLASS_NAME, "[monthStatisticsDataArrayList 내용 확인]");

                for (int index = 0; index < monthStatisticsArrayList.size(); index++) {

                    Log.d(CLASS_NAME, "---- " + index + "번째 ----");
                    Log.d(CLASS_NAME, "year : " + monthStatisticsArrayList.get(index).getYear());
                    Log.d(CLASS_NAME, "month : " + monthStatisticsArrayList.get(index).getMonth());
                    Log.d(CLASS_NAME, "winCount : " + monthStatisticsArrayList.get(index).getWinCount());
                    Log.d(CLASS_NAME, "lossCount : " + monthStatisticsArrayList.get(index).getLossCount());

                    StringBuilder billiardCount = new StringBuilder();
                    billiardCount.append("billiarData / count : ");
                    for (int innerIndex = 0; innerIndex < monthStatisticsArrayList.get(index).getBilliardDataArrayList().size(); innerIndex++) {

                        billiardCount.append("[" + monthStatisticsArrayList.get(index).getBilliardDataArrayList().get(innerIndex).getCount() + "]");
                    }
                    Log.d(CLASS_NAME, billiardCount.toString());
                }
            }

    }

}