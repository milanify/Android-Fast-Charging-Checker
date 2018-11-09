package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.HashSet;

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

    public HashSet<String> getNotifications() {
        StatusBarNotification[] statusBarNotifications = getActiveNotifications();
        HashSet<String> hashSet = new HashSet<>();

        for(StatusBarNotification status : statusBarNotifications) {
            if(status.getPackageName().equalsIgnoreCase("android")) {
                hashSet.add(status.getNotification().tickerText.toString());
                hashSet.add(status.getNotification().extras.getString("android.text"));
            }
        }

        for(String s : hashSet) {
            Log.i(TAG, "HashSet contains " + s);
        }

        return hashSet;
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
