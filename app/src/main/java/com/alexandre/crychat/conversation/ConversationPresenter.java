package com.alexandre.crychat.conversation;

import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Base64;

import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.receivers.SmsReceiver;
import com.alexandre.crychat.utilities.CryptoServiceProvider;
import com.alexandre.crychat.utilities.DateParser;
import com.alexandre.crychat.utilities.IDataReceived;

import android.telephony.SmsManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public void sendMessage(String address, String msg, boolean crypted) {
        String message;

        if(crypted) {
            CryptoServiceProvider crypto = new CryptoServiceProvider("test");
            message = MD5;

            try {
                byte[] cryptedMessage = crypto.Encrypt(msg);
                String base64EncodedMessage = Base64.encodeToString(cryptedMessage, Base64.DEFAULT);
                message += base64EncodedMessage;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            message = msg;
        }
        db.messageDao().insertMessage(new Message(message, frag.getConversation().getConversationID(), DateParser.getCurrentDate()));
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
        Message sms;
        if(o.getClass() == Message.class)
            sms = (Message) o;
        else
            return;

        if(sms.getConversationUUID().compareTo(frag.getConversation().getConversationID()) != 0)
            return;

        String message = sms.getMessage();
        //TODO: change password (input from user)
        CryptoServiceProvider crypto = new CryptoServiceProvider("test");
        if(message.contains(MD5)) {
            message = message.substring(MD5.length());

            //If the message is crypted, Hash the source to fetch the proper conversation
            byte[] cryptedMessage = Base64.decode(message, Base64.DEFAULT);

            try {
                message = crypto.Decrypt(cryptedMessage);
                sms.setMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        frag.messageReceived(sms);
    }

    @Override
    public void afficherConversation(String conversationId, byte[] password) {

    }
}
