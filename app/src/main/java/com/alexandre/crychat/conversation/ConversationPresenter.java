package com.alexandre.crychat.conversation;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Base64;

import com.alexandre.crychat.receivers.SmsReceiver;
import com.alexandre.crychat.utilities.CryptoServiceProvider;
import com.alexandre.crychat.utilities.IDataReceived;

import android.telephony.SmsManager;


public class ConversationPresenter implements IConversationContract.Presenter, IDataReceived {
    private IConversationContract.View frag;
    /**
     * Used to identify crypted SMS messages
     *
     * Append MD5 to the beginning of a crypted SMS message
     */
    private final String MD5 = "b746dd1ceef69da6afdcbbaf320e018a";

    ConversationPresenter(Context context, IConversationContract.View frag)
    {
        this.frag = frag;
        this.frag.setPresenter(this);
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

    @Override
    public void afficherConversation(String conversationId, byte[] password) {

    }
}
