package com.alexandre.crychat.conversations;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.alexandre.crychat.R;

/**
 * Created by alexa on 2018-04-22.
 */

public class ConversationActivity extends AppCompatActivity{
    private ConversationFragment frag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation_fragment);

        if (frag == null){
            frag = ConversationFragment.getInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.activity_main_frame, frag);
            transaction.commit();
        }
    }
}
