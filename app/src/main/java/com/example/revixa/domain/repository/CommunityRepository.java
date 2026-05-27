package com.example.revixa.domain.repository;

import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;

import java.util.List;

public interface CommunityRepository {
    List<Card> getPublicCards();
    List<Category> getPublicCategories();
    void publishDeck(List<Card> cards, Category category);
    void importDeck(List<Card> cards);
}
