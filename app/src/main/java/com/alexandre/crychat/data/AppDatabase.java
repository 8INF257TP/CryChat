package com.alexandre.crychat.data;

public class AppDatabase {
    private static final AppDatabsse ourInstance = new AppDatabsse();

    public static AppDatabsse getInstance() {
        return ourInstance;
    }

    private AppDatabase() {
    }
}
