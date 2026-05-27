package com.example.revixa.presentation.ui.progress;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.revixa.RevixaApp;
import com.example.revixa.data.local.database.AppDatabase;
import com.example.revixa.data.local.dao.CardDao;
import com.example.revixa.domain.model.Card;
import com.example.revixa.domain.model.Category;
import com.example.revixa.domain.repository.CardRepository;
import com.example.revixa.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgressViewModel extends AndroidViewModel {

    private final CardRepository    cardRepository;
    private final CardDao           cardDao;
    private final PreferenceManager prefManager;

    private final LiveData<List<Card>>     allCards;
    private final MutableLiveData<List<Category>> allCategoriesWithMastery = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> masteredCount    = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> weakCount        = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> totalCount       = new MutableLiveData<>(0);
    private final MutableLiveData<List<Integer>> dayRevisionCounts = new MutableLiveData<>();

    public ProgressViewModel(@NonNull Application application) {
        super(application);
        cardRepository = RevixaApp.getInstance().getCardRepository();
        cardDao        = RevixaApp.getInstance().getDatabase().cardDao();
        prefManager    = PreferenceManager.getInstance(application);
        allCards       = cardRepository.getAllCards();
        loadStats();
    }

    public void loadStats() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int mastered = cardRepository.getMasteredCardCount();
            int weak     = cardRepository.getWeakCardCount();
            List<Card> cards = cardRepository.getAllCardsSync();
            masteredCount.postValue(mastered);
            weakCount.postValue(weak);
            totalCount.postValue(cards.size());

            // Per-category mastery
            List<Category> cats = cardRepository.getAllCategoriesSync();
            for (Category cat : cats) {
                float avg = cardDao.getAverageMasteryForCategory(cat.getId());
                cat.setMasteryPercentage(avg > 0 ? avg : 0);
            }
            // Only show categories that have cards
            List<Category> withCards = new ArrayList<>();
            for (Category c : cats) {
                if (c.getCardCount() > 0) withCards.add(c);
            }
            allCategoriesWithMastery.postValue(withCards);

            // Build last 7 days revision counts
            List<Integer> counts = new ArrayList<>();
            for (int i = 6; i >= 0; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, -i);
                long start = startOfDay(cal);
                long end   = endOfDay(cal);
                int dayCount = 0;
                for (Card c : cards) {
                    if (c.getLastReviewDate() >= start && c.getLastReviewDate() <= end) {
                        dayCount++;
                    }
                }
                counts.add(dayCount);
            }
            dayRevisionCounts.postValue(counts);
        });
    }

    private long startOfDay(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);      c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    private long endOfDay(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.HOUR_OF_DAY, 23); c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);       c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

    public void refresh() { loadStats(); }

    public LiveData<List<Card>>     getAllCards()              { return allCards;                 }
    public LiveData<List<Category>> getAllCategories()         { return allCategoriesWithMastery; }
    public LiveData<Integer>        getMasteredCount()         { return masteredCount;            }
    public LiveData<Integer>        getWeakCount()             { return weakCount;                }
    public LiveData<Integer>        getTotalCount()            { return totalCount;               }
    public LiveData<List<Integer>>  getDayRevisionCounts()     { return dayRevisionCounts;        }
    public int  getStreak()  { return prefManager.getStreak();  }
    public int  getTotalXp() { return prefManager.getTotalXp(); }
}
