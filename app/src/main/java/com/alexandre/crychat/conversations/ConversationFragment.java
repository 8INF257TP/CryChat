package com.alexandre.crychat.conversations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexandre.crychat.R;
import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;
import com.alexandre.crychat.sms.SMSActivity;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment{
    private ListView listView;
    private AppDatabase db;
    private List<Conversation> conversations;

    public static ConversationFragment getInstance() { return new ConversationFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.list_conversation_fragment, container, false);

        db = AppDatabase.getInstance(view.getContext());
        listView = view.findViewById(R.id.conversations);

        conversations = db.conversationDao().loadAllConversation();

        ConvAdapter adapter = new ConvAdapter(view.getContext(), (ArrayList<Conversation>) conversations);

        Conversation test = new Conversation();
        test.setConversationID("+15814901733");
        test.setTime("1234");
        test.setHashedPass("test");

        //db.conversationDao().insertConversation(test);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Conversation selectedConversation = (Conversation) adapterView.getItemAtPosition(position);



                Intent intent = new Intent(getActivity(), SMSActivity.class);
                intent.putExtra("EXTRA_ID", selectedConversation.getConversationID());
                intent.putExtra("EXTRA_DATE", selectedConversation.getTime());
                intent.putExtra("EXTRA_PASS", selectedConversation.getHashedPass());
                startActivity(intent);
            }
        });

                adapter.add(test);

        return view;
    }
}
