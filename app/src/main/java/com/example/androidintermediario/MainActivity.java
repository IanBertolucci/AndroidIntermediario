package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ExemploReceiver exemploReceiver;
    // o curso indica para ser usado o LocalBroadcastManager
    // para broadcasts internos, mas o método foi descontinuado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnEstatico).setOnClickListener(this);
        findViewById(R.id.btnDinamico).setOnClickListener(this);
        exemploReceiver = new ExemploReceiver();

        registerReceiver(exemploReceiver, new IntentFilter("BROADCAST_DINAMICO"));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnEstatico:
                sendBroadcast(new Intent("BROADCAST_ESTATICO"));
                break;
            case R.id.btnDinamico:
                sendBroadcast(new Intent("BROADCAST_DINAMICO"));
                break;
        }
        Toast.makeText(this, "Intent enviada", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(exemploReceiver);
    }
}
