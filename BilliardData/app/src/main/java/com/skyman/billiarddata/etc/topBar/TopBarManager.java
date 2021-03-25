package com.skyman.billiarddata.etc.topBar;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyman.billiarddata.R;

public class TopBarManager {

    // instance variable
    private Activity activity;
    private String activityTitle;
    private LeftButtonClickListener leftButtonClickListener;
    private RightButtonClickListener rightButtonClickListener;

    // instance variable
    private ImageView leftButton;
    private TextView title;
    private TextView rightButton;

    // constructor
    private TopBarManager(Builder builder) {
        this.activity = builder.activity;
        this.activityTitle = builder.activityTitle;
    }

    public void init() {

        // connect widget
        connectWidget();

        // init widget
        initWidget();

    }

    /**
     * widget 연결하기
     */
    private void connectWidget() {

        // leftButton
        leftButton = (ImageView) activity.findViewById(R.id.layout_topBar_leftButton);

        // title
        title = (TextView) activity.findViewById(R.id.layout_topBar_title);

        // rightButton
        rightButton = (TextView) activity.findViewById(R.id.layout_topBar_rightButton);

    }

    /**
     * widget 초기 설정
     */
    private void initWidget() {

//        if (activityTitle != null) {
//            new RuntimeException("set activity's title");
//            return;
//        }

        // title
        title.setText(activityTitle);

        // leftButton
        leftButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (leftButtonClickListener != null) {

                            // leftButtonClickListener 의 onClickListener 메소드 실행
                            leftButtonClickListener.onClickListener();

                        } else {

                            activity.finish();
                        }
                    }
                }
        );

        // rightButton
        rightButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (rightButtonClickListener != null) {

                            // rightButton : GONE -> VISIBLE
                            rightButton.setVisibility(View.VISIBLE);

                            // rightButtonClickListener 의 onClickListener 메소드 실행
                            rightButtonClickListener.onClickListener();

                        }
                    }
                }
        );

    }

    // ------------------------------------ interface ------------------------------------
    public interface LeftButtonClickListener {
        void onClickListener();
    }

    public interface RightButtonClickListener {
        void onClickListener();
    }

    // ------------------------------------ Builder ------------------------------------
    public static class Builder {

        // instance variable
        private Activity activity;
        private String activityTitle;
        private LeftButtonClickListener leftButtonClickListener;
        private RightButtonClickListener rightButtonClickListener;

        // constructor
        public Builder() {
        }

        // setter
        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setActivityTitle(String activityTitle) {
            this.activityTitle = activityTitle;
            return this;
        }

        public Builder setLeftButtonClickListener(LeftButtonClickListener leftButtonClickListener) {
            this.leftButtonClickListener = leftButtonClickListener;
            return this;
        }

        public Builder setRightButtonClickListener(RightButtonClickListener rightButtonClickListener) {
            this.rightButtonClickListener = rightButtonClickListener;
            return this;
        }

        // create object
        public TopBarManager create() {
            return new TopBarManager(this);
        }
    }


}
