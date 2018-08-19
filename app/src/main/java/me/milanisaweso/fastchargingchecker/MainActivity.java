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
            .setBooleanArgument(true).buildJobAndSchedule();
        }

        //*after job detects charging or onPlugged intent received*
        //
        //if chargingThroughWallCharger && !fastCharging
        //	sendNotificationSound(3);
        //
        //
        //
        //plugged in for first time->setChargingRequiredIsTrue
        //if(setChargingRequiredTrue)
        // doesNotificationCheck
        // startJobThatRequiresChargingFalse
        //
        //->setChargingRequiredFalse
        //if setChargingRequiredFalse and isCharging then do nothing, and repeat startJobThatRequiresChargingFalse
        //
        //else if setChargingRequiredFalse and NotCharging then start first job
    }
}
