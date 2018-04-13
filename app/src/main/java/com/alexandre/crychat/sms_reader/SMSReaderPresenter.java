package com.alexandre.crychat.SMSReader;

public class SMSReaderPresenter implements ISMSReaderContract.Presenter {

    private ISMSReaderContract.View frag;

    SMSReaderPresenter(ISMSReaderContract.View fragment)
    {
        frag = fragment;
        frag.setPresenter(this);
    }

    @Override
    public void getMessages(String groupId) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
