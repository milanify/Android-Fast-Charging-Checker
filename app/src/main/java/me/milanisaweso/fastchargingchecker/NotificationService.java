package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActiveNotifications();
            batteryManager = (BatteryManager) this.getSystemService(Context.BATTERY_SERVICE);
        }
        Log.i(TAG, "Listener connected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        if(batteryManager.isCharging() && sbn.getPackageName().toLowerCase().contains("android")) {
            Log.i(TAG, "Charging");
            // Check the notification to see if we're fast charging
        }
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

    public void handleNotificationCheck(Context context, String notificationString) {
        if(getNotificationService() != null) {
            String notificationService = getNotificationService().getAndroidSystemNotifications().toLowerCase();
            if(notificationService.contains("charging slowly") || notificationService.contains("charge faster") ||
                    notificationService.contains("original charger") || notificationService.contains("slow charging") ||
                    notificationService.contains("slow charge") || notificationService.contains("slow") ||
                    notificationService.contains("slowly")) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(context, notification);
                r.play();
                r.play();
            }
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
