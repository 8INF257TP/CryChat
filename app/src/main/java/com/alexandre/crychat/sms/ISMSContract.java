package com.alexandre.crychat.sms;

import com.alexandre.crychat.utilities.IBasePresenter;
import com.alexandre.crychat.utilities.IBaseView;

public interface ISMSContract {
    interface View extends IBaseView<Presenter> {
        @Override
        void setPresenter(Presenter presenter);
        void messageReceived(String sender, String message);
    }

    interface Presenter extends IBasePresenter {
        void getMessages(String groupId);

        @Override
        void subscribe();

        @Override
        void unsubscribe();
    }
}
