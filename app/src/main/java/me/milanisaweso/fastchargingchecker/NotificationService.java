package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
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
                stringBuilder.append(valueOf(status.getNotification().tickerText));
                stringBuilder.append(" ");
                stringBuilder.append(valueOf(status.getNotification().extras, Notification.EXTRA_TITLE));
                stringBuilder.append(" ");
                stringBuilder.append(valueOf(status.getNotification().extras, Notification.EXTRA_TEXT));
                stringBuilder.append(" ");
                stringBuilder.append("Phone charging slowly");
            }
        }
        Log.i(TAG, stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }

    public static String valueOf(Bundle bundle, String notificationComponent) {
        return (bundle == null) ? "null" : bundle.getString(notificationComponent);
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
