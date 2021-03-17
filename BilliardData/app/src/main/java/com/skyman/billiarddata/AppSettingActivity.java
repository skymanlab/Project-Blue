package com.skyman.billiarddata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.file.FileConstants;
import com.skyman.billiarddata.management.file.FileExport;
import com.skyman.billiarddata.management.file.FileImport;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class AppSettingActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME_LOG = AppSettingActivity.class.getSimpleName();

    // instance variable
    private LinearLayout dataExportWrapper;
    private LinearLayout dataImportWrapper;

    // instance variable
    private UserDbManager userDbManager;
    private BilliardDbManager billiardDbManager;
    private PlayerDbManager playerDbManager;
    private FriendDbManager friendDbManager;

    // instance variable
    private FileExport fileExport = null;
    private FileImport fileImport = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        // create db manager
        createDBManager();

        // LinearLayout : dataExport
        dataExportWrapper = (LinearLayout) findViewById(R.id.app_setting_data_export_wrapper);
        dataExportWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // '파일 내보내기' 를 하기 위한 객체를 생성하고 초기 설정을 한다.
                        fileExport = new FileExport(
                                AppSettingActivity.this,
                                userDbManager,
                                billiardDbManager,
                                playerDbManager,
                                friendDbManager);
                        fileExport.init();

                    }
                }
        );

        // LinearLayout : dataImport
        dataImportWrapper = (LinearLayout) findViewById(R.id.app_setting_data_import_wrapper);
        dataImportWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // '파일 가져오기' 를 하기 위한 객체를 생성하고 초기 설정을 한다.
                        fileImport = new FileImport(
                                AppSettingActivity.this,
                                userDbManager,
                                billiardDbManager,
                                playerDbManager,
                                friendDbManager
                        );
                        fileImport.init();
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FileConstants.REQUEST_CODE_CREATE_FILE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;

            if (data != null) {

                // 파일이 만들어진 위치의 Uri 를 가져오기
                uri = data.getData();
                DeveloperManager.displayLog(
                        CLASS_NAME_LOG,
                        "==========================================>>>>>>>>>>>>>>>>>>> 내보내기"
                );

                DeveloperManager.displayLog(
                        CLASS_NAME_LOG,
                        "uri : " + uri.toString()
                );

                // FileExport 의 객체가 생성되었을 때
                // writeJsonObject() 메소드를 사용하여 해당 위치의 파일에
                // 데이터베이스의 모든 내용이 담긴 JsonObject 를 파일에 쓴다.
                if (fileExport != null) {
                    fileExport.writeJsonObject(uri);
                }

            }
        } else if (requestCode == FileConstants.REQUEST_CODE_OPEN_FILE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;

            if (data != null) {
                uri = data.getData();
                DeveloperManager.displayLog(
                        CLASS_NAME_LOG,
                        "==========================================>>>>>>>>>>>>>>>>>>> 가져오기 "
                );

                DeveloperManager.displayLog(
                        CLASS_NAME_LOG,
                        "uri : " + uri.toString()
                );

                if (fileImport != null) {
                    fileImport.saveDatabase(uri);
                }

            }

        }


    }

    @Override
    protected void onDestroy() {
        final String METHOD_NAME = "[onDestroy] ";
        super.onDestroy();

        // user
        if (this.userDbManager != null) {
            this.userDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 메니저가 생성되지 않았습니다.");
        }


        // friend
        if (this.friendDbManager != null) {
            this.friendDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friend 메니저가 생성되지 않았습니다.");
        }


        // Billiard
        if (this.billiardDbManager != null) {
            this.billiardDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 메니저가 생성되지 않았습니다.");
        }

        // player
        if (this.playerDbManager != null) {
            this.playerDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 메니저가 생성되지 않았습니다.");
        }

    }

    /**
     * [method] user, friend 테이블을 관리하는 메니저를 생성한다.
     */
    private void createDBManager() {

        // [iv/C]UserDbManager : user 테이블을 관리하는 매니저 생성과 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블을 관리하는 매니저 생성과 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

        // [iv/C]BilliardDbManager : billiard 테이블을 관리하는 매니저 생성과 초기화
        this.billiardDbManager = new BilliardDbManager(this);
        this.billiardDbManager.initDb();

        // [iv/C]PlayerDbManager : player 테이블을 관리하는 매니저 생성과 초기화
        this.playerDbManager = new PlayerDbManager(this);
        this.playerDbManager.initDb();
    }


}