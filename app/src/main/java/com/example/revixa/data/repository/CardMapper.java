package com.example.revixa.data.repository;

import com.example.revixa.data.local.entity.CardEntity;
import com.example.revixa.data.local.entity.CategoryEntity;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {

    public static Card entityToModel(CardEntity entity) {
        if (entity == null) return null;
        Card card = new Card();
        card.setId(entity.id);
        card.setTitle(entity.title);
        card.setQuestion(entity.question);
        card.setAnswer(entity.answer);
        card.setTags(entity.tags);
        card.setPriority(entity.priority);
        card.setDifficulty(entity.difficulty);
        card.setCategoryId(entity.categoryId != null ? entity.categoryId : 0L);
        card.setCardType(entity.cardType);
        card.setPublic(entity.isPublic);
        card.setCreatedAt(entity.createdAt);
        card.setUpdatedAt(entity.updatedAt);
        card.setEaseFactor(entity.easeFactor);
        card.setIntervalDays(entity.intervalDays);
        card.setRepetitions(entity.repetitions);
        card.setNextReviewDate(entity.nextReviewDate);
        card.setLastReviewDate(entity.lastReviewDate);
        card.setMasteryLevel(entity.masteryLevel);
        card.setReviewCount(entity.reviewCount);
        card.setCorrectCount(entity.correctCount);
        return card;
    }

    public static CardEntity modelToEntity(Card card) {
        if (card == null) return null;
        CardEntity entity = new CardEntity();
        entity.id = card.getId();
        entity.title = card.getTitle();
        entity.question = card.getQuestion();
        entity.answer = card.getAnswer();
        entity.tags = card.getTags();
        entity.priority = card.getPriority();
        entity.difficulty = card.getDifficulty();
        entity.categoryId = card.getCategoryId() > 0 ? card.getCategoryId() : null;
        entity.cardType = card.getCardType();
        entity.isPublic = card.isPublic();
        entity.createdAt = card.getCreatedAt();
        entity.updatedAt = card.getUpdatedAt();
        entity.easeFactor = card.getEaseFactor();
        entity.intervalDays = card.getIntervalDays();
        entity.repetitions = card.getRepetitions();
        entity.nextReviewDate = card.getNextReviewDate();
        entity.lastReviewDate = card.getLastReviewDate();
        entity.masteryLevel = card.getMasteryLevel();
        entity.reviewCount = card.getReviewCount();
        entity.correctCount = card.getCorrectCount();
        return entity;
    }

    public static List<Card> entityListToModelList(List<CardEntity> entities) {
        List<Card> cards = new ArrayList<>();
        if (entities == null) return cards;
        for (CardEntity e : entities) {
            cards.add(entityToModel(e));
        }
        return cards;
    }

    public static Category categoryEntityToModel(CategoryEntity entity) {
        if (entity == null) return null;
        Category cat = new Category();
        cat.setId(entity.id);
        cat.setName(entity.name);
        cat.setColor(entity.color);
        cat.setIcon(entity.icon);
        cat.setDescription(entity.description);
        cat.setCardCount(entity.cardCount);
        cat.setCreatedAt(entity.createdAt);
        return cat;
    }

    public static CategoryEntity categoryModelToEntity(Category category) {
        if (category == null) return null;
        CategoryEntity entity = new CategoryEntity();
        entity.id = category.getId();
        entity.name = category.getName();
        entity.color = category.getColor();
        entity.icon = category.getIcon();
        entity.description = category.getDescription();
        entity.cardCount = category.getCardCount();
        entity.createdAt = category.getCreatedAt();
        return entity;
    }

    public static List<Category> categoryEntityListToModelList(List<CategoryEntity> entities) {
        List<Category> categories = new ArrayList<>();
        if (entities == null) return categories;
        for (CategoryEntity e : entities) {
            categories.add(categoryEntityToModel(e));
        }
        return categories;
    }
}
