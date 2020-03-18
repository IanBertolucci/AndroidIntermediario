package com.example.androidintermediario;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Date;

public class ExemploBindService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder iBinder = new LocalBinder();
        return iBinder;
    }

    public class LocalBinder extends Binder{
        public ExemploBindService getService(){
            return ExemploBindService.this;
        }
    }

    public String getHoras(){
        Date data = new Date();
        return data.toString();
    }
}
