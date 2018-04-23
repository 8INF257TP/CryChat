package com.alexandre.crychat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.alexandre.crychat.utilities.IDataReceived;

public class SmsReceiver extends BroadcastReceiver {

    private static IDataReceived listener;

    public static void addListener(IDataReceived listener) {
        SmsReceiver.listener = listener;
    }

    public static void removeListener() {
        SmsReceiver.listener = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            for(int i = 0; i < pdus.length; ++i)
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if(listener != null)
                    listener.onDataReceived(sms);
            }
        }
    }
}
