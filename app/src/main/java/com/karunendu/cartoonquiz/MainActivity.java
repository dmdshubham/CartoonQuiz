package com.karunendu.cartoonquiz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.settings.Settings;

public class MainActivity extends AppCompatActivity implements  Runnable{

    LinearLayout optionContainer;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        optionContainer=(LinearLayout)findViewById(R.id.option_container);
        handler=new Handler();
        handler.postDelayed(this,2000);
    }

    @Override
    public void run() {
        optionContainer.setVisibility(View.VISIBLE);
    }
    public  void onPlay(View v)
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void onSettings(View v)
    {
        Intent intent=new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

}
