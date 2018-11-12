// Not needed, but useful scheduling a recurring job
//package me.milanisaweso.fastchargingchecker;
//
//import android.app.job.JobInfo;
//import android.app.job.JobScheduler;
//import android.content.ComponentName;
//import android.content.Context;
//
//public class RepeatingJob {
//    private JobScheduler jobScheduler;
//    private JobInfo.Builder jobBuilder;
//    public static final int CHARGING_NOT_REQUIRED = 0;
//    public static final int CHARGING_REQUIRED = 1;
//
//    public RepeatingJob(Context context, int jobIdentifier) {
//        jobScheduler = (JobScheduler) context.getApplicationContext()
//                .getSystemService(Context.JOB_SCHEDULER_SERVICE);
//
//        jobBuilder = new JobInfo.Builder(jobIdentifier,
//                new ComponentName(context, MyService.class));
//    }
//
//    public RepeatingJob setChargingRequired(boolean requiresCharging) {
//        jobBuilder.setRequiresCharging(requiresCharging);
//        return this;
//    }
//
//    public RepeatingJob setPersisted(boolean requiresPersistence) {
//        jobBuilder.setPersisted(requiresPersistence);
//        return this;
//    }
//
//    public RepeatingJob setMinimumLatency(int milliseconds) {
//        jobBuilder.setMinimumLatency(milliseconds);
//        return this;
//    }
//
//    public void buildJobAndSchedule() {
//        jobScheduler.schedule(jobBuilder.build());
//    }
//}
