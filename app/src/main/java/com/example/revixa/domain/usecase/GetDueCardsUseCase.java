package com.example.revixa.domain.usecase;

import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.repository.CardRepository;

import java.util.List;

public class GetDueCardsUseCase {
    private final CardRepository repository;

    public GetDueCardsUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> execute() {
        return repository.getDueCardsSync(System.currentTimeMillis());
    }
}
