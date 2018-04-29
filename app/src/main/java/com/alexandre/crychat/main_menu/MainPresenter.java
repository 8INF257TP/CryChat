package com.alexandre.crychat.main_menu;

import android.app.Fragment;
import android.content.Context;

import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.receivers.SmsReceiver;
import com.alexandre.crychat.utilities.DateParser;
import com.alexandre.crychat.utilities.IDataReceived;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexa on 2018-04-23.
 */

public class MainPresenter implements IMainContract.Presenter, IDataReceived {
    IMainContract.View frag;
    private AppDatabase db;

    MainPresenter(Context context, IMainContract.View frag){
        this.frag = frag;
        frag.setPresenter(this);
        db = AppDatabase.getInstance(context);
        subscribe();
    }

    @Override
    public void subscribe() {
        SmsReceiver.addListener(this);
    }

    @Override
    public void unsubscribe() {
        SmsReceiver.removeListener();
    }

    @Override
    public ArrayList<Conversation> getConversations() {
        return (ArrayList) db.conversationDao().loadAllConversation();
    }


    // Creates new conversation and returns it
    public Conversation newConversation(String conversationID){
        Conversation newConversation = new Conversation(conversationID, DateParser.getCurrentDate(), "test");

        db.conversationDao().insertConversation(newConversation);
        return newConversation;
    }


    // Find a conversation from a contact, If not exists, creates new Conversation
    public Conversation findConversation(String conversationID){
        List<Conversation> conv = db.conversationDao().loadConversation(conversationID);

        if(conv.isEmpty())
            return newConversation(conversationID);
        else
            return conv.get(0); //TODO: ???
    }

    @Override
    public void onDataReceived(Object o) {
        Conversation received;

        if(o.getClass() == Conversation.class)
            received = (Conversation) o;
        else
            return;

        this.frag.conversationCreated(received);
    }
}
