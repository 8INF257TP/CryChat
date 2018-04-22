package com.alexandre.crychat.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    public void insertMessages(Message... messages);

    @Insert
    public void insertMessage(Message message);

    @Query("SELECT * FROM message")
    public List<Message> getAllMessage();

    @Query("SELECT * FROM message WHERE messageID = :id")
    public List<Message> getMessage(String id);
}
