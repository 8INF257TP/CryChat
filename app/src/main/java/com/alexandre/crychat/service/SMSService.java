package com.alexandre.crychat.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alexandre.crychat.receivers.SmsReceiver;

/**
 * Created by alexa on 2018-04-23.
 */

public class SMSService extends Service {

    private SmsReceiver sms;
    private IntentFilter intentFilter;

    @Override
    public void onCreate() {
        sms = new SmsReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(sms, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(sms);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
