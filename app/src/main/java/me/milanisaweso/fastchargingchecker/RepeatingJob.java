package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

public class RepeatingJob {
    private JobScheduler jobScheduler;
    private JobInfo.Builder jobBuilder;

    public RepeatingJob(Context context) {
        jobScheduler = (JobScheduler) context.getApplicationContext()
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        jobBuilder = new JobInfo.Builder(0,
                new ComponentName(context, MyService.class));
    }

    public RepeatingJob setChargingRequired(boolean requiresCharging) {
        jobBuilder.setRequiresCharging(requiresCharging);
        return this;
    }

    public RepeatingJob setPersisted(boolean requiresPersistence) {
        jobBuilder.setPersisted(requiresPersistence);
        return this;
    }

    public RepeatingJob setMinimumLatency(int milliseconds) {
        jobBuilder.setMinimumLatency(milliseconds);
        return this;
    }

    @TargetApi(22)
    public RepeatingJob setBooleanArgument(Boolean requiresCharging) {
        PersistableBundle bundle = new PersistableBundle();
        bundle.putBoolean("requiresCharging", requiresCharging);
        jobBuilder.setExtras(bundle);
        return this;
    }

    public void buildJobAndSchedule() {
        jobScheduler.schedule(jobBuilder.build());
    }
}
