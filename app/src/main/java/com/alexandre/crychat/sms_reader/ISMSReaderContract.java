package com.alexandre.crychat.SMSReader;

import com.alexandre.crychat.Utilities.IBasePresenter;
import com.alexandre.crychat.Utilities.IBaseView;

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
