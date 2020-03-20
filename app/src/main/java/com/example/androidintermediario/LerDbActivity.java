package com.example.androidintermediario;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class LerDbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_db);

        readDatabase();
    }

    private void readDatabase(){
        DbHelper dbHelper = null;
        SQLiteDatabase db = null;

        try {
            dbHelper = new DbHelper(this);
            db = dbHelper.getReadableDatabase();

            String[] from = {dbHelper.C_NOME, dbHelper.C_IDADE, dbHelper.C_SEXO, dbHelper.C_TELEFONE};
            int[] to = {R.id.textViewNomeDb, R.id.textViewIdadeDb, R.id.textViewSexoDb, R.id.textViewTelefoneDb};

            Cursor cursor = db.query(dbHelper.TABLE, null, null, null, null, null, dbHelper.C_ID + " desc");

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_ler_db_widgets, cursor, from, to);

            ListView listView = (ListView) findViewById(R.id.listViewDadosDb);
            listView.setAdapter(simpleCursorAdapter);

        } catch (Exception e){
            Log.e("ReadDatabase: ", e.getMessage());
        } finally {
            db.close();
        }
    }
}
