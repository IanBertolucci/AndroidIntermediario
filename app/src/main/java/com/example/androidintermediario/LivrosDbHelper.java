package com.example.androidintermediario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LivrosDbHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "books.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "livros";
    static final String C_ID = "_id";
    static final String C_TITULO = "titulo";
    static final String C_AUTOR = "autor";

    public LivrosDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE
                + "(" + C_ID + " integer primary key autoincrement, "
                + C_TITULO + " text, "
                + C_AUTOR + " text )";
        try{
            db.execSQL(sql);
        }catch (Exception e) {
            Log.e("Error dbHelper", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("drop table if exists " + TABLE);
            onCreate(db);
        }catch (Exception e) {
            Log.e("Error dbHelper", e.getMessage());
        }
    }
}
