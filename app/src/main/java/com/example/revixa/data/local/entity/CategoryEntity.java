package com.example.revixa.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "icon")
    public String icon;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "card_count")
    public int cardCount;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    public CategoryEntity() {
        this.createdAt = System.currentTimeMillis();
        this.cardCount = 0;
    }

    public CategoryEntity(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.createdAt = System.currentTimeMillis();
        this.cardCount = 0;
    }
}
