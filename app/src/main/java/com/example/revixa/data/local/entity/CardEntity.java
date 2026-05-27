package com.example.revixa.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "cards",
    foreignKeys = @ForeignKey(
        entity = CategoryEntity.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = ForeignKey.SET_NULL
    ),
    indices = {@Index("category_id")}
)
public class CardEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "question")
    public String question;

    @ColumnInfo(name = "answer")
    public String answer;

    @ColumnInfo(name = "tags")
    public String tags;

    @ColumnInfo(name = "priority")
    public int priority; // 0=Low, 1=Medium, 2=High

    @ColumnInfo(name = "difficulty")
    public int difficulty; // 0=Easy, 1=Medium, 2=Hard

    @ColumnInfo(name = "category_id")
    public Long categoryId;

    @ColumnInfo(name = "card_type")
    public String cardType; // Definition, Formula, Fact, etc.

    @ColumnInfo(name = "is_public")
    public boolean isPublic;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    // Spaced repetition fields
    @ColumnInfo(name = "ease_factor")
    public float easeFactor; // default 2.5

    @ColumnInfo(name = "interval_days")
    public int intervalDays; // days until next review

    @ColumnInfo(name = "repetitions")
    public int repetitions;

    @ColumnInfo(name = "next_review_date")
    public long nextReviewDate;

    @ColumnInfo(name = "last_review_date")
    public long lastReviewDate;

    @ColumnInfo(name = "mastery_level")
    public int masteryLevel; // 0-100

    @ColumnInfo(name = "review_count")
    public int reviewCount;

    @ColumnInfo(name = "correct_count")
    public int correctCount;

    public CardEntity() {
        this.easeFactor = 2.5f;
        this.intervalDays = 1;
        this.repetitions = 0;
        this.masteryLevel = 0;
        this.reviewCount = 0;
        this.correctCount = 0;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.nextReviewDate = System.currentTimeMillis();
        this.isPublic = false;
    }
}
