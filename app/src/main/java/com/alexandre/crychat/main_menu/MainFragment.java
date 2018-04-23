package com.alexandre.crychat.main_menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexandre.crychat.R;
import com.alexandre.crychat.conversation.ConversationActivity;
import com.alexandre.crychat.conversation.IConversationContract;
import com.alexandre.crychat.data.adapter.ConvAdapter;
import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements IMainContract.View {
    private ListView listView;
    private AppDatabase db;
    private List<Conversation> conversations;
    private IMainContract.Presenter presenter;

    public static MainFragment getInstance() { return new MainFragment(); }

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

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Conversation selectedConversation = (Conversation) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ConversationActivity.class);
                intent.putExtra("EXTRA_ID", selectedConversation.getConversationID());
                intent.putExtra("EXTRA_DATE", selectedConversation.getTime());
                intent.putExtra("EXTRA_PASS", selectedConversation.getHashedPass());
                startActivity(intent);

               // presenter.afficherConversation(selectedConversation.getConversationID(), Base64.decode(selectedConversation.getHashedPass(), Base64.DEFAULT));

            }
        });

        return view;
    }

    @Override
    public void setPresenter(IMainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
