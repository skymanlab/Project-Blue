package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AppInfoActivity extends AppCompatActivity {

    // instance variable
    private TextView mainSite;
    private TextView secondSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        // TextView : mainSite
        this.mainSite = (TextView) findViewById(R.id.app_info_main_site);
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
        this.secondSite = (TextView) findViewById(R.id.app_info_second_site);
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