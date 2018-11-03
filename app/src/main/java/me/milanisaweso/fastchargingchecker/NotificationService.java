package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {
    public static String TAG = NotificationService.class.getSimpleName();

    public NotificationService() {}

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public void onListenerConnected() {
        super.onListenerConnected();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActiveNotifications();
        }
        Log.d(TAG, "Listener connected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        /*StatusBarNotification[] statusBarNotification = getActiveNotifications();
        for(StatusBarNotification status : statusBarNotification) {
            Log.i(TAG, "ID:" + status.getId());
            Log.i(TAG, "Posted by:" + status.getPackageName());
            Log.i(TAG, "tickerText:" + status.getNotification().tickerText);
        }*/
        Log.i(TAG, "Notification received");

        StatusBarNotification[] statusBarNotifications = getActiveNotifications();

        for(StatusBarNotification status : statusBarNotifications) {
            Log.i(TAG, "ID:" + status.getId());
            Log.i(TAG, "Posted by:" + status.getPackageName());
            Log.i(TAG, "tickerText:" + status.getNotification().tickerText);
        }

        /*Log.i(TAG, "ID:" + sbn.getId());
        Log.i(TAG, "Posted by:" + sbn.getPackageName());
        Log.i(TAG, "tickerText:" + sbn.getNotification().tickerText);*/
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
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
