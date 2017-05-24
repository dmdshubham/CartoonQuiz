package com.karunendu.cartoonquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    int level;
    ArrayList<QuestionData> datalist;
    GridView gridView;
    QuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        getSupportActionBar().hide();

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.activity_level);
        SharedPreferences sp=getSharedPreferences("Settings",MODE_PRIVATE);
        String theme=sp.getString("theme","default");
        if(theme.equals("red"))
        {
            rl.setBackgroundColor(Color.RED);
        }
        else if(theme.equals("green"))
        {
            rl.setBackgroundColor(Color.GREEN);
        }
        else if(theme.equals("blue"))
        {
            rl.setBackgroundColor(Color.BLUE);
        }

        Intent intent=getIntent();
        level=intent.getIntExtra("level",1);
        AssetManager asset=getAssets();
        datalist=new ArrayList<>();
        try {
            String[] list = asset.list("level " + level);
            SQLiteDatabase db=new QuizDB(this).getReadableDatabase();
            String [] columns={"ans"};
            String sellection="level=? and qns=?";
            for(int i=0;i<list.length;i++)
            {
                String str=list[i];
                QuestionData qd=new QuestionData();
                qd.setQuestion(str.substring(0,str.length()-4));
                qd.setImage("level "+level+"/"+str);
                String [] selArgs={""+level,qd.getQuestion()};
                Cursor cr=db.query("quiztable",columns,sellection,selArgs,null,null,null);
                if(cr.moveToFirst())
                {
                    qd.setLastAnswer(cr.getString(0));
                }
                else
                {
                    qd.setLastAnswer("");
                }
                datalist.add(qd);

            }
            db.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        gridView=(GridView)findViewById(R.id.qns_grid);
        //width=?;
        //height=width*1.5f;

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float width = metrics.widthPixels;
        width=width/3;
        float height=width*1.5f;

        adapter =new QuestionAdapter(getLayoutInflater(),datalist,width,height);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        QuestionData data=datalist.get(i);
        Intent intent=new Intent(this,QuestionActivity.class);
        intent.putExtra("image",data.getImage());
        intent.putExtra("qns",data.getQuestion());
        intent.putExtra("ans",data.getLastAnswer());
        intent.putExtra("level",level);
        startActivity(intent);
    }
}
