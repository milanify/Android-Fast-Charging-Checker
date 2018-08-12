package me.milanisaweso.fastchargingchecker;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Build;

public class MyService extends JobService {
    public MyService() {}

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        int i = 10;
        while(i > 0) {
            System.out.println("Job ran " + i);
            i--;
        }

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            scheduleRefresh();
        }

        jobFinished(jobParameters, false );

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private void scheduleRefresh() {
        JobScheduler JobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);

        JobScheduler.schedule(new JobInfo.Builder(0,
                new ComponentName(this, MyService.class))
                .setMinimumLatency(5000)
                .setRequiresCharging(true)
                .setPersisted(true)
                .build());
    }
}
