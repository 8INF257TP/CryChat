package com.alexandre.crychat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.widget.Toast;

import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.utilities.DateParser;
import com.alexandre.crychat.utilities.IDataReceived;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private final String MD5 = "b746dd1ceef69da6afdcbbaf320e018a";

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
        Message receivedMsg;

        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            for(int i = 0; i < pdus.length; ++i)
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);


                String message, conversationUUID, date;

                // creates new Message with current information and insert into database
                message = sms.getMessageBody();

                if(message.contains(MD5)) {
                    try {
                        conversationUUID = Base64.encodeToString(
                                MessageDigest.getInstance("SHA-512").digest(
                                                sms.getDisplayOriginatingAddress().getBytes()),
                                Base64.DEFAULT);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    conversationUUID = sms.getDisplayOriginatingAddress();
                }

                date = DateParser.getDateFromTimeStamp(sms.getTimestampMillis());

                receivedMsg = new Message(message, conversationUUID, date);

                messages.add(receivedMsg);

                if(listener != null)
                    listener.onDataReceived(receivedMsg);
            }



            for(Message message : messages)
            {
                List<Conversation> conversations = db.conversationDao().loadConversation(message.getConversationUUID());

                if(conversations.size() == 0) {
                    Conversation conversation = new Conversation();
                    conversation.setConversationID(message.getConversationUUID());
                    conversation.setTime(DateParser.getCurrentDate());
                    db.conversationDao().insertConversation(conversation);

                    if(listener != null)
                        listener.onDataReceived(conversation);
                }
                db.messageDao().insertMessage(message);
            }
        }
    }
}
