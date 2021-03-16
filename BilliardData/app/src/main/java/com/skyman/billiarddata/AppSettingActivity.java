package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppSettingActivity extends AppCompatActivity {

    // instance variable
    private LinearLayout dataExportWrapper;
    private LinearLayout dataImportWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        // LinearLayout : dataExport
        dataExportWrapper = (LinearLayout) findViewById(R.id.app_setting_data_export_wrapper);
        dataExportWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        // LinearLayout : dataImport
        dataImportWrapper = (LinearLayout) findViewById(R.id.app_setting_data_import_wrapper);
        dataImportWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
    }
}