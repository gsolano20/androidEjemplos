package com.gsolano.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView animationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animationView =(LottieAnimationView) findViewById(R.id.animation_view);
        //animationView.setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_Lq6i91.json");
        //animationView.setAnimationFromUrl("https://assets8.lottiefiles.com/packages/lf20_Ihv6OC.json");

        animationView.playAnimation();

        new android.os.Handler().postDelayed(

                new Runnable() {
                    public void run() {
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        animationView.cancelAnimation();
                        startActivity(mainActivity);
                    }
                },
                5000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
