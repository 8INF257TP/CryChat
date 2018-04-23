package com.alexandre.crychat.conversation;

import com.alexandre.crychat.utilities.IBasePresenter;
import com.alexandre.crychat.utilities.IBaseView;

public interface IConversationContract {
    interface View extends IBaseView<Presenter> {
        @Override
        void setPresenter(Presenter presenter);
        void messageReceived(String sender, String message);
    }

    interface Presenter extends IBasePresenter {
        void getMessages(String groupId);
        void sendMessage(String address, String msg);
        void afficherConversation(String conversationId, byte[] password);

        @Override
        void subscribe();

        @Override
        void unsubscribe();
    }
}
