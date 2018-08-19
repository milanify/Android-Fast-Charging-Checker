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
        boolean acCharge = new ChargingUtility(context).isChargingThroughAC();

        if(acCharge) {
            //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //StatusBarNotification[] notificationsArray = notificationManager.getActiveNotifications();

            Toast.makeText(context, "Plugged in via AC: "  + intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }
}
