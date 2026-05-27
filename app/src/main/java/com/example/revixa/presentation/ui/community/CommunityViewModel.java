package com.example.revixa.presentation.ui.community;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.revixa.RevixaApp;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.data.repository.CommunityRepositoryImpl;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.domain.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;

public class CommunityViewModel extends AndroidViewModel {

    private final CommunityRepositoryImpl communityRepository;
    private final CardRepository          cardRepository;

    private final MutableLiveData<List<Card>>     communityCards = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Category>> allCategories  = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean>        isLoading      = new MutableLiveData<>(false);
    private final MutableLiveData<String>         importStatus   = new MutableLiveData<>();

    private List<Card> allCommunityCards = new ArrayList<>();

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        communityRepository = (CommunityRepositoryImpl)
                RevixaApp.getInstance().getCommunityRepository();
        cardRepository = RevixaApp.getInstance().getCardRepository();
        loadData();
    }

    public void loadData() {
        isLoading.postValue(true);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // 1. Load categories (background)
            List<Category> cats = cardRepository.getAllCategoriesSync();
            allCategories.postValue(cats);

            // 2. Load user cards (already enriched with categoryName)
            List<Card> userCards = cardRepository.getAllCardsSync();

            // 3. Load sample community decks
            List<Card> sampleCards = communityRepository.getSampleCommunityDecks();

            // 4. Merge: user cards first, then non-duplicate samples
            List<Card> merged = new ArrayList<>(userCards);
            for (Card sample : sampleCards) {
                boolean dup = false;
                for (Card user : userCards) {
                    if (user.getTitle().equalsIgnoreCase(sample.getTitle())) {
                        dup = true; break;
                    }
                }
                if (!dup) merged.add(sample);
            }

            allCommunityCards = merged;
            communityCards.postValue(merged);
            isLoading.postValue(false);
        });
    }

    public void filterBySubject(String subject) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (subject == null || subject.equals("All")) {
                communityCards.postValue(allCommunityCards);
                return;
            }
            List<Card> filtered = new ArrayList<>();
            for (Card c : allCommunityCards) {
                if (subject.equals(c.getCategoryName())) filtered.add(c);
            }
            communityCards.postValue(filtered);
        });
    }

    public void importCard(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Card> single = new ArrayList<>();
            single.add(card);
            communityRepository.importDeck(single);
            importStatus.postValue("'" + card.getTitle() + "' added to your deck!");
            // Reload to reflect changes
            loadData();
        });
    }

    public LiveData<List<Card>>     getCommunityCards() { return communityCards; }
    public LiveData<List<Category>> getAllCategories()   { return allCategories;  }
    public LiveData<Boolean>        getIsLoading()       { return isLoading;      }
    public LiveData<String>         getImportStatus()    { return importStatus;   }
}
