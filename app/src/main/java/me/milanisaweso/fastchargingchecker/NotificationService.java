package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {
    public static String TAG = NotificationService.class.getSimpleName();
    private static NotificationService self = null;

    public NotificationService() {
        self = this;
    }

    public static NotificationService getNotificationService() {
        return self;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public void onListenerConnected() {
        super.onListenerConnected();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActiveNotifications();
        }
        Log.i(TAG, "Listener connected");
    }

    public String getAndroidSystemNotifications() {
        StatusBarNotification[] statusBarNotifications = getActiveNotifications();
        StringBuilder stringBuilder = new StringBuilder();

        for(StatusBarNotification status : statusBarNotifications) {
            if(status.getPackageName().toLowerCase().contains("android")) {
                stringBuilder.append(status.getNotification().tickerText.toString());
                stringBuilder.append(" ");
                stringBuilder.append(status.getNotification().extras.getString("android.text"));
                stringBuilder.append(" ");
                stringBuilder.append("Phone charging slowly");
            }
        }
        Log.i(TAG, stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){}

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public void onListenerDisconnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Notification listener disconnected - requesting rebind
            requestRebind(new ComponentName(this, NotificationListenerService.class));
        }
    }
}
