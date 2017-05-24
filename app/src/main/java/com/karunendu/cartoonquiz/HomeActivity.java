package com.karunendu.cartoonquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ArrayList<LevelData> levelItems;
    ListView listView;
    LevelAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.activity_home);
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



        AssetManager assets=getAssets();
        levelItems =new ArrayList<>();
        try {

            SQLiteDatabase db=new QuizDB(this).getReadableDatabase();
            String [] columns={"count(level)"};
            String sellection="level=? and qns=ans and ans != ''";
            for (int i = 1; i <= 9; i++)
            {
                LevelData ld = new LevelData();
                ld.setLevel(i);
                ld.setTitle("Level " + i);
                int qns = assets.list("level " + i).length;
                ld.setNumOfQuestion(qns);
                //ld.setNumOfAnswered(0);
                //select count(level) from quiztable where level='1' and qns=ans
                String [] selArgs={""+i};
                Cursor cr=db.query("quiztable",columns,sellection,selArgs,null,null,null);
                if(cr.moveToFirst())
                {
                    ld.setNumOfAnswered(cr.getInt(0));
                }
                else
                {
                    ld.setNumOfAnswered(0);
                }

                ld.setImage("level/level"+i+"_Image@2x.png");


                levelItems.add(ld);
            }
            db.close();

        }
        catch (Exception ex){
            int x=0;
        }



        listView=(ListView)findViewById(R.id.list);
        adapter=new LevelAdapter(getLayoutInflater(),levelItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        SQLiteDatabase db=new QuizDB(this).getReadableDatabase();
        String [] columns={"count(level)"};
        String sellection="level=? and qns=ans";
        for(int i=0;i<levelItems.size();i++)
        {
            LevelData ld=levelItems.get(i);
            String [] selArgs={""+ld.getLevel()};

            Cursor cr=db.query("quiztable",columns,sellection,selArgs,null,null,null);
            if(cr.moveToFirst())
            {
                ld.setNumOfAnswered(cr.getInt(0));
            }
            else
            {
                ld.setNumOfAnswered(0);
            }
        }
        db.close();
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent=new Intent(this,LevelActivity.class);
        intent.putExtra("level",(i+1));
        startActivity(intent);

    }
}
