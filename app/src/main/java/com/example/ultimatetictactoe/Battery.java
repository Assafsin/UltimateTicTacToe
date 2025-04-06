package com.example.ultimatetictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


public class Battery extends BroadcastReceiver {

    private int batteryThreshold;
    public static boolean alreadyChecked = false;

    // Default constructor with a default threshold of 50%
    public Battery() {
        this(50);
    }

    // Parameterized constructor to set a custom threshold
    public Battery(int batteryThreshold) {
        this.batteryThreshold = batteryThreshold;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!alreadyChecked) {
            // Get battery level from the intent
            int level = intent.getIntExtra("level", 0);

            // Present low battery alert if battery level is below the set threshold
            if (level < batteryThreshold) {
                timerDelayRemoveDialog(3000, new AlertDialog.Builder(context)
                        .setTitle("Low Battery")
                        .setIcon(android.R.drawable.ic_lock_idle_low_battery)
                        .show());
            }
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d.dismiss();
            }
        }, time);
        alreadyChecked = true;
    }
}


