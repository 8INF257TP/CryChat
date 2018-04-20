package com.alexandre.crychat.sms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.alexandre.crychat.R;
import com.alexandre.crychat.data.Message;

import java.util.ArrayList;

public class SMSFragment extends Fragment implements ISMSContract.View{
    private ISMSContract.Presenter presenter;
    private Button sendButton;
    private SMSAdapter adapter;
    private View view;

    public static SMSFragment getInstance()
    {
        return new SMSFragment();
    }

    @Override
    public void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundle) {
        super.onCreateView(inflater, container, savedBundle);

        //On dessine le layout
        view = inflater.inflate(R.layout.sms_reader_fragment, container, false);
        // Creation d'un nouveau ListView
        ListView listView = (ListView)view.findViewById(R.id.textMessages);

        // creating adapter to fill de ListView
        ArrayList<Message> messages = new ArrayList<>();
        adapter = new SMSAdapter(view.getContext(), messages);

        // attaching Adapater to the ListView
        listView.setAdapter(adapter);

        //**************MESSAGE DE TEST***************/
        Message test = new Message("test", "Alexandre", "ConvoTest");
        adapter.add(test);

        // attaching onClickListener to send button
        sendButton = view.findViewById(R.id.send);
        sendButton.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            EditText edit = view.findViewById(R.id.edit_text);
            Message sentMsg = new Message(edit.getText().toString(), "Alex", "ConvoTest");

            adapter.add(sentMsg);
            presenter.sendMessage(edit.getText().toString());
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(ISMSContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void messageReceived(String sender, String message) {
    }
}
