package com.skyman.billiarddata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.file.FileConstants;
import com.skyman.billiarddata.management.file.FileExport;
import com.skyman.billiarddata.management.file.FileImport;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.projectblue.database.AppDbManager;
import com.skyman.billiarddata.management.user.data.UserData;

import java.io.IOException;

public class AppSettingActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final String CLASS_NAME = AppSettingActivity.class.getSimpleName();

    // instance variable : session
    private UserData userData = null;

    // instance variable
    private AppDbManager appDbManager;

    // instance variable : widget
    private LinearLayout dataBackupExportWrapper;
    private LinearLayout dataBackupImportWrapper;

    // instance variable
    private FileExport fileExport;
    private FileImport fileImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        // sessionManager : getter ( userData )
        userData = SessionManager.getUserDataFromIntent(getIntent());

        // appDbManager
        initAppDbManager();

        // widget : connect -> init
        connectWidget();
        initWidget();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FileConstants.REQUEST_CODE_CREATE_FILE && resultCode == Activity.RESULT_OK) {

            if (data != null) {

                // 파일이 만들어진 위치의 Uri 를 가져오기
                Uri uri = data.getData();
                DeveloperManager.displayLog(
                        CLASS_NAME,
                        "내보내기/uri : " + uri.toString()
                );

                // FileExport 의 객체가 생성되었을 때
                // writeJsonObject() 메소드를 사용하여 해당 위치의 파일에
                // 데이터베이스의 모든 내용이 담긴 JsonObject 를 파일에 쓴다.
                if (fileExport != null) {

                    try {

                        fileExport.writeJsonObject(
                                uri,
                                new FileExport.OnSuccessListener() {
                                    @Override
                                    public void onSuccess() {
                                        // <사용자 알림>
                                        Toast.makeText(
                                                getApplicationContext(),
                                                R.string.appSetting_dataBackUp_export_noticeUser_success,
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }
                        );

                    } catch (IOException e) {

                        // 오류 발생
                        // <사용자 알림>
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.appSetting_dataBackUp_export_noticeUser_error,
                                Toast.LENGTH_SHORT
                        ).show();
                        e.printStackTrace();
                    }
                }
            }

        } else if (requestCode == FileConstants.REQUEST_CODE_OPEN_FILE && resultCode == Activity.RESULT_OK) {

            if (data != null) {

                // 선택한 파일 uri
                Uri uri = data.getData();
                DeveloperManager.displayLog(
                        CLASS_NAME,
                        "가져오기/uri : " + uri.toString()
                );

                if (fileImport != null) {

                    try {
                        fileImport.saveDatabase(
                                uri,
                                new FileImport.OnSuccessListener() {
                                    @Override
                                    public void onSuccess() {
                                        // <사용자 알림>
                                        Toast.makeText(
                                                getApplicationContext(),
                                                R.string.appSetting_dataBackUp_import_noticeUser_success,
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }
                        );
                    } catch (Exception e) {
                        // 오류 발생
                        // <사용자 알림>
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.appSetting_dataBackUp_import_noticeUser_error,
                                Toast.LENGTH_SHORT
                        ).show();
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    @Override
    protected void onDestroy() {

        appDbManager.closeDb();

        super.onDestroy();
    }

    @Override
    public void initAppDbManager() {

        appDbManager = new AppDbManager(this);
        appDbManager.connectDb(
                true,
                true,
                true,
                true
        );

    }

    @Override
    public void connectWidget() {

        dataBackupExportWrapper = (LinearLayout) findViewById(R.id.appSetting_dataBackUp_exportWrapper);

        dataBackupImportWrapper = (LinearLayout) findViewById(R.id.appSetting_dataBackUp_importWrapper);
    }

    @Override
    public void initWidget() {

        // '내보내기' 실행
        dataBackupExportWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (userData != null) {

                            // '파일 내보내기' 를 하기 위한 객체를 생성하고 초기 설정을 한다.
                            fileExport = new FileExport(AppSettingActivity.this, appDbManager, userData);

                            try {

                                fileExport.init();

                            } catch (Exception e) {
                                // 오류 발생
                                // <사용자 알림>
                                Toast.makeText(
                                        getApplicationContext(),
                                        R.string.appSetting_dataBackUp_export_noticeUser_error,
                                        Toast.LENGTH_SHORT
                                ).show();

                                e.printStackTrace();
                            }

                        } else {

                            // <사용자 알림>
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.appSetting_dataBackUp_export_noticeUser_noData,
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }
                }
        );

        // LinearLayout : dataImport
        dataBackupImportWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (userData != null) {

                            // <사용자 알림>
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.appSetting_dataBackUp_import_noticeUser_noImport,
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            // '파일 가져오기' 를 하기 위한 객체를 생성하고 초기 설정을 한다.
                            fileImport = new FileImport(AppSettingActivity.this, appDbManager);
                            fileImport.init();

                        }

                    }
                }
        );
    }


}