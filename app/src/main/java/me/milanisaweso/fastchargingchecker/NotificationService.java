package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import static me.milanisaweso.fastchargingchecker.StringHelper.stringValueOf;

public class NotificationService extends NotificationListenerService {
    private BatteryManager batteryManager;

    private static NotificationService self = null;
    public static String TAG = NotificationService.class.getSimpleName();

    public NotificationService() {
        self = this;
    }

    public static NotificationService getNotificationService() {
        return self;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onListenerConnected() {
        super.onListenerConnected();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActiveNotifications();
        }
        Log.i(TAG, "Listener connected");
        batteryManager = (BatteryManager) this.getSystemService(Context.BATTERY_SERVICE);
    }

    public String getAndroidSystemNotifications() {
        StatusBarNotification[] statusBarNotifications = getActiveNotifications();
        StringBuilder stringBuilder = new StringBuilder();

        for(StatusBarNotification notification : statusBarNotifications) {
            if(notification.getPackageName().toLowerCase().contains("android")) {
                stringBuilder.append(stringValueOf(notification.getNotification().tickerText));
                stringBuilder.append(" ");
                stringBuilder.append(stringValueOf(notification.getNotification().extras, Notification.EXTRA_TITLE));
                stringBuilder.append(" ");
                stringBuilder.append(stringValueOf(notification.getNotification().extras, Notification.EXTRA_TEXT));
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        if(batteryManager.isCharging()) {
            Log.i(TAG, "Charging");
            //MainActivity.handleNotificationCheck(this);
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public void onListenerDisconnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestRebind(new ComponentName(this, NotificationListenerService.class));
        }
    }
}
