package com.alexandre.crychat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msg = null;
        String str = "";

        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            for(int i = 0; i < pdus.length; ++i)
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String phone = sms.getOriginatingAddress();
                String message = sms.getMessageBody().toString();

                Toast.makeText(context, phone + ": " + message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
