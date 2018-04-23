package com.alexandre.crychat.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alexandre.crychat.R;
import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.main_menu.MainFragment;

public class ConversationActivity extends AppCompatActivity {
    private ConversationPresenter presenter;
    private ConversationFragment frag;

    @Override
    protected void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_main);

        frag = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.sms_reader_fragment);

        if(frag == null) {
            frag = ConversationFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.activity_main_frame, frag);
            transaction.commit();
        }
        presenter = new ConversationPresenter(this, frag);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String smsApp = Telephony.Sms.getDefaultSmsPackage(this);
        if(!smsApp.equals(this.getPackageName())) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        } else {
            presenter.subscribe();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }
}
