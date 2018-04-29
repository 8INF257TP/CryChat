package com.alexandre.crychat.conversation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alexandre.crychat.R;
import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.data.Message;
import com.alexandre.crychat.data.adapter.SMSAdapter;
import com.alexandre.crychat.utilities.DateParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationFragment extends Fragment implements IConversationContract.View{
    private IConversationContract.Presenter presenter;
    private SMSAdapter adapter;
    private View view;
    private Conversation conversation;

    public static ConversationFragment getInstance()
    {
        return new ConversationFragment();
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

        conversation = new Conversation();
        conversation.setConversationID(getActivity().getIntent().getStringExtra("EXTRA_ID"));
        conversation.setTime(getActivity().getIntent().getStringExtra("EXTRA_DATE"));
        conversation.setHashedPass(getActivity().getIntent().getStringExtra("EXTRA_PASS"));
        // Creation d'un nouveau ListView
        ListView listView = view.findViewById(R.id.textMessages);

        // creating adapter to fill de ListView
        ArrayList<Message> messages = presenter.getMessages(conversation.getConversationID());
        adapter = new SMSAdapter(view.getContext(), (ArrayList) messages);

        // attaching Adapater to the ListView
        listView.setAdapter(adapter);


        // attaching onClickListener to send button
        Button sendButton = view.findViewById(R.id.send);
        sendButton.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            EditText edit = view.findViewById(R.id.edit_text);

            if(!edit.getText().toString().isEmpty()) {
                final Message sentMsg = new Message(edit.getText().toString(), conversation.getConversationID(), DateParser.getCurrentDate());

                adapter.add(sentMsg);
                adapter.notifyDataSetChanged();
                //TODO:Change crypted to a switch button
                presenter.sendMessage(conversation.getConversationID(), edit.getText().toString(), true);

                edit.getText().clear();
                edit.clearFocus();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(IConversationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void messageReceived(Message message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Conversation getConversation(){
        return this.conversation;
    }
}
