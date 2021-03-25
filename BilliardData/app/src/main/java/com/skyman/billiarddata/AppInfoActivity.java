package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skyman.billiarddata.etc.SectionManager;

public class AppInfoActivity extends AppCompatActivity implements SectionManager.Initializable {

    // instance variable
    private TextView mainSite;
    private TextView secondSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        // widget : connect -> init
        connectWidget();
        initWidget();

    }

    @Override
    public void initAppDbManager() {

    }

    @Override
    public void connectWidget() {

        this.mainSite = (TextView) findViewById(R.id.appInfo_mainSite);

        this.secondSite = (TextView) findViewById(R.id.appInfo_secondSite);

    }

    @Override
    public void initWidget() {

        // TextView : mainSite
        this.mainSite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainSite.getText().toString()));
                        startActivity(intent);

                    }
                }
        );

        // TextView : secondSite
        this.secondSite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(secondSite.getText().toString()));
                        startActivity(intent);

                    }
                }
        );

    }
}