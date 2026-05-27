package com.example.revixa.presentation.ui.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.revixa.RevixaApp;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.domain.usecase.AddCardUseCase;
import com.example.revixa.domain.usecase.GenerateCardUseCase;
import com.example.revixa.domain.usecase.UpdateCardUseCase;

import java.util.List;

public class CreateCardViewModel extends AndroidViewModel {

    private final CardRepository    cardRepository;
    private final AddCardUseCase    addCardUseCase;
    private final UpdateCardUseCase updateCardUseCase;
    private final GenerateCardUseCase generateCardUseCase;

    private final MutableLiveData<Card>    generatedCard = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saveSuccess   = new MutableLiveData<>();
    private final MutableLiveData<String>  errorMessage  = new MutableLiveData<>();
    private final LiveData<List<Category>> categories;

    public CreateCardViewModel(@NonNull Application application) {
        super(application);
        cardRepository      = RevixaApp.getInstance().getCardRepository();
        addCardUseCase      = new AddCardUseCase(cardRepository);
        updateCardUseCase   = new UpdateCardUseCase(cardRepository);
        generateCardUseCase = new GenerateCardUseCase();
        categories          = cardRepository.getAllCategories();
    }

    public LiveData<List<Category>> getCategories()   { return categories;    }
    public LiveData<Card>    getGeneratedCard()        { return generatedCard; }
    public LiveData<Boolean> getSaveSuccess()          { return saveSuccess;   }
    public LiveData<String>  getErrorMessage()         { return errorMessage;  }

    public void saveCard(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            AddCardUseCase.Result result = addCardUseCase.execute(card);
            if (result.success) {
                saveSuccess.postValue(true);
            } else {
                errorMessage.postValue(result.errorMessage);
            }
        });
    }

    public void updateCard(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            boolean ok = updateCardUseCase.execute(card);
            if (ok) {
                saveSuccess.postValue(true);
            } else {
                errorMessage.postValue("Validation failed. Check all fields.");
            }
        });
    }

    public void generateCard(String categoryName) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Card card = generateCardUseCase.generate(categoryName);
            generatedCard.postValue(card);
        });
    }

    public Card getCardByIdSync(long id) {
        return cardRepository.getCardByIdSync(id);
    }
}
