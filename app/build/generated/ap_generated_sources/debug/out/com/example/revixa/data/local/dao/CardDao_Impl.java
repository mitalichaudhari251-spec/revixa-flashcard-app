package com.example.revixa.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.revixa.data.local.entity.CardEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CardDao_Impl implements CardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CardEntity> __insertionAdapterOfCardEntity;

  private final EntityDeletionOrUpdateAdapter<CardEntity> __deletionAdapterOfCardEntity;

  private final EntityDeletionOrUpdateAdapter<CardEntity> __updateAdapterOfCardEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMasteryLevel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public CardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCardEntity = new EntityInsertionAdapter<CardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cards` (`id`,`title`,`question`,`answer`,`tags`,`priority`,`difficulty`,`category_id`,`card_type`,`is_public`,`created_at`,`updated_at`,`ease_factor`,`interval_days`,`repetitions`,`next_review_date`,`last_review_date`,`mastery_level`,`review_count`,`correct_count`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CardEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.question == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.question);
        }
        if (entity.answer == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.answer);
        }
        if (entity.tags == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.tags);
        }
        statement.bindLong(6, entity.priority);
        statement.bindLong(7, entity.difficulty);
        if (entity.categoryId == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.categoryId);
        }
        if (entity.cardType == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.cardType);
        }
        final int _tmp = entity.isPublic ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.createdAt);
        statement.bindLong(12, entity.updatedAt);
        statement.bindDouble(13, entity.easeFactor);
        statement.bindLong(14, entity.intervalDays);
        statement.bindLong(15, entity.repetitions);
        statement.bindLong(16, entity.nextReviewDate);
        statement.bindLong(17, entity.lastReviewDate);
        statement.bindLong(18, entity.masteryLevel);
        statement.bindLong(19, entity.reviewCount);
        statement.bindLong(20, entity.correctCount);
      }
    };
    this.__deletionAdapterOfCardEntity = new EntityDeletionOrUpdateAdapter<CardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `cards` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CardEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfCardEntity = new EntityDeletionOrUpdateAdapter<CardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `cards` SET `id` = ?,`title` = ?,`question` = ?,`answer` = ?,`tags` = ?,`priority` = ?,`difficulty` = ?,`category_id` = ?,`card_type` = ?,`is_public` = ?,`created_at` = ?,`updated_at` = ?,`ease_factor` = ?,`interval_days` = ?,`repetitions` = ?,`next_review_date` = ?,`last_review_date` = ?,`mastery_level` = ?,`review_count` = ?,`correct_count` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CardEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.question == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.question);
        }
        if (entity.answer == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.answer);
        }
        if (entity.tags == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.tags);
        }
        statement.bindLong(6, entity.priority);
        statement.bindLong(7, entity.difficulty);
        if (entity.categoryId == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.categoryId);
        }
        if (entity.cardType == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.cardType);
        }
        final int _tmp = entity.isPublic ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.createdAt);
        statement.bindLong(12, entity.updatedAt);
        statement.bindDouble(13, entity.easeFactor);
        statement.bindLong(14, entity.intervalDays);
        statement.bindLong(15, entity.repetitions);
        statement.bindLong(16, entity.nextReviewDate);
        statement.bindLong(17, entity.lastReviewDate);
        statement.bindLong(18, entity.masteryLevel);
        statement.bindLong(19, entity.reviewCount);
        statement.bindLong(20, entity.correctCount);
        statement.bindLong(21, entity.id);
      }
    };
    this.__preparedStmtOfUpdateMasteryLevel = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE cards SET mastery_level = ?, updated_at = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cards WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insertCard(final CardEntity card) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCardEntity.insertAndReturnId(card);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCard(final CardEntity card) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCardEntity.handle(card);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCard(final CardEntity card) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCardEntity.handle(card);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateMasteryLevel(final long id, final int level, final long time) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMasteryLevel.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, level);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, time);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateMasteryLevel.release(_stmt);
    }
  }

  @Override
  public void deleteById(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public LiveData<CardEntity> getCardById(final long id) {
    final String _sql = "SELECT * FROM cards WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<CardEntity>() {
      @Override
      @Nullable
      public CardEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
          final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
          final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
          final CardEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new CardEntity();
            _result.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _result.title = null;
            } else {
              _result.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _result.question = null;
            } else {
              _result.question = _cursor.getString(_cursorIndexOfQuestion);
            }
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _result.answer = null;
            } else {
              _result.answer = _cursor.getString(_cursorIndexOfAnswer);
            }
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _result.tags = null;
            } else {
              _result.tags = _cursor.getString(_cursorIndexOfTags);
            }
            _result.priority = _cursor.getInt(_cursorIndexOfPriority);
            _result.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _result.categoryId = null;
            } else {
              _result.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            if (_cursor.isNull(_cursorIndexOfCardType)) {
              _result.cardType = null;
            } else {
              _result.cardType = _cursor.getString(_cursorIndexOfCardType);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _result.isPublic = _tmp != 0;
            _result.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
            _result.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
            _result.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
            _result.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            _result.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            _result.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            _result.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            _result.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public CardEntity getCardByIdSync(final long id) {
    final String _sql = "SELECT * FROM cards WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
      final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
      final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
      final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
      final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
      final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
      final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
      final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
      final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
      final CardEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new CardEntity();
        _result.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _result.title = null;
        } else {
          _result.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _result.question = null;
        } else {
          _result.question = _cursor.getString(_cursorIndexOfQuestion);
        }
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _result.answer = null;
        } else {
          _result.answer = _cursor.getString(_cursorIndexOfAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _result.tags = null;
        } else {
          _result.tags = _cursor.getString(_cursorIndexOfTags);
        }
        _result.priority = _cursor.getInt(_cursorIndexOfPriority);
        _result.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _result.categoryId = null;
        } else {
          _result.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfCardType)) {
          _result.cardType = null;
        } else {
          _result.cardType = _cursor.getString(_cursorIndexOfCardType);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
        _result.isPublic = _tmp != 0;
        _result.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _result.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
        _result.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
        _result.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
        _result.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
        _result.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
        _result.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
        _result.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
        _result.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<CardEntity>> getAllCards() {
    final String _sql = "SELECT * FROM cards ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<List<CardEntity>>() {
      @Override
      @Nullable
      public List<CardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
          final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
          final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
          final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CardEntity _item;
            _item = new CardEntity();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _item.title = null;
            } else {
              _item.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _item.question = null;
            } else {
              _item.question = _cursor.getString(_cursorIndexOfQuestion);
            }
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _item.answer = null;
            } else {
              _item.answer = _cursor.getString(_cursorIndexOfAnswer);
            }
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _item.tags = null;
            } else {
              _item.tags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.priority = _cursor.getInt(_cursorIndexOfPriority);
            _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _item.categoryId = null;
            } else {
              _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            if (_cursor.isNull(_cursorIndexOfCardType)) {
              _item.cardType = null;
            } else {
              _item.cardType = _cursor.getString(_cursorIndexOfCardType);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _item.isPublic = _tmp != 0;
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
            _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
            _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
            _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<CardEntity> getAllCardsSync() {
    final String _sql = "SELECT * FROM cards ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
      final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
      final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
      final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
      final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
      final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
      final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
      final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
      final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
      final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CardEntity _item;
        _item = new CardEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _item.question = null;
        } else {
          _item.question = _cursor.getString(_cursorIndexOfQuestion);
        }
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _item.answer = null;
        } else {
          _item.answer = _cursor.getString(_cursorIndexOfAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _item.tags = null;
        } else {
          _item.tags = _cursor.getString(_cursorIndexOfTags);
        }
        _item.priority = _cursor.getInt(_cursorIndexOfPriority);
        _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _item.categoryId = null;
        } else {
          _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfCardType)) {
          _item.cardType = null;
        } else {
          _item.cardType = _cursor.getString(_cursorIndexOfCardType);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
        _item.isPublic = _tmp != 0;
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
        _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
        _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
        _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
        _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
        _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
        _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
        _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<CardEntity>> getCardsByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM cards WHERE category_id = ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<List<CardEntity>>() {
      @Override
      @Nullable
      public List<CardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
          final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
          final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
          final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CardEntity _item;
            _item = new CardEntity();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _item.title = null;
            } else {
              _item.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _item.question = null;
            } else {
              _item.question = _cursor.getString(_cursorIndexOfQuestion);
            }
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _item.answer = null;
            } else {
              _item.answer = _cursor.getString(_cursorIndexOfAnswer);
            }
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _item.tags = null;
            } else {
              _item.tags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.priority = _cursor.getInt(_cursorIndexOfPriority);
            _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _item.categoryId = null;
            } else {
              _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            if (_cursor.isNull(_cursorIndexOfCardType)) {
              _item.cardType = null;
            } else {
              _item.cardType = _cursor.getString(_cursorIndexOfCardType);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _item.isPublic = _tmp != 0;
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
            _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
            _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
            _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<CardEntity>> getDueCards(final long currentTime) {
    final String _sql = "SELECT * FROM cards WHERE next_review_date <= ? ORDER BY next_review_date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<List<CardEntity>>() {
      @Override
      @Nullable
      public List<CardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
          final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
          final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
          final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CardEntity _item;
            _item = new CardEntity();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _item.title = null;
            } else {
              _item.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _item.question = null;
            } else {
              _item.question = _cursor.getString(_cursorIndexOfQuestion);
            }
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _item.answer = null;
            } else {
              _item.answer = _cursor.getString(_cursorIndexOfAnswer);
            }
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _item.tags = null;
            } else {
              _item.tags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.priority = _cursor.getInt(_cursorIndexOfPriority);
            _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _item.categoryId = null;
            } else {
              _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            if (_cursor.isNull(_cursorIndexOfCardType)) {
              _item.cardType = null;
            } else {
              _item.cardType = _cursor.getString(_cursorIndexOfCardType);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _item.isPublic = _tmp != 0;
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
            _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
            _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
            _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<CardEntity> getDueCardsSync(final long currentTime) {
    final String _sql = "SELECT * FROM cards WHERE next_review_date <= ? ORDER BY next_review_date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
      final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
      final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
      final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
      final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
      final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
      final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
      final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
      final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
      final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CardEntity _item;
        _item = new CardEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _item.question = null;
        } else {
          _item.question = _cursor.getString(_cursorIndexOfQuestion);
        }
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _item.answer = null;
        } else {
          _item.answer = _cursor.getString(_cursorIndexOfAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _item.tags = null;
        } else {
          _item.tags = _cursor.getString(_cursorIndexOfTags);
        }
        _item.priority = _cursor.getInt(_cursorIndexOfPriority);
        _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _item.categoryId = null;
        } else {
          _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfCardType)) {
          _item.cardType = null;
        } else {
          _item.cardType = _cursor.getString(_cursorIndexOfCardType);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
        _item.isPublic = _tmp != 0;
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
        _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
        _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
        _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
        _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
        _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
        _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
        _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CardEntity> getDueCardsByCategorySync(final long currentTime, final long categoryId) {
    final String _sql = "SELECT * FROM cards WHERE next_review_date <= ? AND category_id = ? ORDER BY next_review_date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, categoryId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
      final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
      final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
      final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
      final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
      final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
      final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
      final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
      final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
      final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CardEntity _item;
        _item = new CardEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _item.question = null;
        } else {
          _item.question = _cursor.getString(_cursorIndexOfQuestion);
        }
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _item.answer = null;
        } else {
          _item.answer = _cursor.getString(_cursorIndexOfAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _item.tags = null;
        } else {
          _item.tags = _cursor.getString(_cursorIndexOfTags);
        }
        _item.priority = _cursor.getInt(_cursorIndexOfPriority);
        _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _item.categoryId = null;
        } else {
          _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfCardType)) {
          _item.cardType = null;
        } else {
          _item.cardType = _cursor.getString(_cursorIndexOfCardType);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
        _item.isPublic = _tmp != 0;
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
        _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
        _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
        _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
        _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
        _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
        _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
        _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalCardCount() {
    final String _sql = "SELECT COUNT(*) FROM cards";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getTotalCardCountSync() {
    final String _sql = "SELECT COUNT(*) FROM cards";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getDueCardCount(final long currentTime) {
    final String _sql = "SELECT COUNT(*) FROM cards WHERE next_review_date <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getAllCardsDueCount() {
    final String _sql = "SELECT COUNT(*) FROM cards";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getMasteredCardCount() {
    final String _sql = "SELECT COUNT(*) FROM cards WHERE mastery_level >= 80";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getWeakCardCount() {
    final String _sql = "SELECT COUNT(*) FROM cards WHERE mastery_level < 40";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<CardEntity>> searchCards(final String query) {
    final String _sql = "SELECT * FROM cards WHERE title LIKE ? OR question LIKE ? OR tags LIKE ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 3;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"cards"}, false, new Callable<List<CardEntity>>() {
      @Override
      @Nullable
      public List<CardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
          final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
          final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
          final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
          final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CardEntity _item;
            _item = new CardEntity();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _item.title = null;
            } else {
              _item.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _item.question = null;
            } else {
              _item.question = _cursor.getString(_cursorIndexOfQuestion);
            }
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _item.answer = null;
            } else {
              _item.answer = _cursor.getString(_cursorIndexOfAnswer);
            }
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _item.tags = null;
            } else {
              _item.tags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.priority = _cursor.getInt(_cursorIndexOfPriority);
            _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _item.categoryId = null;
            } else {
              _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            if (_cursor.isNull(_cursorIndexOfCardType)) {
              _item.cardType = null;
            } else {
              _item.cardType = _cursor.getString(_cursorIndexOfCardType);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _item.isPublic = _tmp != 0;
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
            _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
            _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
            _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
            _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<CardEntity> getCardsByPriority(final int priority) {
    final String _sql = "SELECT * FROM cards WHERE priority = ? ORDER BY next_review_date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, priority);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
      final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
      final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
      final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
      final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
      final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
      final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
      final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
      final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
      final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CardEntity _item;
        _item = new CardEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _item.question = null;
        } else {
          _item.question = _cursor.getString(_cursorIndexOfQuestion);
        }
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _item.answer = null;
        } else {
          _item.answer = _cursor.getString(_cursorIndexOfAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _item.tags = null;
        } else {
          _item.tags = _cursor.getString(_cursorIndexOfTags);
        }
        _item.priority = _cursor.getInt(_cursorIndexOfPriority);
        _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _item.categoryId = null;
        } else {
          _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfCardType)) {
          _item.cardType = null;
        } else {
          _item.cardType = _cursor.getString(_cursorIndexOfCardType);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
        _item.isPublic = _tmp != 0;
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
        _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
        _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
        _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
        _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
        _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
        _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
        _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CardEntity> getPublicCardsSync() {
    final String _sql = "SELECT * FROM cards WHERE is_public = 1 ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
      final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "card_type");
      final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "is_public");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
      final int _cursorIndexOfEaseFactor = CursorUtil.getColumnIndexOrThrow(_cursor, "ease_factor");
      final int _cursorIndexOfIntervalDays = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_days");
      final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
      final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "next_review_date");
      final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_review_date");
      final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery_level");
      final int _cursorIndexOfReviewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "review_count");
      final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correct_count");
      final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CardEntity _item;
        _item = new CardEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _item.question = null;
        } else {
          _item.question = _cursor.getString(_cursorIndexOfQuestion);
        }
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _item.answer = null;
        } else {
          _item.answer = _cursor.getString(_cursorIndexOfAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _item.tags = null;
        } else {
          _item.tags = _cursor.getString(_cursorIndexOfTags);
        }
        _item.priority = _cursor.getInt(_cursorIndexOfPriority);
        _item.difficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _item.categoryId = null;
        } else {
          _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfCardType)) {
          _item.cardType = null;
        } else {
          _item.cardType = _cursor.getString(_cursorIndexOfCardType);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
        _item.isPublic = _tmp != 0;
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.updatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _item.easeFactor = _cursor.getFloat(_cursorIndexOfEaseFactor);
        _item.intervalDays = _cursor.getInt(_cursorIndexOfIntervalDays);
        _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
        _item.nextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
        _item.lastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
        _item.masteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
        _item.reviewCount = _cursor.getInt(_cursorIndexOfReviewCount);
        _item.correctCount = _cursor.getInt(_cursorIndexOfCorrectCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public float getAverageMasteryForCategory(final long categoryId) {
    final String _sql = "SELECT AVG(mastery_level) FROM cards WHERE category_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final float _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getFloat(0);
      } else {
        _result = 0f;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getReviewedCardCountForDay(final long timestamp) {
    final String _sql = "SELECT COUNT(*) FROM cards WHERE review_count > 0 AND DATE(last_review_date / 1000, 'unixepoch') = DATE(? / 1000, 'unixepoch')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, timestamp);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
