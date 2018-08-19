package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;

public class MyService extends JobService {
    public MyService() {}

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        ChargingUtility chargingUtility = new ChargingUtility(this);

        boolean requiresCharging = (boolean) jobParameters.getExtras().get("requiresCharging");
        System.out.println("Test was called with requiresCharging " + requiresCharging);

        // If requires charging == true, do job to check notifications


        if(chargingUtility.isCharging() && !requiresCharging) {
            System.out.println("Phone is AC charging, set up longer wait");

            new RepeatingJob(this).setChargingRequired(false).setPersisted(true)
                    .setMinimumLatency(10 * 1000).setBooleanArgument(false).buildJobAndSchedule();
        } else {
            System.out.println("Phone is not charging via AC, starting required charging job");

            new RepeatingJob(this).setChargingRequired(true).setPersisted(true)
                    .setBooleanArgument(true).buildJobAndSchedule();
        }

        jobFinished(jobParameters, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
