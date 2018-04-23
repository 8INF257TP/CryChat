package com.alexandre.crychat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.utilities.IDataReceived;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class SmsReceiver extends BroadcastReceiver {

    private static IDataReceived listener;

    /**
     *
     * @param listener
     */
    public static void addListener(IDataReceived listener) {
        SmsReceiver.listener = listener;
    }

    /**
     *
     */
    public static void removeListener() {
        SmsReceiver.listener = null;
    }

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        AppDatabase db = AppDatabase.getInstance(context);
        List<Message> messages = new ArrayList<>();
        Message receivedMsg = new Message();
        Message test = new Message();

        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            for(int i = 0; i < pdus.length; ++i)
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if(listener != null)
                    listener.onDataReceived(sms);

                // creates new Message with current information and insert into database
                Toast.makeText(context, sms.getMessageBody(), Toast.LENGTH_LONG);

                receivedMsg.setMessage(sms.getMessageBody());
                receivedMsg.setConversationUUID(sms.getDisplayOriginatingAddress());
                receivedMsg.setDate(Long.toString(sms.getTimestampMillis()));
                receivedMsg.setSource(sms.getDisplayOriginatingAddress());

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
                String date = formatter.format(new Date(sms.getTimestampMillis()));

                messages.add(receivedMsg);
            }

            for(Message message : messages)
                db.messageDao().insertMessage(message);
        }
    }
}
