package com.example.androidintermediario;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class CameraExternaActivity extends AppCompatActivity {
    //    ImageView imageViewFoto;
    VideoView videoView;
    Uri uriVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_externa);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

//        imageViewFoto = (ImageView) findViewById(R.id.imageViewFoto);
        videoView = (VideoView) findViewById(R.id.videoView);
        findViewById(R.id.btnIniciarCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tirarFoto();
                gravarVideo();
            }
        });
    }

//    public void tirarFoto(){
//        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intentFoto, 1);
//    }

    public void gravarVideo() {
        String arquivo = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".mp4";
        File file = new File(arquivo);
        uriVideo = Uri.fromFile(file);

        Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intentVideo.putExtra(MediaStore.EXTRA_OUTPUT, uriVideo);
        intentVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intentVideo, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == 1 && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
//            Bitmap imagem = (Bitmap) extras.get("data");
//            imageViewFoto.setImageBitmap(imagem);
//        }
//
        if (requestCode == 2 && resultCode == RESULT_OK) {
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uriVideo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
