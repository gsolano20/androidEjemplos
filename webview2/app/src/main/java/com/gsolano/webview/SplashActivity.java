package com.gsolano.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //animationView.cancelAnimation();
            //startActivity(mainActivity);

            startActivityForResult(mainActivity, 0);
            overridePendingTransition(0,0);
        }
        },500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
