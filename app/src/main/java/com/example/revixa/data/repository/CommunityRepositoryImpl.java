package com.example.revixa.data.repository;

import com.example.revixa.data.local.dao.CardDao;
import com.example.revixa.data.local.dao.CategoryDao;
import com.example.revixa.data.local.entity.CardEntity;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.domain.repository.CommunityRepository;

import java.util.ArrayList;
import java.util.List;

public class CommunityRepositoryImpl implements CommunityRepository {

    private final CardDao cardDao;
    private final CategoryDao categoryDao;

    public CommunityRepositoryImpl(CardDao cardDao, CategoryDao categoryDao) {
        this.cardDao = cardDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Card> getPublicCards() {
        return CardMapper.entityListToModelList(cardDao.getPublicCardsSync());
    }

    @Override
    public List<Category> getPublicCategories() {
        return CardMapper.categoryEntityListToModelList(categoryDao.getAllCategoriesSync());
    }

    @Override
    public void publishDeck(List<Card> cards, Category category) {
        for (Card card : cards) {
            card.setPublic(true);
            cardDao.updateCard(CardMapper.modelToEntity(card));
        }
    }

    @Override
    public void importDeck(List<Card> cards) {
        for (Card card : cards) {
            card.setId(0);
            card.setCreatedAt(System.currentTimeMillis());
            card.setUpdatedAt(System.currentTimeMillis());
            cardDao.insertCard(CardMapper.modelToEntity(card));
        }
    }

    public List<Card> getSampleCommunityDecks() {
        List<Card> sampleCards = new ArrayList<>();

        String[][] samples = {
            {"Newton's First Law", "What is Newton's First Law of Motion?", "An object at rest stays at rest, and an object in motion stays in motion unless acted upon by an external force.", "physics,motion,newton", "Physics"},
            {"Newton's Second Law", "State Newton's Second Law of Motion.", "Force equals mass times acceleration: F = ma", "physics,force,acceleration", "Physics"},
            {"Ohm's Law", "What is Ohm's Law?", "Voltage (V) = Current (I) × Resistance (R)", "physics,electricity,ohm", "Physics"},
            {"Photosynthesis", "What is the equation for photosynthesis?", "6CO₂ + 6H₂O + light → C₆H₁₂O₆ + 6O₂", "biology,photosynthesis,plants", "Biology"},
            {"Pythagorean Theorem", "State the Pythagorean Theorem.", "In a right triangle: a² + b² = c², where c is the hypotenuse.", "math,geometry,triangle", "Mathematics"},
            {"Mitosis Phases", "List the phases of mitosis.", "Prophase → Metaphase → Anaphase → Telophase → Cytokinesis", "biology,cell,division", "Biology"},
            {"Binary Search", "What is the time complexity of Binary Search?", "O(log n) — it halves the search space at each step.", "cs,algorithms,search", "Computer Sci"},
            {"Boyle's Law", "What does Boyle's Law state?", "At constant temperature, pressure × volume = constant (P₁V₁ = P₂V₂)", "chemistry,gas,pressure", "Chemistry"},
            {"DNA Structure", "Who discovered the structure of DNA?", "Watson and Crick (1953), with X-ray data from Rosalind Franklin", "biology,dna,history", "Biology"},
            {"Quadratic Formula", "What is the quadratic formula?", "x = (−b ± √(b²−4ac)) / 2a, for ax² + bx + c = 0", "math,algebra,formula", "Mathematics"},
        };

        for (int i = 0; i < samples.length; i++) {
            Card card = new Card();
            card.setId(-(i + 1));
            card.setTitle(samples[i][0]);
            card.setQuestion(samples[i][1]);
            card.setAnswer(samples[i][2]);
            card.setTags(samples[i][3]);
            card.setCategoryName(samples[i][4]);
            card.setPublic(true);
            card.setDifficulty(1);
            card.setPriority(1);
            sampleCards.add(card);
        }
        return sampleCards;
    }
}
