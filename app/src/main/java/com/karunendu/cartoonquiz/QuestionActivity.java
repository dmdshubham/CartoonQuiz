package com.karunendu.cartoonquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luolc.emojirain.EmojiRainLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.paulyung.laybellayout.LaybelLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{

    int level;
    String image;
    String qns;
    String ans;
    ImageView imageView;
    LaybelLayout laybelLayout;
    ArrayList<String> charList;
    ArrayList<Integer> indexList;
    TextView ansTextView;
    EmojiRainLayout mContainer;
    MediaPlayer mp;
    SharedPreferences sp;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getSupportActionBar().hide();
        imageView=(ImageView)findViewById(R.id.qns_image);
        ansTextView=(TextView)findViewById(R.id.answer_text);
        mContainer=(EmojiRainLayout)findViewById(R.id.group_emoji_container);

        Intent intent=getIntent();
        level=intent.getIntExtra("level",0);
        image=intent.getStringExtra("image");
        qns=intent.getStringExtra("qns");
        ans=intent.getStringExtra("ans");

        ImageLoader loader=ImageLoader.getInstance();
        loader.displayImage("assets://"+image,imageView);
        sp=getSharedPreferences("Settings", Context.MODE_PRIVATE);
        flag=sp.getBoolean("Is_sound_On",true);

        LinearLayout rl=(LinearLayout)findViewById(R.id.activity_question);
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
        if(qns.equalsIgnoreCase(ans))
        {
            ansTextView.setText(qns);
            findViewById(R.id.backbtn).setVisibility(View.GONE);
            return;
        }

        LayoutInflater inflater=getLayoutInflater();
        laybelLayout=(LaybelLayout)findViewById(R.id.laybel_layout);
        charList=new ArrayList<>();
        indexList=new ArrayList<>();
        for(int i=0;i<qns.length();i++)
        {
            String chars=qns.toUpperCase().charAt(i)+"";
            if(chars.equals(" "))
            {
                continue;
            }
            charList.add(chars);
        }
        long seed = System.nanoTime();
        Collections.shuffle(charList, new Random(seed));


        for(int i=0;i<charList.size();i++)
        {
            String chars=charList.get(i);
            View view=inflater.inflate(R.layout.item_text, null);
            TextView tv=(TextView)view.findViewById(R.id.text_char);
            tv.setText(chars);
            tv.setTag(i);
            tv.setOnClickListener(this);
            laybelLayout.addView(view);
        }
        View view=inflater.inflate(R.layout.item_text, null);
        TextView tv=(TextView)view.findViewById(R.id.text_char);
        tv.setText(" ");
        tv.setOnClickListener(this);
        laybelLayout.addView(view);
        mp=MediaPlayer.create(this,R.raw.right);

    }

    @Override
    public void onClick(View view)
    {
        TextView tv=(TextView)view;
        String clikedText=tv.getText().toString();
        String str=ansTextView.getText().toString();
        if(str.length()!=0)
        {
            if(str.lastIndexOf(" ")!=str.length()-1)
            {
                clikedText=clikedText.toLowerCase();
            }
        }
        str=str+clikedText;
        ansTextView.setText(str);

        if(!clikedText.equals(" "))
        {
            tv.setText("");
            int tag=(int)tv.getTag();
            indexList.add(tag);

        }
        if(str.equalsIgnoreCase(qns) )
        {
            SQLiteDatabase db=new QuizDB(this).getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("level",""+level);
            cv.put("qns",qns);
            cv.put("ans",str);
            long record=db.insert("quiztable",null,cv);
            Toast.makeText(this,"Success: "+record,Toast.LENGTH_LONG).show();
            db.close();
            ImageLoader loader=ImageLoader.getInstance();
            Bitmap bitmap=loader.loadImageSync("assets://"+image);

            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.addEmoji(bitmap);
            mContainer.startDropping();

            if(flag) {
                mp.reset();
                mp = MediaPlayer.create(this, R.raw.right);
                mp.start();
            }

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mContainer.stopDropping();
                    finish();
                }
            },5000);
        }
        else if(str.length()==qns.length())
        {
            Toast.makeText(this,"Wrong",Toast.LENGTH_LONG).show();
            if(flag) {
                mp.reset();
                mp = MediaPlayer.create(this, R.raw.wrong);
                mp.start();
            }
        }
    }
    public void onBackClick(View v)
    {
        String str=ansTextView.getText().toString();
        if(str.length()>0)
        {
            String lastChar=str.charAt(str.length()-1)+"";
            lastChar=lastChar.toUpperCase();
            ansTextView.setText(str.substring(0,str.length()-1));
            if(!lastChar.equals(" "))
            {
                int index=indexList.get(indexList.size()-1);
                indexList.remove(indexList.size()-1);
                View view=laybelLayout.getChildAt(index);
                TextView tv=(TextView)view.findViewById(R.id.text_char);
                tv.setText(lastChar);
            }
        }
    }
}
