package com.skyman.billiarddata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.button.MaterialButton;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.SectionManager;

public class AppInfoActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "AppInfoActivity";

    // constant
    private static final String TEST_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String REAL_ID = "ca-app-pub-9668325238172702/1575756579";

    // instance variable
    private TextView blog1;
    private TextView blog2;
    private MaterialButton adMob;
    private AdView adView;

    // instance variable
    private InterstitialAd interstitialAd;

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

        this.blog1 = (TextView) findViewById(R.id.appInfo_blog_1);

        this.blog2 = (TextView) findViewById(R.id.appInfo_blog_2);

        this.adMob = (MaterialButton) findViewById(R.id.appInfo_adMob);

        this.adView = (AdView) findViewById(R.id.appInfo_adView);

    }

    @Override
    public void initWidget() {

        initWidgetOfAdView();

        initWidgetOfFullAdView();

        // widget (blog1) : click listener
        this.blog1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(blog1.getText().toString()));
                        startActivity(intent);

                    }
                }
        );

        // widget (blog2) : click listener
        this.blog2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(blog2.getText().toString()));
                        startActivity(intent);

                    }
                }
        );

        // widget (adMob) : click listener
        this.adMob.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 전면 광고는 widget 에 로드하는 것이 아니다.
                        // interstitialAd 가 초기화가 되고 객체가 생성되었을 때
                        // show() 메소드를 사용하여 해당 Activity 에 표시할 수 있다.
                        // interstitialAd 가 null 이면 전면광고를 표시 할 수 없다.
                        if (interstitialAd != null) {

                            // <사용자 확인>
                            Toast.makeText(
                                    AppInfoActivity.this,
                                    R.string.appInfo_noticeUser_loadFullAd,
                                    Toast.LENGTH_SHORT
                            ).show();

                            interstitialAd.show(AppInfoActivity.this);

                        } else {

                            // <사용자 확인>
                            Toast.makeText(
                                    AppInfoActivity.this,
                                    R.string.appInfo_noticeUser_didNotLoadFullAd,
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                }
        );

    }

    /**
     * 스마트 배너를 AdView widget 에 표시한다.
     */
    private void initWidgetOfAdView() {

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {

                    }
                }
        );

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    /**
     * 전면 광고를 표시하기 위한 초기화 작업을 수행한다.
     */
    private void initWidgetOfFullAdView() {

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {

                    }
                }
        );

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(
                this,
                REAL_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                        AppInfoActivity.this.interstitialAd = interstitialAd;

                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        AppInfoActivity.this.interstitialAd = null;

                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");

                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        AppInfoActivity.this.interstitialAd = null;

                                    }
                                }

                        );
                        super.onAdLoaded(interstitialAd);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());

                        // <사용자 확인>
                        Toast.makeText(
                                AppInfoActivity.this,
                                R.string.appInfo_noticeUser_errorAdMob,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

        );
    }
}