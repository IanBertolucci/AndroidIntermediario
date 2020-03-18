package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity implements OnClickListener{

    private Button btnService;
    private Button btnBindService;
    private TextView textViewStatus;
    private ExemploBindService exemploBindService;
    private boolean statusBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        btnService = (Button) findViewById(R.id.btnService);
        btnBindService = (Button) findViewById(R.id.btnBindService);
        btnService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intentBind = new Intent(this, ExemploBindService.class);
        bindService(intentBind, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ExemploBindService.LocalBinder localBinder = (ExemploBindService.LocalBinder) service;
            exemploBindService = localBinder.getService();
            statusBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            statusBind = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (statusBind){
            unbindService(serviceConnection);
            statusBind = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnService:
                Intent service = new Intent(this, ExemploServices.class);
                if (isRunningService(service)){
                    stopService(service);
                    textViewStatus.setText("parado");
                } else {
                    startService(service);
                    textViewStatus.setText("rodando");
                }
                break;
            case R.id.btnBindService:
                if (statusBind){
                    String hora = exemploBindService.getHoras();
                    textViewStatus.setText(hora);
                }
                break;
        }
    }

    private boolean isRunningService(Intent intent){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if (service.service.getClassName().equals("com.example.androidintermediario.ExemploService")){
                return true;
            }
        }
        return false;
    }
}
