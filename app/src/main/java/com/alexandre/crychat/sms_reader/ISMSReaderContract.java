package com.alexandre.crychat.sms_reader;

import com.alexandre.crychat.utilities.IBasePresenter;
import com.alexandre.crychat.utilities.IBaseView;

public interface ISMSReaderContract {
    interface View extends IBaseView<Presenter> {
        @Override
        void setPresenter(Presenter presenter);
    }

    interface Presenter extends IBasePresenter {
        void getMessages(String groupId);

        @Override
        void subscribe();

        @Override
        void unsubscribe();
    }
}
