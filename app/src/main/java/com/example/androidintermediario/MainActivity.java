package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

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
        findViewById(R.id.btnAgendarB).setOnClickListener(this);
        findViewById(R.id.btnRepetir).setOnClickListener(this);
        findViewById(R.id.btnCancelar).setOnClickListener(this);

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
            case R.id.btnAgendarB:
                Intent agendarBroadcast = new Intent("BROADCAST_DINAMICO");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, agendarBroadcast, 0);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.SECOND, 5);
                Long tempo = c.getTimeInMillis();

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, tempo, pendingIntent);

                Toast.makeText(this, "Broadcast agendada", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnRepetir:
                Intent agendarBroadcastRepetir = new Intent("BROADCAST_DINAMICO");
                PendingIntent pendingIntentRepetir = PendingIntent.getBroadcast(this, 2, agendarBroadcastRepetir, 0);

                Calendar cRepetir = Calendar.getInstance();
                cRepetir.add(Calendar.SECOND, 5);
                Long tempoRepetir = cRepetir.getTimeInMillis();

                AlarmManager alarmManagerRepetir = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerRepetir.setRepeating(AlarmManager.RTC, tempoRepetir, 60000, pendingIntentRepetir);

                Toast.makeText(this, "Broadcast repetindo", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnCancelar:
                Intent agendarBroadcastCancelar = new Intent("BROADCAST_DINAMICO");
                PendingIntent pendingIntentCancelar = PendingIntent.getBroadcast(this, 2, agendarBroadcastCancelar, 0);

                AlarmManager alarmManagerCancelar = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerCancelar.cancel(pendingIntentCancelar);

                Toast.makeText(this, "Broadcast cancelado", Toast.LENGTH_LONG).show();

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
