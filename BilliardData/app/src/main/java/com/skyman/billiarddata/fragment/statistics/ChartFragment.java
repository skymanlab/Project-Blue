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
import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.etc.calendar.SameDateGame;
import com.skyman.billiarddata.listView.ByMonthStatsLvAdapter;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.etc.statistics.MonthStatistics;
import com.skyman.billiarddata.etc.calendar.SameDate;
import com.skyman.billiarddata.table.user.data.UserData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.ON;
    private static final String CLASS_NAME = "ChartFragment";

    // constant
    private static final String USER_DATA = "userData";
    private static final String BILLIARD_DATA_LIST = "billiardDataList";
    private static final String ALL_GAME = "allGame";
    private static final String SAME_YEAR_GAME_LIST = "sameYearGameList";
    private static final String SAME_MONTH_GAME_LIST = "sameMonthGameList";



    // instance variable
    private UserData userData;
    private List<BilliardData> billiardDataList;
    private SameDateGame allGame ;
    private Map<String, SameDateGame> sameYearGameList;
    private Map<String, SameDateGame> sameMonthGameList;


    // instance variable
    private TextView recordOfAllGame;
    private ListView byMonthStats;



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
                                            List<BilliardData> billiardDataList,
                                            SameDateGame allGame,
                                            Map<String, SameDateGame> sameYearGameList,
                                            Map<String, SameDateGame> sameMonthGameList) {
        Bundle args = new Bundle();
        args.putSerializable(USER_DATA, userData);
        args.putSerializable(BILLIARD_DATA_LIST, (Serializable) billiardDataList);
        args.putSerializable(ALL_GAME, allGame);
        args.putSerializable(SAME_YEAR_GAME_LIST, (Serializable) sameYearGameList);
        args.putSerializable(SAME_MONTH_GAME_LIST, (Serializable) sameMonthGameList);

        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userData = (UserData) getArguments().getSerializable(USER_DATA);
            billiardDataList = (ArrayList<BilliardData>) getArguments().getSerializable(BILLIARD_DATA_LIST);
            allGame = (SameDateGame) getArguments().getSerializable(ALL_GAME);
            sameYearGameList = (LinkedHashMap<String, SameDateGame>) getArguments().getSerializable(SAME_YEAR_GAME_LIST);
            sameMonthGameList = (LinkedHashMap<String, SameDateGame>) getArguments().getSerializable(SAME_MONTH_GAME_LIST);
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

        // widget : connect -> init
        connectWidget();
        initWidget();

    }

    @Override
    public void initAppDbManager() {

    }

    @Override
    public void connectWidget() {

        this.recordOfAllGame = (TextView) getView().findViewById(R.id.F_chart_recordOfAllGame);

        this.byMonthStats = (ListView) getView().findViewById(R.id.F_chart_byMonthStats);

    }

    @Override
    public void initWidget() {

        // <1> 등록된 유저가 있는 지 확인
        if (userData != null) {

            // <2> 등록된 게임이 있는 지 확인
            if (!billiardDataList.isEmpty()) {
                /*
                1. 월별로 정리된 통계 데이터가 담긴 monthStaticsArrayList를 ListView와 연결시킬 adapter를 생성
                2. ListView monthStatics 에 1번 adapter를 연결 -> 월별 전적 표시
                3. userData 의 승리, 패배로 총 전적 표시
                 */

                // sameMonthGameList 를 날짜의 역순으로 정렬하고 이를 ArrayList 형태로 변환하여
                // ByMonthStatsLvAdapter 로 전달
                ByMonthStatsLvAdapter adapter = new ByMonthStatsLvAdapter(reverse(sameMonthGameList));
                this.byMonthStats.setAdapter(adapter);

                // allGame 의 record.toString()으로 문자열 셋팅
                this.recordOfAllGame.setText(
                        allGame.getRecord().toString()
                );
            }
        }
    }

    private ArrayList<SameDateGame> reverse(Map<String, SameDateGame> sameDateGameList) {
        ArrayList<SameDateGame> arrayList = new ArrayList<>();

        // key set 가져오기
        ArrayList<String> keySet = new ArrayList<>(sameDateGameList.keySet());
        // 날짜 역순으로 정렬
        Collections.reverse(keySet);
        // ListIterator -> String(날짜)
        ListIterator<String> iterator = keySet.listIterator();

        // ListIterator 순환하며 가져온 날짜를 통해
        while (iterator.hasNext()) {
            String date = iterator.next();
            arrayList.add(sameDateGameList.get(date));
        }

        return arrayList;
    }
}