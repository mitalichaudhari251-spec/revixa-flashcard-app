package com.example.revixa.presentation.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.revixa.RevixaApp;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.utils.PreferenceManager;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final CardRepository    cardRepository;
    private final PreferenceManager prefManager;

    // Always recalculate with current time each time this LiveData is observed
    private final LiveData<List<Card>>     allCards;
    private final LiveData<List<Category>> allCategories;
    private final LiveData<Integer>        totalCardCount;
    private final LiveData<Integer>        dueCardCount;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        cardRepository = RevixaApp.getInstance().getCardRepository();
        prefManager    = PreferenceManager.getInstance(application);

        allCards       = cardRepository.getAllCards();
        allCategories  = cardRepository.getAllCategories();
        totalCardCount = cardRepository.getTotalCardCount();
        // Use current time — Room LiveData auto-refreshes when cards table changes
        dueCardCount   = cardRepository.getDueCardCount(System.currentTimeMillis());
    }

    public LiveData<List<Card>>     getAllCards()      { return allCards;      }
    public LiveData<List<Category>> getAllCategories() { return allCategories; }
    public LiveData<Integer>        getTotalCardCount(){ return totalCardCount;}
    public LiveData<Integer>        getDueCardCount()  { return dueCardCount;  }

    public int  getStreak()     { return prefManager.getStreak();    }
    public int  getTotalXp()    { return prefManager.getTotalXp();   }
    public boolean isDarkMode() { return prefManager.isDarkMode();   }
    public void setDarkMode(boolean dark) { prefManager.setDarkMode(dark); }

    public LiveData<List<Card>> searchCards(String query) {
        return cardRepository.searchCards(query);
    }
}
