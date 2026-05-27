package com.example.revixa.di;

import android.content.Context;

import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.data.repository.CardRepositoryImpl;
import com.example.revixa.data.repository.CommunityRepositoryImpl;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.domain.repository.CommunityRepository;

/**
 * Manual dependency injection module.
 * Provides singleton instances of all major dependencies.
 */
public class AppModule {

    private static AppModule instance;
    private final AppDatabase     database;
    private final CardRepository  cardRepository;
    private final CommunityRepository communityRepository;

    private AppModule(Context context) {
        database             = DatabaseModule.provideDatabase(context);
        cardRepository       = RepositoryModule.provideCardRepository(database);
        communityRepository  = RepositoryModule.provideCommunityRepository(database);
    }

    public static synchronized AppModule getInstance(Context context) {
        if (instance == null) {
            instance = new AppModule(context.getApplicationContext());
        }
        return instance;
    }

    public AppDatabase getDatabase()               { return database;             }
    public CardRepository getCardRepository()      { return cardRepository;       }
    public CommunityRepository getCommunityRepository() { return communityRepository; }
}
