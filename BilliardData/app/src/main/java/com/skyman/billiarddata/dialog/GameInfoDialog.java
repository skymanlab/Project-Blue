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
import com.skyman.billiarddata.StatsActivity;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.SectionManager;
import com.skyman.billiarddata.etc.calendar.SameDateGame;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Point;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;
import com.skyman.billiarddata.etc.game.Score;
import com.skyman.billiarddata.etc.game.Time;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;

import java.util.ArrayList;
import java.util.List;

public class GameInfoDialog extends DialogFragment implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "GameInfoDialog";

    // constant
    private static final String SAME_DATE_GAME = "sameDateGame";
    private static final String BILLIARD_DATA_LIST = "billiardDataList";

    // instance variable
    private SameDateGame sameDateGame;
    private ArrayList<BilliardData> billiardDataList;

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
    private GameInfoDialog() {
    }

    public static GameInfoDialog newInstance(SameDateGame sameDateGame, ArrayList<BilliardData> billiardDataList) {
        Bundle args = new Bundle();
        args.putSerializable(SAME_DATE_GAME, sameDateGame);
        args.putSerializable(BILLIARD_DATA_LIST, billiardDataList);

        GameInfoDialog dialog = new GameInfoDialog();
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sameDateGame = (SameDateGame) getArguments().getSerializable(SAME_DATE_GAME);
            billiardDataList = (ArrayList<BilliardData>) getArguments().getSerializable(BILLIARD_DATA_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_game_info, container, false);
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

        appDbManager = ((StatsActivity) getActivity()).getAppDbManager();

    }

    @Override
    public void connectWidget() {

        // [iv/C]Button : close mapping
        this.close = (ImageView) getView().findViewById(R.id.d_gameInfo_button_close);

        // [iv/C]HorizontalScrollView : winOfLoss mapping
        this.contentWrapper = (LinearLayout) getView().findViewById(R.id.d_gameInfo_win_or_loss);

        // [iv/C]TextView : date mapping
        this.date = (TextView) getView().findViewById(R.id.d_gameInfo_date);

        // [iv/C]TextView : speciality mapping
        this.gameMode = (TextView) getView().findViewById(R.id.d_gameInfo_speciality);

        this.playerList = (TextView) getView().findViewById(R.id.d_gameInfo_playerList);

        // [iv/C]TextView : winner mapping
        this.winnerName = (TextView) getView().findViewById(R.id.d_gameInfo_winner);

        // [iv/C]TextView : score mapping
        this.score = (TextView) getView().findViewById(R.id.d_gameInfo_score);

        // [iv/C]TextView : playTime mapping
        this.playTime = (TextView) getView().findViewById(R.id.d_gameInfo_playTime);

        // [iv/C]TextView : cost mapping
        this.cost = (TextView) getView().findViewById(R.id.d_gameInfo_cost);


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

        if (sameDateGame != null) {

            // 버튼 객체 Layout parameter : width, height, margin
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);

            List<Reference> referenceList = sameDateGame.getReferenceList();

            // [cycle 1] : dataChecker 의 getArraySize 만큼 순환하며
            for (int index = 0; index < referenceList.size(); index++) {

                // sameDateItemArrayList 에 1:1 대응되는 버튼 객체 생성
                MaterialButton billiardInfo = new MaterialButton(getContext());

                // 버튼의 설정
                billiardInfo.setLayoutParams(params);
                billiardInfo.setId(100 + index);
                billiardInfo.setTextColor(Color.parseColor("#FFFFFF"));

                // sameDateItem 의 승리여부(=isWinner) 를 판별하여
                // 버튼의 backgroundColor 및 text 를 설정한다.
                if (sameDateGame.getRecordTypeList().get(index).equals(Record.Type.WIN)) {

                    // 승리
                    billiardInfo.setText("승리");
                    billiardInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorRed));

                } else {

                    // 패배
                    billiardInfo.setText("패배");
                    billiardInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBlue));

                } // [check 2]

                // sameDataItem 의 index 가 billiardDataArrayList 의 index 값이다.
                int bIndex = referenceList.get(index).getIndex();
                int countOfBilliard = referenceList.get(index).getCount();

                // click listener
                billiardInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 버튼을 클릭했을 때
                        // sameDateItem 의 내용을 화면에 표시한다.
                        date.setText(billiardDataList.get(bIndex).getDate());                               // 1. date
                        gameMode.setText(billiardDataList.get(bIndex).getGameMode());                       // 2. game mode
                        winnerName.setText(billiardDataList.get(bIndex).getWinnerName());                   // 5. winner name
                        playTime.setText(new Time(billiardDataList.get(bIndex).getPlayTime()).toString());  // 6. play time
                        cost.setText(new Cost(billiardDataList.get(bIndex).getCost()).toString());                // 8. cost

                        appDbManager.requestPlayerQuery(
                                new AppDbManager.PlayerQueryRequestListener() {
                                    @Override
                                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

                                        ArrayList<PlayerData> playerDataList = playerDbManager2.loadAllContentByBilliardCount(countOfBilliard);

                                        Score score1 = new Score();

                                        for (int index = 0; index < playerDataList.size(); index++) {
                                            score1.getPointList().add(
                                                    new Point(
                                                            playerDataList.get(index).getPlayerName(),
                                                            playerDataList.get(index).getTargetScore(),
                                                            playerDataList.get(index).getScore()
                                                    )
                                            );
                                        }

                                        playerList.setText(
                                                score1.toPlayersString()
                                        );

                                        score.setText(
                                                score1.toScoreString()
                                        );
                                    }
                                }
                        );

                    }
                });

                // 위에서 생성한 버튼을
                // contentWrapper 에 추가하여 화면에 표시하기
                this.contentWrapper.addView(billiardInfo);
            }
        }
    }
}
