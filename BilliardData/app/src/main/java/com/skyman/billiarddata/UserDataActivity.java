package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skyman.billiarddata.factivity.UserPagerAdapter;

public class UserDataActivity extends AppCompatActivity {

    // faragment pager 객체 생성
    ViewPager pager;

    // activity widget 객체 생성
    Button userInputData ;
    Button userInfo;
    Button userFriend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        // pager setting
        pager = (ViewPager) findViewById(R.id.userdata_pager);
        pager.setAdapter(new UserPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);
        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int)v.getTag();
                pager.setCurrentItem(tag);
            }
        };

        // userInputData button setting
        userInputData = (Button) findViewById(R.id.userdata_user_input_data);
        userInputData.setOnClickListener(movePageListener);
        userInputData.setTag(0);

        // userInfo button setting
        userInfo = (Button) findViewById(R.id.userdata_user_info);
        userInfo.setOnClickListener(movePageListener);
        userInfo.setTag(1);

        // userFriend button setting
        userFriend = (Button) findViewById(R.id.userdata_user_friend);
        userFriend.setOnClickListener(movePageListener);
        userFriend.setTag(2);
    }
}