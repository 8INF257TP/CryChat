package com.alexandre.crychat.data;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Conversation.class, Message.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase instance = null;
    private static final String DATABASE_NAME = "conversation-db";

    public abstract MessageDao messageDao();
    public abstract ConversationDao conversationDao();

    public static AppDatabase getInstance(final Context context) {
        if(instance == null) {
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    protected AppDatabase() {
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
