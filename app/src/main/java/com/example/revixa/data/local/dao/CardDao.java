package com.example.revixa.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.revixa.data.local.entity.CardEntity;

import java.util.List;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCard(CardEntity card);

    @Update
    void updateCard(CardEntity card);

    @Delete
    void deleteCard(CardEntity card);

    @Query("SELECT * FROM cards WHERE id = :id")
    LiveData<CardEntity> getCardById(long id);

    @Query("SELECT * FROM cards WHERE id = :id")
    CardEntity getCardByIdSync(long id);

    @Query("SELECT * FROM cards ORDER BY created_at DESC")
    LiveData<List<CardEntity>> getAllCards();

    @Query("SELECT * FROM cards ORDER BY created_at DESC")
    List<CardEntity> getAllCardsSync();

    @Query("SELECT * FROM cards WHERE category_id = :categoryId ORDER BY created_at DESC")
    LiveData<List<CardEntity>> getCardsByCategory(long categoryId);

    @Query("SELECT * FROM cards WHERE next_review_date <= :currentTime ORDER BY next_review_date ASC")
    LiveData<List<CardEntity>> getDueCards(long currentTime);

    @Query("SELECT * FROM cards WHERE next_review_date <= :currentTime ORDER BY next_review_date ASC")
    List<CardEntity> getDueCardsSync(long currentTime);

    @Query("SELECT * FROM cards WHERE next_review_date <= :currentTime AND category_id = :categoryId ORDER BY next_review_date ASC")
    List<CardEntity> getDueCardsByCategorySync(long currentTime, long categoryId);

    @Query("SELECT COUNT(*) FROM cards")
    LiveData<Integer> getTotalCardCount();

    @Query("SELECT COUNT(*) FROM cards")
    int getTotalCardCountSync();

    @Query("SELECT COUNT(*) FROM cards WHERE next_review_date <= :currentTime")
    LiveData<Integer> getDueCardCount(long currentTime);

    @Query("SELECT COUNT(*) FROM cards")
    LiveData<Integer> getAllCardsDueCount();

    @Query("SELECT COUNT(*) FROM cards WHERE mastery_level >= 80")
    int getMasteredCardCount();

    @Query("SELECT COUNT(*) FROM cards WHERE mastery_level < 40")
    int getWeakCardCount();

    @Query("SELECT * FROM cards WHERE title LIKE :query OR question LIKE :query OR tags LIKE :query ORDER BY created_at DESC")
    LiveData<List<CardEntity>> searchCards(String query);

    @Query("SELECT * FROM cards WHERE priority = :priority ORDER BY next_review_date ASC")
    List<CardEntity> getCardsByPriority(int priority);

    @Query("UPDATE cards SET mastery_level = :level, updated_at = :time WHERE id = :id")
    void updateMasteryLevel(long id, int level, long time);

    @Query("DELETE FROM cards WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM cards WHERE is_public = 1 ORDER BY created_at DESC")
    List<CardEntity> getPublicCardsSync();

    @Query("SELECT AVG(mastery_level) FROM cards WHERE category_id = :categoryId")
    float getAverageMasteryForCategory(long categoryId);

    @Query("SELECT COUNT(*) FROM cards WHERE review_count > 0 AND DATE(last_review_date / 1000, 'unixepoch') = DATE(:timestamp / 1000, 'unixepoch')")
    int getReviewedCardCountForDay(long timestamp);
}
