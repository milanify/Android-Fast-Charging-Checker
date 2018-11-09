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
    }

    public void getNotifications() {
        StatusBarNotification[] statusBarNotifications = getActiveNotifications();

        for(StatusBarNotification status : statusBarNotifications) {
            Log.i(TAG, "ID:" + status.getId());
            Log.i(TAG, "Posted by:" + status.getPackageName());
            Log.i(TAG, "tickerText:" + status.getNotification().tickerText);
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        Log.i(TAG, "Notification received");
    }

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public void onListenerDisconnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Notification listener disconnected - requesting rebind
            requestRebind(new ComponentName(this, NotificationListenerService.class));
        }
    }
}
