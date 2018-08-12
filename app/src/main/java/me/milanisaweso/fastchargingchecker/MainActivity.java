package me.milanisaweso.fastchargingchecker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

            jobScheduler.schedule(new JobInfo.Builder(0,
                    new ComponentName(this, MyService.class))
                    .setRequiresCharging(true)
                    .setPersisted(true)
                    .build());
        }
    }
}
