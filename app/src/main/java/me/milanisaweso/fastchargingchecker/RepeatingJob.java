package me.milanisaweso.fastchargingchecker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

class RepeatingJob {
    private JobScheduler jobScheduler;
    private JobInfo.Builder jobBuilder;

    public RepeatingJob(Context context) {
        jobScheduler = (JobScheduler) context.getApplicationContext()
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        jobBuilder = new JobInfo.Builder(0,
                new ComponentName(context, MyService.class));
    }

    public RepeatingJob setChargingRequired(boolean b) {
        jobBuilder.setRequiresCharging(b);
        return this;
    }

    public RepeatingJob setPersisted(boolean b) {
        jobBuilder.setPersisted(b);
        return this;
    }

    public RepeatingJob setMinimumLatency(int milliseconds) {
        jobBuilder.setMinimumLatency(milliseconds);
        return this;
    }

    public void buildJobAndSchedule() {
        jobScheduler.schedule(jobBuilder.build());
    }
}
