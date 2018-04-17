package com.alexandre.crychat.sms;

import android.util.Log;

import com.alexandre.crychat.utilities.CryptoServiceProvider;

import java.io.ByteArrayOutputStream;

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

            Log.d("CryChat crypted", sms_crypted.toString());
            String decrypted = crypto.Decrypt(crypted);
            Log.d("CryChat decrypted", decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe() {

    }
}
