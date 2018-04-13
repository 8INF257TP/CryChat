package com.alexandre.crychat.sms_reader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandre.crychat.R;

public class SMSReaderFragment extends Fragment implements ISMSReaderContract.View{

    private ISMSReaderContract.Presenter presenter;

    public static SMSReaderFragment getInstance()
    {
        return new SMSReaderFragment();
    }

    @Override
    public void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundle) {
        super.onCreateView(inflater, container, savedBundle);

        //On dessine le layout
        View view = inflater.inflate(R.layout.sms_reader_fragment, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(ISMSReaderContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
