package me.milanisaweso.fastchargingchecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import static me.milanisaweso.fastchargingchecker.StringHelper.stringValueOf;

/** Service to read notifications */
public class NotificationService extends NotificationListenerService {
    private BatteryManager batteryManager;
    private boolean firstChargingNotificationShown = false;
    private static NotificationService self = null;

    /** Set the static reference to the class */
    public NotificationService() {
        self = this;
    }

    /** Allows access to this class from other classes */
    public static NotificationService getNotificationService() {
        return self;
    }

    /**
     * When the service is running, do a necessary call to getActiveNotifications()
     * then create the BatteryManager object (the method this object uses requires Android.M)
     */
    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onListenerConnected() {
        super.onListenerConnected();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActiveNotifications();
        }
    }

    /**
     * When a user receives a notification, only process it if it's an Android system notification
     * and if the first charging notification hasn't been shown yet
     *
     * The second check is needed because the Android system constantly re-posts the
     * charging notification when a device is plugged in, so onNotificationPosted gets called
     * repeatedly, but we only want to handle it the first time
     * @param statusBarNotification the notification object
     */
    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        if(isAndroidSystemNotification(statusBarNotification) && !firstChargingNotificationShown) {
            StringBuilder stringBuilder = new StringBuilder();
            addNotificationToStringBuilder(statusBarNotification, stringBuilder);
            handleNotificationCheck(this, stringBuilder.toString().toLowerCase());
        }
    }

    /**
     * Check if notification is from the Android system
     * @param statusBarNotification the notification object
     * */
    public boolean isAndroidSystemNotification(StatusBarNotification statusBarNotification) {
        return  statusBarNotification.getPackageName().toLowerCase().contains("android");
    }

    /**
     * Reads the notification object as a string and handles null checking
     * @param statusBarNotification the notification object
     * @param stringBuilder the object holding our string
     */
    public void addNotificationToStringBuilder(StatusBarNotification statusBarNotification,
                                               StringBuilder stringBuilder) {
        Notification notification = statusBarNotification.getNotification();
        stringBuilder.append(stringValueOf(notification.tickerText));
        stringBuilder.append(" ");
        stringBuilder.append(stringValueOf(notification.extras, Notification.EXTRA_TITLE));
        stringBuilder.append(" ");
        stringBuilder.append(stringValueOf(notification.extras, Notification.EXTRA_TEXT));
        stringBuilder.append(" ");
    }

    /**
     * If the notification is a fast charging notification, play the specified sound
     * Sets the firstChargingNotificationShown variable to true so this doesn't repeat
     *
     * The AudioManager will play the notification sound at half the max volume of the device,
     * then set the device volume back to the user's original level before the sound was played
     * @param context the application context
     * @param notificationString the string to read
     */
    public void handleNotificationCheck(Context context, String notificationString) {
        if(notificationString.contains("fast charging") ||
                notificationString.contains("fast charger") ||
                notificationString.contains("fast-charging") ||
                notificationString.contains("fast-charger")) {
            firstChargingNotificationShown = true;

            final AudioManager audioManager = (AudioManager)
                    getSystemService(Context.AUDIO_SERVICE);

            final int soundType = AudioManager.STREAM_NOTIFICATION;
            final int userVolumeLevel = audioManager.getStreamVolume(soundType);
            final int maxVolumeHalvedLevel = audioManager.getStreamMaxVolume(soundType) / 2;

            audioManager.setStreamVolume(soundType, maxVolumeHalvedLevel, 0);

            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                    .setLegacyStreamType(soundType)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build());

            Uri path = Uri.parse("android.resource://" +
                    "me.milanisaweso.fastchargingchecker/" +
                    R.raw.fast_charge_sound);
            try {
                mediaPlayer.setDataSource(context, path);
                mediaPlayer.prepare();
            } catch(Exception e) {
                audioManager.setStreamVolume(soundType, userVolumeLevel, 0);
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    audioManager.setStreamVolume(soundType, userVolumeLevel, 0);
                }
            });

            mediaPlayer.start();
        }
    }

    /**
     * When the fast charging notification is removed (device unplugged from charging),
     * then set the firstChargingNotificationShown back to false so that we can read new charges
     *
     * Sleeps for 1 second to allow the batteryManager.isCharging() call to work properly when
     * the device is unplugged, because the device may still be in the charging state for a split
     * second after the charging notification is removed
     * @param statusBarNotification the notification object
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        batteryManager = (BatteryManager) this.getSystemService(Context.BATTERY_SERVICE);
        SystemClock.sleep(1000);
        if(!batteryManager.isCharging()) {
            firstChargingNotificationShown = false;
        }
    }

    /**
     * Gets the notification contents of all Android system notifications
     * @return a string containing all Android system notification text
     */
    public String getAndroidSystemNotifications() {
        StatusBarNotification[] statusBarNotifications = getActiveNotifications();
        StringBuilder stringBuilder = new StringBuilder();

        for(StatusBarNotification notification : statusBarNotifications) {
            if(isAndroidSystemNotification(notification)) {
                addNotificationToStringBuilder(notification, stringBuilder);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * If the notification listener service is killed for whatever reason, request a re-bind
     */
    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public void onListenerDisconnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestRebind(new ComponentName(this, NotificationListenerService.class));
        }
    }
}
