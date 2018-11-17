package me.milanisaweso.fastchargingchecker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/** Lets us know the charging status of the battery */
public class ChargingUtility {
    private Context context;
    private IntentFilter intentFilter;
    private Intent batteryStatus;
    private int chargePlug;

    /**
     * Construct the object and the intent filter we will use
     * @param context
     */
    public ChargingUtility(Context context) {
        this.context = context;
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    /**
     * Lets us know if the device is charging
     * @return whether or not the phone is plugged in to charge
     */
    public boolean isCharging() {
        reloadBatteryStatusIntent();
        return chargePlug == BatteryManager.BATTERY_PLUGGED_AC ||
                chargePlug == BatteryManager.BATTERY_PLUGGED_USB ||
                chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;
    }

    /**
     * Lets us know if the device is charging through an AC adapter
     * @return whether or not the phone is plugged in through an AC adapter
     */
    public boolean isChargingThroughAC() {
        reloadBatteryStatusIntent();
        return chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }

    /**
     * Refreshes the battery status intent to give us the most updated
     * condition of the battery
     */
    public void reloadBatteryStatusIntent() {
        batteryStatus = context.registerReceiver(null, intentFilter);
        chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    }
}
