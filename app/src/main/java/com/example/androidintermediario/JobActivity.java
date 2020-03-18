package com.example.androidintermediario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class JobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        findViewById(R.id.btnAgendarJob).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ComponentName jobComponente = new ComponentName(JobActivity.this, ExemploJobService.class);
                JobInfo.Builder builder = new JobInfo.Builder(1, jobComponente);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setRequiresCharging(false);

                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                JobInfo jobInfo = builder.build();
                jobScheduler.schedule(jobInfo);

                Toast.makeText(JobActivity.this, "job agendado", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btnCancelarJob).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ComponentName jobComponente = new ComponentName(JobActivity.this, ExemploJobService.class);

                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                jobScheduler.cancelAll();

                Toast.makeText(JobActivity.this, "job cancelado", Toast.LENGTH_LONG).show();
            }
        });
    }
}
