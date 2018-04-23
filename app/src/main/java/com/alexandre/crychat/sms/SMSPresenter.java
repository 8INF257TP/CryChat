package com.alexandre.crychat.sms;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;

import com.alexandre.crychat.receivers.SmsReceiver;
import com.alexandre.crychat.utilities.CryptoServiceProvider;
import com.alexandre.crychat.utilities.IDataReceived;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.telephony.SmsManager;
import android.widget.Toast;


public class SMSPresenter implements ISMSContract.Presenter, IDataReceived {

    private ISMSContract.View frag;
    /**
     * Used to identify crypted SMS messages
     *
     * Append MD5 to the beginning of a crypted SMS message
     */
    private final String MD5 = "b746dd1ceef69da6afdcbbaf320e018a";

    SMSPresenter(Context context, ISMSContract.View fragment)
    {
        frag = fragment;
        frag.setPresenter(this);
        SmsReceiver.addListener(this);
    }

    @Override
    public void getMessages(String groupId) {

    }

    /**
     *
     * @param msg Message devant être envoyé
     */
    public void sendMessage(String address, String msg){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(address, null, msg, null, null);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onDataReceived(Object o) {
        SmsMessage sms;
        if(o.getClass() == SmsMessage.class)
            sms = (SmsMessage) o;
        else
            return;

        String message = sms.getMessageBody();
        //TODO: change password (input from user)
        CryptoServiceProvider crypto = new CryptoServiceProvider("test");
        if(message.contains(MD5)) {
            message = message.substring(MD5.length());
            byte[] cryptedMessage = Base64.decode(message, Base64.DEFAULT);

            try {
                message = crypto.Decrypt(cryptedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        frag.messageReceived(sms.getDisplayOriginatingAddress(), message);
    }
}
