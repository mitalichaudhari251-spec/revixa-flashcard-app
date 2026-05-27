package com.example.revixa.di;

import android.content.Context;
import com.example.revixa.data.local.database.AppDatabase;

/**
 * Provides the Room database singleton.
 */
public class DatabaseModule {

    private DatabaseModule() {}

    public static AppDatabase provideDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }
}
