package com.example.revixa.domain.usecase;

import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.utils.ValidationUtils;

public class UpdateCardUseCase {
    private final CardRepository repository;

    public UpdateCardUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Card card) {
        if (!ValidationUtils.isValidTitle(card.getTitle())) return false;
        if (!ValidationUtils.isValidQuestion(card.getQuestion())) return false;
        if (!ValidationUtils.isValidAnswer(card.getAnswer())) return false;

        card.setTitle(ValidationUtils.sanitize(card.getTitle()));
        card.setQuestion(ValidationUtils.sanitize(card.getQuestion()));
        card.setAnswer(ValidationUtils.sanitize(card.getAnswer()));

        repository.updateCard(card);
        return true;
    }
}
