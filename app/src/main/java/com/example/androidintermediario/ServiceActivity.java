package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ServiceActivity extends AppCompatActivity implements OnClickListener{

    private Button btnService;
    private Button btnBindService;
    private Button btnAgendar;
    private TextView textViewStatus;
    private ExemploBindService exemploBindService;
    private boolean statusBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        btnService = (Button) findViewById(R.id.btnService);
        btnBindService = (Button) findViewById(R.id.btnBindService);
        btnAgendar = (Button) findViewById(R.id.btnAgendar);

        btnService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);
        btnAgendar.setOnClickListener(this);

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
            case R.id.btnAgendar:
                Intent agendar = new Intent(this, ExemploServices.class);
                PendingIntent pendingIntent = PendingIntent.getService(this, 1, agendar, 0);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.SECOND, 5);
                Long tempo = c.getTimeInMillis();

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, tempo, pendingIntent);

                Toast.makeText(this, "Serviço agendado", Toast.LENGTH_LONG).show();

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
