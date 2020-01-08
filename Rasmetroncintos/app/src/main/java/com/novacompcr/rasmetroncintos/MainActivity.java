package com.novacompcr.rasmetroncintos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Context context;
    private MySQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        context=this;
        db = new MySQLiteHelper(context);
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    Log.i("tag", "pasando de la pantalla de inicio al login");
                    Intent GoLogin = new Intent(context, LoginActivity.class);
                    startActivity(GoLogin);
                }
            },
        500);
        //db.ReinitUser();
        //db.ReinitNotas();
    }
}
