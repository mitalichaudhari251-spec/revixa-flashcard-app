package com.example.revixa;

import android.app.Application;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.data.repository.CardRepositoryImpl;
import com.example.revixa.data.repository.CommunityRepositoryImpl;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.domain.repository.CommunityRepository;
import com.example.revixa.utils.Constants;

public class RevixaApp extends Application {

    private static RevixaApp instance;
    private AppDatabase database;
    private CardRepository cardRepository;
    private CommunityRepository communityRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDependencies();
    }

    private void initDependencies() {
        database = AppDatabase.getInstance(this);
        cardRepository = new CardRepositoryImpl(database.cardDao(), database.categoryDao());
        communityRepository = new CommunityRepositoryImpl(database.cardDao(), database.categoryDao());
    }

    public static RevixaApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public CommunityRepository getCommunityRepository() {
        return communityRepository;
    }
}
