package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.skyman.billiarddata.dialog.AdMobDialog;
import com.skyman.billiarddata.etc.SectionManager;

public class AppInfoActivity extends AppCompatActivity implements SectionManager.Initializable {

    // instance variable
    private TextView mainSite;
    private TextView secondSite;
    private MaterialButton adMob;

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

        this.adMob = (MaterialButton) findViewById(R.id.appInfo_adMob);

    }

    @Override
    public void initWidget() {

        // widget (mainSite) : click listener
        this.mainSite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainSite.getText().toString()));
                        startActivity(intent);

                    }
                }
        );


        // widget (secondSite) : click listener
        this.secondSite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(secondSite.getText().toString()));
                        startActivity(intent);

                    }
                }
        );

        // widget (adMob) : click listener
        this.adMob.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        AdMobDialog dialog = AdMobDialog.newInstance();
//                        dialog.setStyle(
//                                DialogFragment.STYLE_NO_TITLE,
//                                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen
//                        );
//                        dialog.show(getSupportFragmentManager(), AdMobDialog.class.getSimpleName());

                        Intent intent = new Intent(AppInfoActivity.this, AdMobActivity.class);

                        startActivity(intent);

                    }
                }
        );

    }
}