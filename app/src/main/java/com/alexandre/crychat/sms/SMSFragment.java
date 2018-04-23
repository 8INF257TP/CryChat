package com.alexandre.crychat.sms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alexandre.crychat.R;
import com.alexandre.crychat.conversations.ConversationActivity;
import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.data.Message;

import java.util.ArrayList;

public class SMSFragment extends Fragment implements ISMSContract.View{
    private ISMSContract.Presenter presenter;
    private SMSAdapter adapter;
    private View view;
    private Conversation conversation;

    private AppDatabase db;

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

        // set the conversation with intent from ConversationActivity
        conversation = new Conversation();
        conversation.setConversationID(getActivity().getIntent().getStringExtra("EXTRA_ID"));
        conversation.setTime(getActivity().getIntent().getStringExtra("EXTRA_DATE"));
        conversation.setHashedPass(getActivity().getIntent().getStringExtra("EXTRA_PASS"));

        // generating new db instance
        db = AppDatabase.getInstance(view.getContext());

        // Creation d'un nouveau ListView
        ListView listView = view.findViewById(R.id.textMessages);

        // creating adapter to fill de ListView
        ArrayList<Message> messages = new ArrayList<>();
        adapter = new SMSAdapter(view.getContext(), messages);

        // attaching Adapater to the ListView
        listView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.conversationDao().insertConversation(conversation);
            }
        }).start();


        // attaching onClickListener to send button
        Button sendButton = view.findViewById(R.id.send);
        sendButton.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            EditText edit = view.findViewById(R.id.edit_text);
            final Message sentMsg = new Message(edit.getText().toString(), "Alex", "ConvoTest", "2018-20-04");

            adapter.add(sentMsg);
            adapter.notifyDataSetChanged();

            //db.messageDao().insertMessage(sentMsg);


            presenter.sendMessage(conversation.getConversationID(), edit.getText().toString());
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
        final Message receivedMsg = new Message(message, sender, conversation.getConversationID(), "2018-04-22");

        adapter.add(receivedMsg);
        adapter.notifyDataSetChanged();

        new Thread(new Runnable(){
            @Override
            public void run(){
                db.messageDao().insertMessage(receivedMsg);
            }
        });
    }
}
