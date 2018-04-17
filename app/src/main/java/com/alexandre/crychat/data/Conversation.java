package com.alexandre.crychat.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Conversation {
    @PrimaryKey
    private String conversationID;
    private String time;
    private String hashedPass;

    public Conversation(String time, String hashedPass)
    {
        conversationID = UUID.randomUUID().toString();
        this.time = time;
        this.hashedPass = hashedPass;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getTime() {
        return time;
    }

    public String getHashedPass() {
        return hashedPass;
    }
}
