package com.alexandre.crychat.conversation;

import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.utilities.IBasePresenter;
import com.alexandre.crychat.utilities.IBaseView;

import java.util.ArrayList;

public interface IConversationContract {
    interface View extends IBaseView<Presenter> {
        @Override
        void setPresenter(Presenter presenter);
        void messageReceived(Message message);
        Conversation getConversation();
    }

    interface Presenter extends IBasePresenter {
        ArrayList<Message> getMessages(String groupId);
        void sendMessage(String address, String msg, boolean crypted);
        void afficherConversation(String conversationId, byte[] password);


        @Override
        void subscribe();

        @Override
        void unsubscribe();
    }
}
