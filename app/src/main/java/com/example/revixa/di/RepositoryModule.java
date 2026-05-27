package com.example.revixa.di;

import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.data.repository.CardRepositoryImpl;
import com.example.revixa.data.repository.CommunityRepositoryImpl;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.domain.repository.CommunityRepository;

/**
 * Provides repository instances, wiring DAOs from the database.
 */
public class RepositoryModule {

    private RepositoryModule() {}

    public static CardRepository provideCardRepository(AppDatabase db) {
        return new CardRepositoryImpl(db.cardDao(), db.categoryDao());
    }

    public static CommunityRepository provideCommunityRepository(AppDatabase db) {
        return new CommunityRepositoryImpl(db.cardDao(), db.categoryDao());
    }
}
