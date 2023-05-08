package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.skyman.billiarddata.R;
import com.skyman.billiarddata.StatisticsManagerActivity;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.etc.calendar.SameDate;

import java.util.ArrayList;

public class GameRecordDialog extends DialogFragment implements SectionManager.Initializable {

    // constant
    private static final Display CLASS_LOG_SWITCH = Display.OFF;
    private static final String CLASS_NAME = "GameRecordDialog";

    // constant
    private static final String SAME_DATE_ITEM_ARRAY_LIST = "sameDateItemArrayList";
    private static final String BILLIARD_DATA_ARRAY_LIST = "billiardDataArrayList";

    // instance variable
    private ArrayList<SameDate.SameDateItem> sameDateItemArrayList;
    private ArrayList<BilliardData> billiardDataArrayList;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable
    private ImageView close;
    private LinearLayout contentWrapper;
    private TextView date;
    private TextView gameMode;
    private TextView playTime;
    private TextView playerList;
    private TextView winnerName;
    private TextView score;
    private TextView cost;

    // constructor
    private GameRecordDialog() {
    }

    public static GameRecordDialog newInstance(ArrayList<SameDate.SameDateItem> sameDateItemArrayList,
                                               ArrayList<BilliardData> billiardDataArrayList) {
        Bundle args = new Bundle();
        args.putSerializable(SAME_DATE_ITEM_ARRAY_LIST, sameDateItemArrayList);
        args.putSerializable(BILLIARD_DATA_ARRAY_LIST, billiardDataArrayList);

        GameRecordDialog dialog = new GameRecordDialog();
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sameDateItemArrayList = (ArrayList<SameDate.SameDateItem>) getArguments().getSerializable(SAME_DATE_ITEM_ARRAY_LIST);
            billiardDataArrayList = (ArrayList<BilliardData>) getArguments().getSerializable(BILLIARD_DATA_ARRAY_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_game_record, container, false);
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void initAppDbManager() {

        appDbManager = ((StatisticsManagerActivity) getActivity()).getAppDbManager();

    }

    @Override
    public void connectWidget() {

        // [iv/C]Button : close mapping
        this.close = (ImageView) getView().findViewById(R.id.d_gameRecord_button_close);

        // [iv/C]HorizontalScrollView : winOfLoss mapping
        this.contentWrapper = (LinearLayout) getView().findViewById(R.id.d_gameRecord_win_or_loss);

        // [iv/C]TextView : date mapping
        this.date = (TextView) getView().findViewById(R.id.d_gameRecord_date);

        // [iv/C]TextView : speciality mapping
        this.gameMode = (TextView) getView().findViewById(R.id.d_gameRecord_speciality);

        this.playerList = (TextView) getView().findViewById(R.id.d_gameRecord_playerList);

        // [iv/C]TextView : winner mapping
        this.winnerName = (TextView) getView().findViewById(R.id.d_gameRecord_winner);

        // [iv/C]TextView : score mapping
        this.score = (TextView) getView().findViewById(R.id.d_gameRecord_score);

        // [iv/C]TextView : playTime mapping
        this.playTime = (TextView) getView().findViewById(R.id.d_gameRecord_playTime);

        // [iv/C]TextView : cost mapping
        this.cost = (TextView) getView().findViewById(R.id.d_gameRecord_cost);


    }

    @Override
    public void initWidget() {

        // close 버튼 : click listener
        this.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // [check 1] : dateChecker 의 size 가 0 보다 크다.
        if (!sameDateItemArrayList.isEmpty()) {

            // 버튼 객체 Layout parameter : width, height, margin
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);

            // [cycle 1] : dataChecker 의 getArraySize 만큼 순환하며
            for (int index = 0; index < sameDateItemArrayList.size(); index++) {

                // sameDateItemArrayList 에 1:1 대응되는 버튼 객체 생성
                MaterialButton billiardInfo = new MaterialButton(getContext());

                // 버튼의 설정
                billiardInfo.setLayoutParams(params);
                billiardInfo.setId(100 + index);
                billiardInfo.setTextColor(Color.parseColor("#FFFFFF"));

                // sameDateItem 의 승리여부(=isWinner) 를 판별하여
                // 버튼의 backgroundColor 및 text 를 설정한다.
                if (sameDateItemArrayList.get(index).isWinner()) {

                    // 승리
                    billiardInfo.setText("승리");
                    billiardInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorRed));

                } else {

                    // 패배
                    billiardInfo.setText("패배");
                    billiardInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBlue));

                } // [check 2]

                // sameDataItem 의 index 가 billiardDataArrayList 의 index 값이다.
                int indexOfBilliardDataArrayList = sameDateItemArrayList.get(index).getIndex();

                // click listener
                billiardInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 버튼을 클릭했을 때
                        // sameDateItem 의 내용을 화면에 표시한다.
                        date.setText(billiardDataArrayList.get(indexOfBilliardDataArrayList).getDate());                   // 1. date
                        gameMode.setText(billiardDataArrayList.get(indexOfBilliardDataArrayList).getGameMode());           // 2. game mode
                        winnerName.setText(billiardDataArrayList.get(indexOfBilliardDataArrayList).getWinnerName());       // 5. winner name
                        playTime.setText(billiardDataArrayList.get(indexOfBilliardDataArrayList).getPlayTime() + "분");      // 6. play time
//                        score.setText(billiardDataArrayList.get(indexOfBilliardDataArrayList).getScore() + "");             // 7. score
                        cost.setText(billiardDataArrayList.get(indexOfBilliardDataArrayList).getCost() + "원");                // 8. cost

                        appDbManager.requestPlayerQuery(
                                new AppDbManager.PlayerQueryRequestListener() {
                                    @Override
                                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

                                        ArrayList<PlayerData> playerDataArrayList = playerDbManager2.loadAllContentByBilliardCount(billiardDataArrayList.get(indexOfBilliardDataArrayList).getCount());

                                        playerList.setText(
                                                getPlayerList(playerDataArrayList)
                                        );

                                        score.setText(
                                                getFormatOfScore(
                                                        billiardDataArrayList.get(indexOfBilliardDataArrayList).getScore(),
                                                        playerDataArrayList
                                                )
                                        );
                                        DeveloperManager.printLogPlayerData(CLASS_LOG_SWITCH,CLASS_NAME, playerDataArrayList);
                                    }
                                }
                        );

                    }
                });

                // 위에서 생성한 버튼을
                // contentWrapper 에 추가하여 화면에 표시하기
                this.contentWrapper.addView(billiardInfo);

            } // [cycle 1]

        } else {

        } // [check 1]

    }


    private String getPlayerList(ArrayList<PlayerData> playerDataArrayList) {

        StringBuilder list = new StringBuilder();

        for (int index = 0; index < playerDataArrayList.size(); index++) {

            list.append(playerDataArrayList.get(index).getPlayerName());
            list.append("(");
            list.append(playerDataArrayList.get(index).getTargetScore());
            list.append(")");

            if (!(index == (playerDataArrayList.size() - 1))) {
                list.append(", ");
            }
        }

        return list.toString();
    }


    private String getFormatOfScore(String score, ArrayList<PlayerData> playerDataArrayList) {

        StringBuilder format = new StringBuilder();

        format.append(score);

        format.append(" ( ");
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            format.append(playerDataArrayList.get(index).getPlayerName());

            if (!(index == (playerDataArrayList.size() - 1))) {
                format.append(" : ");
            }
        }
        format.append(" )");

        return format.toString();
    }


}
