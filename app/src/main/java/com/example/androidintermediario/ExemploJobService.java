package com.example.androidintermediario;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExemploJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "Job iniciado",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "Job parado",Toast.LENGTH_LONG).show();
        return true;
    }
}
