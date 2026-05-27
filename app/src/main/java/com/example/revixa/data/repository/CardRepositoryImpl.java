package com.example.revixa.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.revixa.data.local.dao.CardDao;
import com.example.revixa.data.local.dao.CategoryDao;
import com.example.revixa.data.local.entity.CardEntity;
import com.example.revixa.data.local.entity.CategoryEntity;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.utils.SpacedRepetition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CardRepositoryImpl implements CardRepository {

    private final CardDao     cardDao;
    private final CategoryDao categoryDao;

    // In-memory category cache — loaded once on background thread, safe to read anywhere
    private final Map<Long, String> categoryNameCache = new ConcurrentHashMap<>();

    public CardRepositoryImpl(CardDao cardDao, CategoryDao categoryDao) {
        this.cardDao     = cardDao;
        this.categoryDao = categoryDao;
        // Pre-load category cache on background thread at construction
        AppDatabase.databaseWriteExecutor.execute(this::refreshCategoryCache);
    }

    private void refreshCategoryCache() {
        List<CategoryEntity> cats = categoryDao.getAllCategoriesSync();
        if (cats != null) {
            categoryNameCache.clear();
            for (CategoryEntity c : cats) {
                categoryNameCache.put(c.id, c.name != null ? c.name : "General");
            }
        }
    }

    // ── Pure in-memory enrichment — NO DB calls, safe on any thread ────────
    private List<Card> enrich(List<CardEntity> entities) {
        if (entities == null) return new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        for (CardEntity e : entities) {
            Card card = CardMapper.entityToModel(e);
            if (e.categoryId != null) {
                String name = categoryNameCache.get(e.categoryId);
                card.setCategoryName(name != null ? name : "General");
            } else {
                card.setCategoryName("General");
            }
            cards.add(card);
        }
        return cards;
    }

    private Card enrichOne(CardEntity e) {
        if (e == null) return null;
        Card card = CardMapper.entityToModel(e);
        if (e.categoryId != null) {
            String name = categoryNameCache.get(e.categoryId);
            card.setCategoryName(name != null ? name : "General");
        } else {
            card.setCategoryName("General");
        }
        return card;
    }

    // ── insert / update / delete ───────────────────────────────────────────
    @Override
    public long insertCard(Card card) {
        card.setCreatedAt(System.currentTimeMillis());
        card.setUpdatedAt(System.currentTimeMillis());
        long id = cardDao.insertCard(CardMapper.modelToEntity(card));
        categoryDao.refreshCardCounts();
        refreshCategoryCache();
        return id;
    }

    @Override
    public void updateCard(Card card) {
        card.setUpdatedAt(System.currentTimeMillis());
        cardDao.updateCard(CardMapper.modelToEntity(card));
        categoryDao.refreshCardCounts();
    }

    @Override
    public void deleteCard(Card card) {
        cardDao.deleteCard(CardMapper.modelToEntity(card));
        categoryDao.refreshCardCounts();
        refreshCategoryCache();
    }

    @Override
    public void deleteCardById(long id) {
        cardDao.deleteById(id);
        categoryDao.refreshCardCounts();
        refreshCategoryCache();
    }

    // ── LiveData queries — enrich() is now pure in-memory, safe on main thread
    @Override
    public LiveData<Card> getCardById(long id) {
        return Transformations.map(cardDao.getCardById(id), this::enrichOne);
    }

    @Override
    public Card getCardByIdSync(long id) {
        return enrichOne(cardDao.getCardByIdSync(id));
    }

    @Override
    public LiveData<List<Card>> getAllCards() {
        return Transformations.map(cardDao.getAllCards(), this::enrich);
    }

    @Override
    public List<Card> getAllCardsSync() {
        return enrich(cardDao.getAllCardsSync());
    }

    @Override
    public LiveData<List<Card>> getCardsByCategory(long categoryId) {
        return Transformations.map(cardDao.getCardsByCategory(categoryId), this::enrich);
    }

    @Override
    public LiveData<List<Card>> getDueCards(long currentTime) {
        return Transformations.map(cardDao.getDueCards(currentTime), this::enrich);
    }

    @Override
    public List<Card> getDueCardsSync(long currentTime) {
        return enrich(cardDao.getDueCardsSync(currentTime));
    }

    @Override
    public LiveData<Integer> getTotalCardCount() {
        return cardDao.getTotalCardCount();
    }

    @Override
    public LiveData<Integer> getDueCardCount(long currentTime) {
        return cardDao.getDueCardCount(currentTime);
    }

    @Override
    public int getMasteredCardCount() { return cardDao.getMasteredCardCount(); }

    @Override
    public int getWeakCardCount()     { return cardDao.getWeakCardCount();     }

    @Override
    public LiveData<List<Card>> searchCards(String query) {
        return Transformations.map(
            cardDao.searchCards("%" + query + "%"), this::enrich);
    }

    @Override
    public void reviewCard(Card card, int rating) {
        SpacedRepetition.Result result = SpacedRepetition.calculate(
            card.getEaseFactor(), card.getIntervalDays(),
            card.getRepetitions(), rating);
        card.setEaseFactor(result.newEaseFactor);
        card.setIntervalDays(result.newInterval);
        card.setRepetitions(result.newRepetitions);
        card.setNextReviewDate(result.nextReviewDate);
        card.setLastReviewDate(System.currentTimeMillis());
        card.setReviewCount(card.getReviewCount() + 1);
        if (rating >= 2) card.setCorrectCount(card.getCorrectCount() + 1);
        int mastery = Math.min(100,
            (card.getCorrectCount() * 100) / Math.max(1, card.getReviewCount()));
        card.setMasteryLevel(mastery);
        card.setUpdatedAt(System.currentTimeMillis());
        cardDao.updateCard(CardMapper.modelToEntity(card));
        categoryDao.refreshCardCounts();
    }

    // ── categories ─────────────────────────────────────────────────────────
    @Override
    public long insertCategory(Category category) {
        long id = categoryDao.insertCategory(CardMapper.categoryModelToEntity(category));
        refreshCategoryCache();
        return id;
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(CardMapper.categoryModelToEntity(category));
        refreshCategoryCache();
    }

    @Override
    public void deleteCategory(Category category) {
        categoryDao.deleteCategory(CardMapper.categoryModelToEntity(category));
        refreshCategoryCache();
    }

    @Override
    public LiveData<List<Category>> getAllCategories() {
        return Transformations.map(categoryDao.getAllCategories(),
            CardMapper::categoryEntityListToModelList);
    }

    @Override
    public List<Category> getAllCategoriesSync() {
        return CardMapper.categoryEntityListToModelList(categoryDao.getAllCategoriesSync());
    }

    @Override
    public Category getCategoryByIdSync(long id) {
        return CardMapper.categoryEntityToModel(categoryDao.getCategoryByIdSync(id));
    }

    @Override
    public int getCategoryCount() { return categoryDao.getCategoryCount(); }
}
