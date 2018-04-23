package com.alexandre.crychat.main_menu;

import android.app.Fragment;
import android.content.Context;

import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexa on 2018-04-23.
 */

public class MainPresenter implements IMainContract.Presenter {
    IMainContract.View frag;
    private AppDatabase db;

    MainPresenter(Context context, IMainContract.View frag){
        this.frag = frag;
        frag.setPresenter(this);
        db = AppDatabase.getInstance(context);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public ArrayList<Conversation> getConversations() {
        return (ArrayList) db.conversationDao().loadAllConversation();
    }


    // Creates new conversation and returns it
    public Conversation newConversation(String conversationID){
        Conversation newConversation = new Conversation(conversationID, "2018-23-04", "test");

        db.conversationDao().insertConversation(newConversation);
        return newConversation;
    }


    // Find a conversation from a contact, If not exists, creates new Conversation
    public Conversation findConversation(String conversationID){
        List<Conversation> conv = db.conversationDao().loadConversation(conversationID);

        if(conv.isEmpty())
            return newConversation(conversationID);
        else
            return conv.get(0);
    }
}
