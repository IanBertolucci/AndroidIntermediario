package com.example.androidintermediario;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "android_intermediario";
    private static final int DB_VERSION = 1;
    static final String TABLE = "cadastro";
    static final String C_ID = "_id";
    static final String C_NOME = "nome";
    static final String C_SEXO = "sexo";
    static final String C_IDADE = "idade";

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table "+TABLE+" ("+C_ID+" integer primary key autoincrement, "
                    +C_NOME+" text, "+C_SEXO+" text, "+C_IDADE+" text)";
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.e("erro: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String sql = "drop table if exists "+ TABLE;
            db.execSQL(sql);
            onCreate(db);
        } catch (SQLException e) {
            Log.e("erro: ", e.getMessage());
        }
    }
}
