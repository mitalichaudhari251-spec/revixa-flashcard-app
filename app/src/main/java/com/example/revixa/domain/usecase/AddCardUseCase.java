package com.example.revixa.domain.usecase;

import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.utils.ValidationUtils;

public class AddCardUseCase {
    private final CardRepository repository;

    public AddCardUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public Result execute(Card card) {
        if (!ValidationUtils.isValidTitle(card.getTitle()))
            return Result.error("Title must be between 2 and 100 characters.");
        if (!ValidationUtils.isValidQuestion(card.getQuestion()))
            return Result.error("Question must be between 5 and 500 characters.");
        if (!ValidationUtils.isValidAnswer(card.getAnswer()))
            return Result.error("Answer cannot be empty.");

        card.setTitle(ValidationUtils.sanitize(card.getTitle()));
        card.setQuestion(ValidationUtils.sanitize(card.getQuestion()));
        card.setAnswer(ValidationUtils.sanitize(card.getAnswer()));

        long id = repository.insertCard(card);
        if (id <= 0) return Result.error("Failed to save card. Please try again.");
        return Result.success(id);
    }

    public static class Result {
        public final boolean success;
        public final String  errorMessage;
        public final long    insertedId;

        private Result(boolean success, String errorMessage, long insertedId) {
            this.success = success;
            this.errorMessage = errorMessage;
            this.insertedId = insertedId;
        }

        public static Result success(long id) { return new Result(true, null, id); }
        public static Result error(String msg) { return new Result(false, msg, -1); }
    }
}
