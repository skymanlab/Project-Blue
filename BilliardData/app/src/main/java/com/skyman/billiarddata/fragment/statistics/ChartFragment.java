package com.skyman.billiarddata.fragment.statistics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;
import com.skyman.billiarddata.management.statistics.MonthStatisticsData;
import com.skyman.billiarddata.management.statistics.SameDateChecker;
import com.skyman.billiarddata.management.statistics.ListView.MonthStatisticsLvAdapter;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment implements SectionManager.Initializable {

    // constant
    private static final String CLASS_NAME = ChartFragment.class.getSimpleName();

    // constant
    private static final String USER_DATA = "userData";
    private static final String BILLIARD_DATA_ARRAY_LIST = "billiardDataArrayList";
    private static final String SAME_DATE_CHECKER = "sameDateChecker";

    // instance variable
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private SameDateChecker sameDateChecker;

    // instance variable
    private TextView myTotalGameRecord;
    private ListView monthStatistics;

    // instance variable
    private ArrayList<MonthStatisticsData> monthStatisticsDataArrayList;

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
                                            SameDateChecker sameDateChecker) {
        Bundle args = new Bundle();
        args.putSerializable(USER_DATA, userData);
        args.putSerializable(BILLIARD_DATA_ARRAY_LIST, billiardDataArrayList);
        args.putSerializable(SAME_DATE_CHECKER, sameDateChecker);

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
            sameDateChecker = (SameDateChecker) getArguments().getSerializable(SAME_DATE_CHECKER);
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

        DeveloperManager.displayLog(
                CLASS_NAME,
                "=========>>>>>>>>>>>>>>>>>>========================================>>>>>>>>>>>>>>>>>>>>>>>>>> Chart fragment"
        );

        if (userData != null) {
            if (!billiardDataArrayList.isEmpty()) {

                // month statistics 의 data 생성하기
                monthStatisticsDataArrayList = new ArrayList<>();

                createMonthStatisticsData();

                // ListView adapter 생성
                MonthStatisticsLvAdapter adapter = new MonthStatisticsLvAdapter(monthStatisticsDataArrayList);

                // ListView : monthStatistics
                this.monthStatistics.setAdapter(adapter);

                // TextView : myTotalGameRecord
                this.myTotalGameRecord.setText(
                        ProjectBlueDataFormatter.getFormatOfGameRecord(
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

        for (int index = 0; index < sameDateChecker.getArraySize(); index++) {

            // sameDateChecker 의 첫번째 index 는 무조건 standardDate 이므로 추가한다.
            if (monthStatisticsDataArrayList.size() == 0) {

                MonthStatisticsData monthStatisticsData = new MonthStatisticsData();
                monthStatisticsData.setYear(sameDateChecker.getYearToIndex(index));
                monthStatisticsData.setMonth(sameDateChecker.getMonthToIndex(index));
                monthStatisticsData.setWinCount(sameDateChecker.getWinCountToIndex(index));
                monthStatisticsData.setLossCount(sameDateChecker.getLossCountToIndex(index));
                monthStatisticsData.setBilliardDataArrayList(getBilliardDataArrayListToSameDateItemArrayList(sameDateChecker.getSameDateItemToIndex(index)));

                monthStatisticsDataArrayList.add(monthStatisticsData);
                continue;
            }

            // sameDateChecker 의 index 번째가 standardDate 이면
            // monthStatisticsDataArrayList 에서 같은 year, month 가 있는지 검사한다.
            if (sameDateChecker.getStandardDateToIndex(index)) {

                boolean isSame = false;

                for (int innerIndex = 0; innerIndex < monthStatisticsDataArrayList.size(); innerIndex++) {

                    // sameDateChecker 의 데이터가 monthStatisticsDateArrayList 에서 같은 year, month 가 발견되면
                    // monthStatisticsDateArrayList 의 winCount, lossCount 에 sameDateChecker 의 winCount, lossCount 값을 더한다.
                    if (sameDateChecker.getYearToIndex(index) == monthStatisticsDataArrayList.get(innerIndex).getYear()
                            && sameDateChecker.getMonthToIndex(index) == monthStatisticsDataArrayList.get(innerIndex).getMonth()) {

                        // year, month 가 같은 날이 있으면
                        // winCount, lossCount 를 기존의 값에 더한다.
                        int winCount = monthStatisticsDataArrayList.get(innerIndex).getWinCount() + sameDateChecker.getWinCountToIndex(index);
                        int lossCount = monthStatisticsDataArrayList.get(innerIndex).getLossCount() + sameDateChecker.getLossCountToIndex(index);

                        monthStatisticsDataArrayList.get(innerIndex).setWinCount(winCount);
                        monthStatisticsDataArrayList.get(innerIndex).setLossCount(lossCount);
                        monthStatisticsDataArrayList.get(innerIndex).setBilliardDataArrayList(getBilliardDataArrayListToSameDateItemArrayList(sameDateChecker.getSameDateItemToIndex(index)));

                        isSame = true;

                    }

                }

                // 만약 위의 for 구문에서 같은 year, month 를 찾지 못 하면
                // MonthStatisticsData 를 생성하여 monthStatisticsDataArrayList 에 추가한다.
                if (!isSame) {

                    MonthStatisticsData monthStatisticsData = new MonthStatisticsData();
                    monthStatisticsData.setYear(sameDateChecker.getYearToIndex(index));
                    monthStatisticsData.setMonth(sameDateChecker.getMonthToIndex(index));
                    monthStatisticsData.setWinCount(sameDateChecker.getWinCountToIndex(index));
                    monthStatisticsData.setLossCount(sameDateChecker.getLossCountToIndex(index));
                    monthStatisticsData.setBilliardDataArrayList(getBilliardDataArrayListToSameDateItemArrayList(sameDateChecker.getSameDateItemToIndex(index)));

                    monthStatisticsDataArrayList.add(monthStatisticsData);

                }
            }
        }

    }

    private ArrayList<BilliardData> getBilliardDataArrayListToSameDateItemArrayList(ArrayList<SameDateChecker.SameDateItem> sameDateItemArrayList) {

        ArrayList<BilliardData> billiardDataArrayListOfMonth = new ArrayList<>();

        for (int index = 0; index < sameDateItemArrayList.size(); index++) {

            billiardDataArrayListOfMonth.add(
                    this.billiardDataArrayList.get(sameDateItemArrayList.get(index).getIndex())
            );

        }

        return billiardDataArrayListOfMonth;
    }

    /**
     * MonthStatisticsData 를 로그로 출력한다.
     */
    private void printMonthStatisticsData() {

        DeveloperManager.displayLog(
                CLASS_NAME,
                "================================= monthStatisticsDataArrayList - start ================================="
        );

        for (int index = 0; index < monthStatisticsDataArrayList.size(); index++) {

            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "================================= " + index + " ================================="
            );

            // year
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "year : " + monthStatisticsDataArrayList.get(index).getYear()
            );

            // month
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "month : " + monthStatisticsDataArrayList.get(index).getMonth()
            );


            // win count
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "win count : " + monthStatisticsDataArrayList.get(index).getWinCount()
            );


            // loss count
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "loss count : " + monthStatisticsDataArrayList.get(index).getLossCount()
            );

            for (int innerIndex = 0; innerIndex < monthStatisticsDataArrayList.get(index).getBilliardDataArrayList().size(); innerIndex++) {

                DeveloperManager.displayLog(
                        CLASS_NAME,
                        "< " + index + " > 의 << " + innerIndex + " >> billiard count : " + monthStatisticsDataArrayList.get(index).getBilliardDataArrayList().get(innerIndex).getCount()
                );


            }
        }

        DeveloperManager.displayLog(
                CLASS_NAME,
                "================================= monthStatisticsDataArrayList - end ================================="
        );
    }

}