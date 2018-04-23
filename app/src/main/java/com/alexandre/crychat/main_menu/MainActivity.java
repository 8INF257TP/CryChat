package com.alexandre.crychat.main_menu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alexandre.crychat.R;
import com.alexandre.crychat.service.SMSService;

public class MainActivity extends AppCompatActivity {
    private MainFragment frag;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkIfServiceRunning(SMSService.class)) {
            Intent intent = new Intent(this, SMSService.class);
            startService(intent);
        }

        frag = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.sms_reader_fragment);

        if(frag == null) {
            frag = MainFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.activity_main_frame, frag);
            transaction.commit();
        }
        presenter = new MainPresenter(this, frag);
    }

    private boolean checkIfServiceRunning(Class<?> classe) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (classe.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
