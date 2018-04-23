package com.alexandre.crychat.conversation;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Base64;

import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.receivers.SmsReceiver;
import com.alexandre.crychat.utilities.CryptoServiceProvider;
import com.alexandre.crychat.utilities.IDataReceived;

import android.telephony.SmsManager;

import java.util.ArrayList;


public class ConversationPresenter implements IConversationContract.Presenter, IDataReceived {
    private IConversationContract.View frag;
    private AppDatabase db;
    /**
     * Used to identify crypted SMS messages
     *
     * Append MD5 to the beginning of a crypted SMS message
     */
    private final String MD5 = "b746dd1ceef69da6afdcbbaf320e018a";

    ConversationPresenter(Context context, IConversationContract.View frag)
    {
        db = AppDatabase.getInstance(context);
        this.frag = frag;
        this.frag.setPresenter(this);
        SmsReceiver.addListener(this);

    }

    @Override
    public ArrayList<Message> getMessages(String groupId) {
        return (ArrayList) db.conversationDao().loadConversationMessages(groupId);
    }

    /**
     *
     * @param msg Message devant être envoyé
     */
    public void sendMessage(String address, String msg){
        db.messageDao().insertMessage(new Message(msg, "alex", frag.getConversation().getConversationID(), "2018-04-23"));
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
        Message receivedMessage = new Message(message, "alex", frag.getConversation().getConversationID(), "2018-04-23");
        db.messageDao().insertMessage(receivedMessage);
        frag.messageReceived(receivedMessage);
    }

    @Override
    public void afficherConversation(String conversationId, byte[] password) {

    }
}
