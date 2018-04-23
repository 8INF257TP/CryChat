package com.alexandre.crychat.main_menu;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.alexandre.crychat.R;
import com.alexandre.crychat.conversation.ConversationActivity;
import com.alexandre.crychat.conversation.IConversationContract;
import com.alexandre.crychat.data.adapter.ConvAdapter;
import com.alexandre.crychat.data.AppDatabase;
import com.alexandre.crychat.data.Conversation;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment implements IMainContract.View {
    private ListView listView;
    private List<Conversation> conversations;
    private IMainContract.Presenter presenter;
    private static final int CONTACT_RESULT  = 12345;

    public static MainFragment getInstance() { return new MainFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.list_conversation_fragment, container, false);
        listView = view.findViewById(R.id.conversations);

        conversations = presenter.getConversations();

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
            }
        });

        Button button = view.findViewById(R.id.contact_picker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, CONTACT_RESULT);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(IMainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {

            switch (requestCode){
                case CONTACT_RESULT:
                    contactPicked(data);
                    break;
            }
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo ;
            String name;

            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();

            //Query the content uri
            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            // column index of the phone number
            int  phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);

            Conversation selectedConv = presenter.findConversation(phoneNo);

            Intent intent = new Intent(getActivity(), ConversationActivity.class);
            intent.putExtra("EXTRA_ID", selectedConv.getConversationID());
            intent.putExtra("EXTRA_DATE", selectedConv.getTime());
            intent.putExtra("EXTRA_PASS", selectedConv.getHashedPass());
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
