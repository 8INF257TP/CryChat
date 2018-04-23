package com.alexandre.crychat.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Conversation {
    @PrimaryKey @NonNull
    private String conversationID;

    @ColumnInfo(name="time")
    private String time;
    @ColumnInfo(name="hashed_password")
    private String hashedPass;

    public Conversation() {}
    public Conversation(@NonNull String conversationID, String time, String hashedPass)
    {
        this.conversationID = conversationID;
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

    public void setConversationID(String conversationID){this.conversationID = conversationID;}
    public void setTime(String time){this.time = time;}
    public void setHashedPass(String hashedPass){this.hashedPass = hashedPass;}
}
