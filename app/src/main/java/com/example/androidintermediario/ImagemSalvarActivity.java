package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ImagemSalvarActivity extends AppCompatActivity {
    private ImageView imageViewFoto;

    SQLiteDatabase db;
    ImagemDbHelper imagemDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem_salvar);

        imageViewFoto = (ImageView) findViewById(R.id.imageViewFotoDb);

        imagemDbHelper = new ImagemDbHelper(this);

        findViewById(R.id.btnTirarFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        findViewById(R.id.btnListarFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImagemSalvarActivity.this, ListarFotosActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] img = outputStream.toByteArray();

            ContentValues values = new ContentValues();
            values.put(ImagemDbHelper.C_FOTO, img);

            db = imagemDbHelper.getWritableDatabase();

            if (db.insertOrThrow(ImagemDbHelper.TABLE, null, values) != -1){
                Toast.makeText(this, "Imagem salva com sucesso!", Toast.LENGTH_SHORT).show();
            }

            imageViewFoto.setImageBitmap(imagem);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void tirarFoto(){
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentFoto, 1);
    }
}
