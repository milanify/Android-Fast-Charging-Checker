package me.milanisaweso.fastchargingchecker;

import android.os.Bundle;

public class StringHelper {

    public static String stringValueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }

    public static String stringValueOf(Bundle bundle, String notificationComponent) {
        return (bundle == null) ? "null" : bundle.getString(notificationComponent);
    }
}
