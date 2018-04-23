package com.alexandre.crychat.main_menu;

import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.utilities.IBasePresenter;
import com.alexandre.crychat.utilities.IBaseView;

import java.util.ArrayList;

/**
 * Created by alexa on 2018-04-23.
 */

public interface IMainContract {
    interface View extends IBaseView<Presenter>{
        @Override
        void setPresenter(Presenter presenter);
    }

    interface Presenter extends IBasePresenter{
        ArrayList<Conversation> getConversations();
    }
}
