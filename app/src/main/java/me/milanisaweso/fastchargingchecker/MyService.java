package me.milanisaweso.fastchargingchecker;

import android.app.job.JobParameters;
import android.app.job.JobService;
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
            new RepeatingJob(this);
        }

        jobFinished(jobParameters, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
