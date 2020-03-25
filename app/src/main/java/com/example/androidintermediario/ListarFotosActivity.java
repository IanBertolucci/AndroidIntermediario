package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListarFotosActivity extends AppCompatActivity {
//    private ImageView imageViewFoto;
    ImagemDbHelper imagemDbHelper;
    SQLiteDatabase db;
    byte[] img;
    FotosAdapter fotosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_fotos);

//        imageViewFoto = (ImageView) findViewById(R.id.imageViewResultDb);

        imagemDbHelper = new ImagemDbHelper(this);
        db = imagemDbHelper.getReadableDatabase();

        Cursor cursor = db.query(ImagemDbHelper.TABLE, null, null, null, null, null, ImagemDbHelper.C_ID+" desc");

        List<Fotos> listFotos = new ArrayList<>();

        while (cursor.moveToNext()){
            Fotos fotos = new Fotos();
            fotos.setId(cursor.getLong(0));
            fotos.setImagem(cursor.getBlob(1));
            listFotos.add(fotos);
        }

        fotosAdapter = new FotosAdapter(this, R.layout.activity_listar_fotos_item, listFotos);
        ListView listView = (ListView) findViewById(R.id.listViewResultDb);
        listView.setAdapter(fotosAdapter);

//        if (cursor.moveToNext()){
//            img = cursor.getBlob(0);
//        }
//
//        if (img != null){
//            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
////            imageViewFoto.setImageBitmap(bitmap);
//        }

    }
}
