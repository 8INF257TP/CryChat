package com.alexandre.crychat.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ConversationDao {
    @Query("SELECT * FROM conversation WHERE conversationID = :convID")
    public List<Conversation> loadConversation(String convID);

    @Query("SELECT * FROM message " +
            "WHERE message.conversationUUID = :convID")
    public List<Message> loadConversationMessages(String convID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Conversation... conversations);

    @Insert
    void insertConversation(Conversation conversation);

    @Delete
    void delete(Conversation conversation);
}
