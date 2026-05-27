package com.example.revixa.domain.usecase;

import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.utils.SpacedRepetition;

public class ReviewCardUseCase {
    private final CardRepository repository;

    public ReviewCardUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public void execute(Card card, int rating) {
        if (card == null) return;
        if (rating < SpacedRepetition.RATING_HARD || rating > SpacedRepetition.RATING_EASY) return;
        repository.reviewCard(card, rating);
    }
}
