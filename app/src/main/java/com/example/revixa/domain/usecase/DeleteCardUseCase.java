package com.example.revixa.domain.usecase;

import com.example.revixa.domain.repository.CardRepository;

public class DeleteCardUseCase {
    private final CardRepository repository;

    public DeleteCardUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public void execute(long cardId) {
        repository.deleteCardById(cardId);
    }
}
