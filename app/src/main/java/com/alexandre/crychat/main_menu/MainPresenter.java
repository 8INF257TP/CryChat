package com.alexandre.crychat.main_menu;

import android.app.Fragment;
import android.content.Context;

import com.alexandre.crychat.data.Conversation;

import java.util.ArrayList;

/**
 * Created by alexa on 2018-04-23.
 */

public class MainPresenter implements IMainContract.Presenter {
    IMainContract.View frag;

    MainPresenter(Context context, IMainContract.View frag){
        this.frag = frag;
        frag.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public ArrayList<Conversation> getConversations() {
        return null;
    }
}
