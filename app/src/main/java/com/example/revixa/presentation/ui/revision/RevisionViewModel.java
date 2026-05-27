package com.example.revixa.presentation.ui.revision;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.revixa.RevixaApp;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.domain.usecase.GetDueCardsUseCase;
import com.example.revixa.domain.usecase.ReviewCardUseCase;
import com.example.revixa.utils.Constants;
import com.example.revixa.utils.PreferenceManager;
import com.example.revixa.utils.SpacedRepetition;

import java.util.ArrayList;
import java.util.List;

public class RevisionViewModel extends AndroidViewModel {

    private final CardRepository     cardRepository;
    private final ReviewCardUseCase  reviewCardUseCase;
    private final GetDueCardsUseCase getDueCardsUseCase;
    private final PreferenceManager  prefManager;

    private final MutableLiveData<List<Card>> dueCards      = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer>    currentIndex  = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean>    sessionDone   = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean>    answerVisible = new MutableLiveData<>(false);

    private int hardCount = 0, medCount = 0, easyCount = 0, xpEarned = 0;

    public RevisionViewModel(@NonNull Application application) {
        super(application);
        cardRepository     = RevixaApp.getInstance().getCardRepository();
        reviewCardUseCase  = new ReviewCardUseCase(cardRepository);
        getDueCardsUseCase = new GetDueCardsUseCase(cardRepository);
        prefManager        = PreferenceManager.getInstance(application);
    }

    /** Load due cards — optionally filtered by category */
    public void loadDueCards(long categoryId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Card> cards;
            if (categoryId > 0) {
                // Load ALL cards for this category (not just due), so user can review any
                List<Card> all = cardRepository.getAllCardsSync();
                cards = new ArrayList<>();
                for (Card c : all) {
                    if (c.getCategoryId() == categoryId) cards.add(c);
                }
            } else {
                // Load all due cards
                cards = getDueCardsUseCase.execute();
                // If no due cards, fall back to all cards for the session
                if (cards.isEmpty()) {
                    cards = cardRepository.getAllCardsSync();
                }
            }
            dueCards.postValue(cards);
            currentIndex.postValue(0);
            sessionDone.postValue(false);
            answerVisible.postValue(false);
            hardCount = 0; medCount = 0; easyCount = 0; xpEarned = 0;
        });
    }

    public void rateCard(int rating) {
        List<Card> cards = dueCards.getValue();
        Integer idx = currentIndex.getValue();
        if (cards == null || idx == null || idx >= cards.size()) return;

        final Card card = cards.get(idx);
        AppDatabase.databaseWriteExecutor.execute(() ->
            reviewCardUseCase.execute(card, rating));

        switch (rating) {
            case SpacedRepetition.RATING_HARD:   hardCount++; break;
            case SpacedRepetition.RATING_MEDIUM: medCount++;  break;
            case SpacedRepetition.RATING_EASY:   easyCount++; break;
        }
        xpEarned += Constants.XP_PER_REVISION;
        prefManager.addXp(Constants.XP_PER_REVISION);
        answerVisible.postValue(false);

        int next = idx + 1;
        if (next >= cards.size()) {
            prefManager.addXp(Constants.XP_SESSION_COMPLETE);
            prefManager.updateStreak();
            xpEarned += Constants.XP_SESSION_COMPLETE;
            sessionDone.postValue(true);
        } else {
            currentIndex.postValue(next);
        }
    }

    public void revealAnswer()  { answerVisible.setValue(true); }

    public void goToPrevious() {
        Integer idx = currentIndex.getValue();
        if (idx != null && idx > 0) {
            currentIndex.setValue(idx - 1);
            answerVisible.setValue(false);
        }
    }

    public void skipCard() {
        List<Card> cards = dueCards.getValue();
        Integer idx = currentIndex.getValue();
        if (cards == null || idx == null) return;
        int next = idx + 1;
        if (next >= cards.size()) sessionDone.postValue(true);
        else {
            currentIndex.postValue(next);
            answerVisible.postValue(false);
        }
    }

    public LiveData<List<Card>> getDueCards()      { return dueCards;      }
    public LiveData<Integer>    getCurrentIndex()  { return currentIndex;  }
    public LiveData<Boolean>    getSessionDone()   { return sessionDone;   }
    public LiveData<Boolean>    getAnswerVisible() { return answerVisible; }
    public int getHardCount()  { return hardCount;  }
    public int getMedCount()   { return medCount;   }
    public int getEasyCount()  { return easyCount;  }
    public int getXpEarned()   { return xpEarned;   }
    public int getTotalCards() {
        List<Card> c = dueCards.getValue();
        return c != null ? c.size() : 0;
    }
}
