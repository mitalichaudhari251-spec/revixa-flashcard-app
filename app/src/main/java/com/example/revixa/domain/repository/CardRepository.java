package com.example.revixa.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;

import java.util.List;

public interface CardRepository {

    // Card operations
    long insertCard(Card card);
    void updateCard(Card card);
    void deleteCard(Card card);
    void deleteCardById(long id);

    LiveData<Card> getCardById(long id);
    Card getCardByIdSync(long id);
    LiveData<List<Card>> getAllCards();
    List<Card> getAllCardsSync();
    LiveData<List<Card>> getCardsByCategory(long categoryId);
    LiveData<List<Card>> getDueCards(long currentTime);
    List<Card> getDueCardsSync(long currentTime);

    LiveData<Integer> getTotalCardCount();
    LiveData<Integer> getDueCardCount(long currentTime);
    int getMasteredCardCount();
    int getWeakCardCount();

    LiveData<List<Card>> searchCards(String query);
    void reviewCard(Card card, int rating);

    // Category operations
    long insertCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Category category);
    LiveData<List<Category>> getAllCategories();
    List<Category> getAllCategoriesSync();
    Category getCategoryByIdSync(long id);
    int getCategoryCount();
}
