package com.alexandre.crychat.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Conversation.class,
                                    parentColumns = "conversationID",
                                    childColumns = "conversationUUID",
                                    onDelete = CASCADE))
public class Message {

    @PrimaryKey
    private String messageID;
    private String message;
    private String sender;
    private String conversationUUID;

    public Message(String message, String sender, String conversationUUID)
    {
        messageID = UUID.randomUUID().toString();
        this.message = message;
        this.sender = sender;
        this.conversationUUID = conversationUUID;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getConversationUUID() {
        return conversationUUID;
    }
}
