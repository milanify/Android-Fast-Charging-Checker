package me.milanisaweso.fastchargingchecker;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        //if(Notifications detected "Your device is charging slowly")
        //	sendNotificationSound(3);
        //
        //Logic for notifications should be in a separate class
    }

    public void onClickBtn(View view) {
        NotificationService.getNotificationService().getNotifications();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
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
