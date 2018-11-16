package me.milanisaweso.fastchargingchecker;

import android.os.Bundle;

/** Helps read notification text */
public class StringHelper {

    /**
     * Gets the string value of an object and handles null cases
     * @param obj the object to read
     * @return a string representation of the object
     */
    public static String stringValueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }

    /**
     * Gets the string value of a a bundle's data and handles null cases
     * @param bundle the notification's bundled data
     * @param notificationComponent the part of the notification
     * @return a string representation of the the bundle's item
     */
    public static String stringValueOf(Bundle bundle, String notificationComponent) {
        return (bundle == null) ? "null" : bundle.getString(notificationComponent);
    }
}
