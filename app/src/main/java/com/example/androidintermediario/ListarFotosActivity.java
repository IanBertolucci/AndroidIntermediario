package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ListarFotosActivity extends AppCompatActivity {
    private ImageView imageViewFoto;
    ImagemDbHelper imagemDbHelper;
    SQLiteDatabase db;
    byte[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_fotos);

        imageViewFoto = (ImageView) findViewById(R.id.imageViewResultDb);
        imagemDbHelper = new ImagemDbHelper(this);
        db = imagemDbHelper.getReadableDatabase();

        Cursor cursor = db.query(ImagemDbHelper.TABLE, new String[]{ImagemDbHelper.C_FOTO}, null, null, null, null, ImagemDbHelper.C_ID+" desc");

        if (cursor.moveToNext()){
            img = cursor.getBlob(0);
        }

        if (img != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imageViewFoto.setImageBitmap(bitmap);
        }

    }
}
