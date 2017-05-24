package com.karunendu.cartoonquiz;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Karunendu Mishra on 1/5/2017.
 */

public class QuestionAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<QuestionData> dataArrayList;
    float w,h;
    public QuestionAdapter(LayoutInflater inflater,
            ArrayList<QuestionData> dataArrayList,float w,float h)
    {
        this.inflater=inflater;
        this.dataArrayList=dataArrayList;
        this.w=w;
        this.h=h;
    }

    @Override
    public int getCount() {
        return dataArrayList.size();
    }

    @Override
    public QuestionData getItem(int i) {
        return dataArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view=inflater.inflate(R.layout.item_question,null);
        }
        ImageView iv=(ImageView)view.findViewById(R.id.qns_image);
        RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)iv.getLayoutParams();
        lp.width=(int)w;
        lp.height=(int)h;
        QuestionData data=getItem(i);
        ImageLoader loader=ImageLoader.getInstance();
        loader.displayImage("assets://"+data.getImage(),iv);
        if(data.getQuestion().equalsIgnoreCase(data.getLastAnswer()))
        {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        else
        {
            view.setBackgroundColor(Color.WHITE);
        }
        return  view;
    }
}
