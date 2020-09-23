package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skyman.billiarddata.management.projectblue.ProjectBlueDatabaseManager;

public class MainActivity extends AppCompatActivity {

    /*
    * 용어 정리
    * 1. declaration = 선언
    * 2. create = 생성성
    * 3. colon = :
   * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button : object declaration
        Button billiardInput = (Button) findViewById(R.id.main_bt_billiard_input);
        Button billiardDisplay = (Button) findViewById(R.id.main_bt_billiard_display);
        Button userManager = (Button) findViewById(R.id.main_bt_user_manager);

        // Button 1 : inputData setting - '데이터 입력'
        billiardInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BilliardInputActivity.class);
                startActivity(intent);
            }
        });

        // Button 2 : viewDAta setting - '데이터 보기'
        billiardDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                startActivity(intent);
            }
        });

        // Button 3 : userManager setting - '나의 정보'
        userManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserManagerActivity.class);
                startActivity(intent);
            }
        });

        /*
        * 1. 유저 데이터가 있는지 확인
        * 2. 없으면 일단 user 기본 정보 입력하는 activity 로 이동 */

        // ProjectBlueDatabaseManager : projectBlueDatabaseManager setting - project_blue.db 를 생성하고 필요한 모든 테이블 생성하기
        ProjectBlueDatabaseManager projectBlueDatabaseManager = new ProjectBlueDatabaseManager(this);
        projectBlueDatabaseManager.init_db();
    }
}