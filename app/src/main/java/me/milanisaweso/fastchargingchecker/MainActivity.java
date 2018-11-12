package me.milanisaweso.fastchargingchecker;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Switch main_switch;
    private TextView main_switch_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_switch = findViewById(R.id.main_switch);
        main_switch_text_view = findViewById(R.id.main_switch_text_view);
        main_switch.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSwitchBasedOnNotificationAccess();
    }

    public static void handleNotificationCheck(Context context) {
        if(NotificationService.getNotificationService() != null) {
            String s = NotificationService.getNotificationService().getAndroidSystemNotifications().toLowerCase();
            if(s.contains("charging slowly") || s.contains("charge faster") ||
                    s.contains("original charger") || s.contains("slow charging") ||
                    s.contains("slow charge") || s.contains("slow") ||
                    s.contains("slowly")) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(context, notification);
                r.play();
                r.play();
            }
        }
    }

    public boolean setSwitchBasedOnNotificationAccess() {
        String notificationListenerString = Settings.Secure.getString(this.getContentResolver(),
                "enabled_notification_listeners");

        if (notificationListenerString == null ||
                !notificationListenerString.contains(getPackageName())) {
            main_switch.setChecked(false);
            main_switch_text_view.setText(getResources().getString(R.string.switch_off_message));
        } else {
            main_switch.setChecked(true);
            main_switch_text_view.setText(getResources().getString(R.string.switch_on_message));
        }

        return main_switch.isChecked();
    }

//     Not needed, but useful scheduling a recurring job
//    public static void scheduleChargingRequiredJob(Context context) {
//        new RepeatingJob(context, RepeatingJob.CHARGING_REQUIRED)
//                .setChargingRequired(true).setPersisted(true)
//                .buildJobAndSchedule();
//    }
//
//    public static void scheduleChargingNotRequiredJob(Context context, int secondsToDelay) {
//        new RepeatingJob(context, RepeatingJob.CHARGING_NOT_REQUIRED)
//                .setChargingRequired(false).setPersisted(true)
//                .setMinimumLatency(secondsToDelay * 1000).buildJobAndSchedule();
//    }
}
