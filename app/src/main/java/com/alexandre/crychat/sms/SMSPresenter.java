package com.alexandre.crychat.sms;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;

import com.alexandre.crychat.utilities.CryptoServiceProvider;
import java.io.ByteArrayOutputStream;
import java.util.List;

import android.telephony.SmsManager;
import android.widget.Toast;


public class SMSPresenter implements ISMSContract.Presenter {

    private ISMSContract.View frag;
    /**
     * Used to identify crypted SMS messages
     *
     * Append MD5 to the beginning of a crypted SMS message
     */
    private static final String MD5 = "b746dd1ceef69da6afdcbbaf320e018a";
    private static Context context;

    SMSPresenter(Context context, ISMSContract.View fragment)
    {
        frag = fragment;
        frag.setPresenter(this);
        this.context = context;
    }

    @Override
    public void getMessages(String groupId) {

    }

    /**
     *
     * @param msg Message devant être envoyé
     */
    public void sendMessage(String msg){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+15812343545", null, msg, null, null);
    }

    @Override
    public void subscribe() {
        CryptoServiceProvider crypto = new CryptoServiceProvider("test");
        try {
            byte[] crypted = crypto.Encrypt("va chier mon gros calis de chiens de marde");
            byte[] sms = new String(MD5).getBytes();

            /**
             * :)
             */
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(sms);
            output.write(crypted);
            byte[] sms_crypted = output.toByteArray();

            Log.d("CryChat crypted", Base64.encodeToString(crypted, Base64.DEFAULT));
            String decrypted = crypto.Decrypt(crypted);
            Log.d("CryChat decrypted", decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe() {

    }

    public static void messageReceived(SmsMessage sms) {
        String message = sms.getMessageBody();
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
    }
}
