package me.milanisaweso.fastchargingchecker;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/** The single, main user interface */
public class MainActivity extends AppCompatActivity {
    private Switch main_switch;
    private TextView main_switch_text_view;

    /**
     * Sets the View components' state
     * @param savedInstanceState the activity's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        main_switch = findViewById(R.id.main_switch);
        main_switch_text_view = findViewById(R.id.main_switch_text_view);
        main_switch.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });
    }

    /**
     * Make sure the View components' states are accurate when returning to the application
     */
    @Override
    protected void onResume() {
        super.onResume();
        setSwitchBasedOnNotificationAccess();
    }

    /**
     * If we are granted notification access, properly set the switch state and text view message
     * @return whether or not we have notification access
     */
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
