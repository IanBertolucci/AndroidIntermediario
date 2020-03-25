package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCameraExterna).setOnClickListener(this);
        findViewById(R.id.btnCameraApp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCameraExterna:
                startActivity(new Intent(MainActivity.this, CameraExternaActivity.class));
                break;
            case R.id.btnCameraApp:
                startActivity(new Intent(MainActivity.this, CameraNoAppActivity.class));
                break;
        }
    }
}
