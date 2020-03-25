package com.example.androidintermediario;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class CameraExternaActivity extends AppCompatActivity {
    ImageView imageViewFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_externa);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        imageViewFoto = (ImageView) findViewById(R.id.imageViewFoto);
        findViewById(R.id.btnIniciarCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });
    }

    public void tirarFoto(){
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentFoto, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageViewFoto.setImageBitmap(imagem);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
