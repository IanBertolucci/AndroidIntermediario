package com.example.androidintermediario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
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

    private MediaRecorder mediaRecorder;
    private boolean gravando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_no_app);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 3);
        }

        camera = getCameraInstance();

        cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.frameLayout);
        preview.addView(cameraPreview);

        btnCapturar = (Button) findViewById(R.id.btnCapturar);
//        btnCapturar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                camera.takePicture(null, null, pictureCallback);
//            }
//        });

        btnCapturar.setOnClickListener(clickListenerVideo);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean prepareVideoRecorder(){
        mediaRecorder = new MediaRecorder();

        camera.unlock();
        mediaRecorder.setCamera(camera);

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mediaRecorder.setOutputFile(getOutputMediaFile(2));

        mediaRecorder.setPreviewDisplay(cameraPreview.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }

        return true;
    }

    private View.OnClickListener clickListenerVideo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (gravando){
                mediaRecorder.stop();
                releaseMediaRecorder();
                camera.lock();

                btnCapturar.setText("Gravar");
                gravando = false;
            } else {
                if (prepareVideoRecorder()){
                    mediaRecorder.start();
                    btnCapturar.setText("Stop");
                    gravando = true;
                } else {
                    releaseMediaRecorder();
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    public void releaseCamera(){
        if (camera != null){
            camera.release();
            camera = null;
        }
    }

    public void releaseMediaRecorder(){
        if (mediaRecorder != null){
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            camera.lock();
        }
    }
}
