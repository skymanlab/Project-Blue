package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button inputData = (Button) findViewById(R.id.main_inputData);
        Button viewData = (Button) findViewById(R.id.main_viewdata);
        Button userData = (Button) findViewById(R.id.main_userData);

        /* inputData button setting */
        inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);
                startActivity(intent);
            }
        });

        /* viewData button setting*/
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                startActivity(intent);
            }
        });

        /* userData button setting */
        userData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserManageActivity.class);
                startActivity(intent);
            }
        });

        /*
        * 1. 유저 데이터가 있는지 확인
        * 2. 없으면 일단 user 기본 정보 입력하는 activity 로 이동 */


    }
}