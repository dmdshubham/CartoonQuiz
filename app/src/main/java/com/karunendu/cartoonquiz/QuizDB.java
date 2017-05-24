package com.karunendu.cartoonquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Karunendu Mishra on 1/7/2017.
 */

public class QuizDB extends SQLiteOpenHelper
{

    public QuizDB(Context context) {
        super(context, "Quiz", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd="Create table quiztable(level text, qns text, ans text)";
        db.execSQL(cmd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
