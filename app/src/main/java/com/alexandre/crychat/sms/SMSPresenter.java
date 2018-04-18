package com.alexandre.crychat.sms;

public class SMSPresenter implements ISMSContract.Presenter {

    private ISMSContract.View frag;
    /**
     * Used to identify crypted SMS messages
     *
     * Append MD5 to the beginning of a crypted SMS message
     */
    private final char[] MD5 = "b746dd1ceef69da6afdcbbaf320e018a".toCharArray();

    SMSPresenter(ISMSContract.View fragment)
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
