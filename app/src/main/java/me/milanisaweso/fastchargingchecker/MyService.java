package me.milanisaweso.fastchargingchecker;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class MyService extends JobService {

    public MyService() {}

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        final int MINIMUM_SECONDS_TO_DELAY_JOB = 10;
        ChargingUtility chargingUtility = new ChargingUtility(this);

        int jobType = jobParameters.getJobId();
        Log.i("Service","Job was called with jobType " + jobType);

        if(jobType == RepeatingJob.CHARGING_REQUIRED) {
            Log.i("Service","Plugged in for the first time! Doing notification check.");
            //logic to check notifications

            MainActivity.scheduleChargingNotRequiredJob(this, MINIMUM_SECONDS_TO_DELAY_JOB);

        } else if(jobType == RepeatingJob.CHARGING_NOT_REQUIRED && chargingUtility.isCharging()) {
            Log.i("Service","Charging not required for this job, but we are still charging..." +
                    "do nothing except repeat this not required job");

            MainActivity.scheduleChargingNotRequiredJob(this, MINIMUM_SECONDS_TO_DELAY_JOB);

        } else if (jobType == RepeatingJob.CHARGING_NOT_REQUIRED && !chargingUtility.isCharging()) {
            Log.i("Service","Charging not required for this job, and we aren't charging..." +
                    "start the intial job again that waits for a charge!");

            MainActivity.scheduleChargingRequiredJob(this);
        }

        jobFinished(jobParameters, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
