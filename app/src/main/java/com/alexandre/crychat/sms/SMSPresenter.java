package com.alexandre.crychat.sms;

import android.util.Log;

import com.alexandre.crychat.utilities.CryptoServiceProvider;

import java.io.ByteArrayOutputStream;

import android.telephony.SmsManager;

public class SMSPresenter implements ISMSContract.Presenter {

    private ISMSContract.View frag;
    /**
     * Used to identify crypted SMS messages
     *
     * Append MD5 to the beginning of a crypted SMS message
     */
    private final char[] MD5 = "b746dd1ceef69da6afdcbbaf320e018a".toCharArray();

    SMSPresenter(ISMSContract.View fragment)
    {
        frag = fragment;
        frag.setPresenter(this);
    }

    @Override
    public void getMessages(String groupId) {

    }

    public void sendMessage(){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+15812343545", null, "sms message", null, null);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
