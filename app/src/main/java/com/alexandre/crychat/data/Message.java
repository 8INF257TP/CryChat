package com.alexandre.crychat.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Conversation.class,
                                    parentColumns = "conversationID",
                                    childColumns = "conversationUUID",
                                    onDelete = CASCADE))
public class Message {

    @PrimaryKey @NonNull
    private String messageID;

    @ColumnInfo(name="date") @NonNull
    private String date;
    @ColumnInfo(name="message") @NonNull
    private String message;
    @ColumnInfo(name="conversationUUID") @NonNull
    private String conversationUUID;

    public Message(String message, String conversationUUID, String date)
    {
        messageID = UUID.randomUUID().toString();
        this.message = message;
        this.conversationUUID = conversationUUID;
        this.date = date;
    }

    public Message(){}

    public String getMessageID() {
        return messageID;
    }
    public String getMessage() {
        return message;
    }
    public String getConversationUUID() {
        return conversationUUID;
    }
    public String getDate() { return date; }

    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setDate (String date) {this.date = date;}
    public void setMessage(String message) { this.message = message; }
    public void setConversationUUID(String conversationUUID) {this.conversationUUID = conversationUUID;}

}
