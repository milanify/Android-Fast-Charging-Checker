package me.milanisaweso.fastchargingchecker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

class RepeatingJob {

    public RepeatingJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getApplicationContext()
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder jobBuilder = new JobInfo.Builder(0,
                new ComponentName(context, MyService.class));

        jobBuilder.setRequiresCharging(true).setPersisted(true).setMinimumLatency(5000);

        jobScheduler.schedule(jobBuilder.build());
    }
}
