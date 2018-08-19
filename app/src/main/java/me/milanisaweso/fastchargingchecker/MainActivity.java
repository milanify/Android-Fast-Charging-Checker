package me.milanisaweso.fastchargingchecker;

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
            scheduleChargingRequiredJob(this);
        }

        //*after job detects charging or onPlugged intent received*
        //
        //if chargingThroughWallCharger && !fastCharging
        //	sendNotificationSound(3);
        //
    }

    public static void scheduleChargingRequiredJob(Context context) {
        new RepeatingJob(context, RepeatingJob.CHARGING_REQUIRED)
                .setChargingRequired(true).setPersisted(true)
                .buildJobAndSchedule();
    }

    public static void scheduleChargingNotRequiredJob(Context context, int secondsToDelay) {
        new RepeatingJob(context, RepeatingJob.CHARGING_NOT_REQUIRED)
                .setChargingRequired(false).setPersisted(true)
                .setMinimumLatency(secondsToDelay * 1000).buildJobAndSchedule();
    }
}
