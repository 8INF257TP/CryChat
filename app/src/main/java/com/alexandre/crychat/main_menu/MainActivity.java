package com.alexandre.crychat.main_menu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alexandre.crychat.R;

public class MainActivity extends AppCompatActivity {
    private MainFragment frag;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.sms_reader_fragment);

        if(frag == null) {
            frag = MainFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.activity_main_frame, frag);
            transaction.commit();
        }
        presenter = new MainPresenter(this, frag);
    }
}
