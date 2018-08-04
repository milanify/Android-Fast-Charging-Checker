package me.milanisaweso.fastchargingchecker;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean acCharge = isACCharge(context);

        if(acCharge) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            StatusBarNotification[] notificationsArray = notificationManager.getActiveNotifications();

            Toast.makeText(context, "Plugged in via AC: "  + intent.getAction(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Plugged in "  + intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isACCharge(Context context) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, intentFilter);

        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }
}
