package com.alexandre.crychat.SMSReader;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alexandre.crychat.R;

public class SMSReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_main);

        SMSReaderFragment frag = (SMSReaderFragment) getSupportFragmentManager().findFragmentById(R.id.sms_reader_fragment);

        if(frag == null) {
            frag = SMSReaderFragment.getInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.activity_main_frame, frag);
            transaction.commit();
        }

        SMSReaderPresenter presenter = new SMSReaderPresenter(frag);
    }
}
