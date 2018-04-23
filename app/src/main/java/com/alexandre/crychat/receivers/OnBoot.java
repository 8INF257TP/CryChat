package com.alexandre.crychat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alexandre.crychat.service.SMSService;

/**
 * Created by alexa on 2018-04-23.
 */

public class OnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, SMSService.class);
        context.startService(newIntent);
    }
}
