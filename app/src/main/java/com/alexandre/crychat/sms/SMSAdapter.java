package com.alexandre.crychat.sms;

import com.alexandre.crychat.R;
import com.alexandre.crychat.data.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexa on 2018-04-18.
 */

public class SMSAdapter extends ArrayAdapter<Message>{
    public SMSAdapter(Context context, ArrayList<Message> messages){
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // getting data
        Message newMessage = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.received_message, parent, false);
        }

        // creating ListView items
        TextView dateReceived = (TextView)convertView.findViewById(R.id.dateReceived);
        TextView theMessage = (TextView)convertView.findViewById(R.id.theMessage);

        theMessage.setText(newMessage.getMessage());
        dateReceived.setText(newMessage.getMessageID());

        return convertView;
    }
}
