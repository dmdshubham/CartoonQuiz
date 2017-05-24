package com.karunendu.cartoonquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener{


    Switch sound_switch;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        sp=getSharedPreferences("Settings", Context.MODE_PRIVATE);

        sound_switch=(Switch)findViewById(R.id.sound_switch);

        boolean flag=sp.getBoolean("Is_sound_On",true);
        sound_switch.setChecked(flag);
        sound_switch.setOnCheckedChangeListener(this);

        String theme=sp.getString("theme","default");
        if(theme.equals("red"))
        {
            RadioButton rb=(RadioButton)findViewById(R.id.rb_red);
            rb.setChecked(true);
        }
        else if(theme.equals("green"))
        {
            RadioButton rb=(RadioButton)findViewById(R.id.rb_green);
            rb.setChecked(true);
        }
        else if(theme.equals("blue"))
        {
            RadioButton rb=(RadioButton)findViewById(R.id.rb_blue);
            rb.setChecked(true);
        }
        RadioGroup rg=(RadioGroup)findViewById(R.id.rg_theme);
        rg.setOnCheckedChangeListener(this);



    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.equals(sound_switch))
        {
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean("Is_sound_On",b);
            editor.commit();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        String data="";
        if(i==R.id.rb_default)
        {
            data="default";
        }
        else if(i==R.id.rb_red)
        {
            data="red";
        }
        else if(i==R.id.rb_green)
        {
            data="green";
        }
        else if(i==R.id.rb_blue)
        {
            data="blue";
        }
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("theme",data);
        editor.commit();



    }
}
