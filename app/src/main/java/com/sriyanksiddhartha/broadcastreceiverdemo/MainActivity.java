package com.sriyanksiddhartha.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Author: Sriyank Siddhartha
 * <p>
 * Module 5: Understanding Sticky Broadcast
 * <p>
 * "AFTER" project
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void methodOne(View view) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = registerReceiver(null, intentFilter);

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        displayBatteryStatus(status);
        Log.i(TAG, status + " ");
    }

    public void methodTwo(View view) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryStatusReceiver, intentFilter);
    }

    private BroadcastReceiver batteryStatusReceiver = new BroadcastReceiver() {

        private static final String TAG = "Main Battery Receiver";

        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            displayBatteryStatus(status);
            Log.i(TAG, status + " ");
        }
    };

    private void displayBatteryStatus(int status) {

        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            Toast.makeText(this, "Battery Getting Charged", Toast.LENGTH_SHORT).show();
        }

        if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            Toast.makeText(this, "Battery Getting Discharged", Toast.LENGTH_SHORT).show();
        }

        if (status == BatteryManager.BATTERY_STATUS_FULL) {
            Toast.makeText(this, "Battery Fully Charged", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(batteryStatusReceiver);
    }
}
