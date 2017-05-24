package com.karunendu.cartoonquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Karunendu Mishra on 1/4/2017.
 */

public class LevelAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<LevelData> list;
    public LevelAdapter(LayoutInflater inflater , ArrayList<LevelData> list)
    {
        this.inflater=inflater;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LevelData getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null)
        {
            view=inflater.inflate(R.layout.item_level,null);
        }
        TextView tv1=(TextView)view.findViewById(R.id.title_text);
        TextView tv2=(TextView)view.findViewById(R.id.qns_text);
        ImageView iv=(ImageView) view.findViewById(R.id.background_image);
        ProgressBar pb=(ProgressBar)view.findViewById(R.id.qns_progress);

        LevelData ld=getItem(i);
        tv1.setText(ld.getTitle());
        tv2.setText(ld.getNumOfAnswered()+ " of "+ld.getNumOfQuestion());
        pb.setMax(ld.getNumOfQuestion());
        pb.setProgress(ld.getNumOfAnswered());
        ImageLoader loader=ImageLoader.getInstance();
        loader.displayImage("assets://"+ld.getImage(),iv);

        return view;
    }
}
