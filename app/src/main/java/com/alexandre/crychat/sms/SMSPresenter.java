package com.alexandre.crychat.sms;

import android.util.Log;

import com.alexandre.crychat.utilities.CryptoServiceProvider;

public class SMSPresenter implements ISMSContract.Presenter {

    private ISMSContract.View frag;

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
            Log.d("CryChat crypted", crypted.toString());
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
