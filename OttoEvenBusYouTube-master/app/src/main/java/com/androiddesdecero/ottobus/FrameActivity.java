package com.androiddesdecero.ottobus;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

public class FrameActivity extends AppCompatActivity {


    private TextView mTvSegundoFragment;
    private EditText mEtSegundoFragment;
    private Button mBtSegundoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_segundo);

        confiView();
    }

    private void confiView(){
       // View view =  inflater.inflate(R.layout.fragment_segundo, container, false);

        mTvSegundoFragment = findViewById(R.id.segundo_fragment_tv);

        mBtSegundoFragment = findViewById(R.id.segundo_fragment_bt);
        mEtSegundoFragment = findViewById(R.id.segundo_fragment_et);

        mBtSegundoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusDeOtto.getBus().post(new MessageFtoA(mEtSegundoFragment.getText().toString()));
                finish();
            }
        });

    }


    @Override
    protected void onResume(){
        super.onResume();
        BusDeOtto.getBus().register(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        BusDeOtto.getBus().unregister(this);
    }

    @Subscribe
    public void receiveMessageFtoA(MessageAtoF message){
        mTvSegundoFragment.setText(message.getMessage());
    }



}
