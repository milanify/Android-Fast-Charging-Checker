package me.milanisaweso.fastchargingchecker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class ChargingUtility {
    private IntentFilter intentFilter;
    private Intent batteryStatus;
    private int chargePlug;

    public ChargingUtility(Context context) {
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = context.registerReceiver(null, intentFilter);
        chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    }

    public boolean isCharging() {
        return chargePlug == BatteryManager.BATTERY_PLUGGED_AC ||
                chargePlug == BatteryManager.BATTERY_PLUGGED_USB ||
                chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;
    }

    public boolean isChargingThroughAC() {
        return chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }
}
