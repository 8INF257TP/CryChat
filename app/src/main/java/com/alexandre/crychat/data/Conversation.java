package com.alexandre.crychat.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Conversation {
    @PrimaryKey
    private String conversationID;
    private String time;

    public Conversation(String time)
    {
        conversationID = UUID.randomUUID().toString();
        this.time = time;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getTime() {
        return time;
    }
}
