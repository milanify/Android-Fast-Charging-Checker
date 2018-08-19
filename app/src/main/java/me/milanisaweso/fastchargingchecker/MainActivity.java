package me.milanisaweso.fastchargingchecker;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            new RepeatingJob(this).setChargingRequired(true).setPersisted(true)
            .setMinimumLatency(5000).buildJobAndSchedule();
        }

        //*after job detects charging or onPlugged intent received*
        //
        //if chargingThroughWallCharger && !fastCharging
        //	sendNotificationSound(3);
        //
        //1st time running:
        //when phone is charging:
        //	doJob()
        //	startJobThatSetRequiresCharging(false)
        //
        //when phone is either charging or not:
        //	if(phone is charging) do nothing, repeat after 5 mins
        //	if(phone is not charging) start 1st job
    }
}
