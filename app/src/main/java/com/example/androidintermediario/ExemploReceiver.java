package com.example.androidintermediario;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExemploReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String acao = intent.getAction();

        if (acao.equals("BROADCAST_DINAMICO")){
            Log.i("RECEIVER", "dinamico");
        }else if (acao.equals("BROADCAST_ESTATICO")){
            Log.i("RECEIVER", "estatico");
        }

    }
}
