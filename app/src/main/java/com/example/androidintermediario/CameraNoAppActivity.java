package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraNoAppActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;
    private Button btnCapturar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_no_app);

        camera = getCameraInstance();

        cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.frameLayout);
        preview.addView(cameraPreview);

        btnCapturar = (Button) findViewById(R.id.btnCapturar);
        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);
            }
        });
    }

    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    public Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picFile = getOutputMediaFile(1);
            if (picFile == null){
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(picFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static File getOutputMediaFile(int type){
        File mediaFile = null;
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/"+"FotosEVideos");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("erro: ", "falha ao criar diretório");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("HH_mm_ss").format(new Date());

        if (type == 1){
            mediaFile = new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
        }
        if (type == 2){
            mediaFile = new File(mediaStorageDir.getPath()+File.separator+"MOV_"+timeStamp+".mp4");
        }

        return mediaFile;
    }

    public void releaseCamera(){
        if (camera != null){
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
}
