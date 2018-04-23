package com.alexandre.crychat.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alexandre.crychat.R;
import com.alexandre.crychat.data.Conversation;

import java.util.ArrayList;

/**
 * Created by alexa on 2018-04-22.
 */

public class ConvAdapter extends ArrayAdapter<Conversation> {
    public ConvAdapter(Context context, ArrayList<Conversation> conversations) {super(context, 0, conversations);}

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        Conversation conversation = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conversation_adapter, parent, false);
        }

        TextView convID = (TextView) convertView.findViewById(R.id.conv_id);


        try {
            convID.setText(conversation.getConversationID());
        }
        catch (NullPointerException exception){
            exception.printStackTrace();
        }

        return convertView;
    }
}
