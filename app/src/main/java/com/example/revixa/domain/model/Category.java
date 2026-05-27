package com.example.revixa.domain.model;

public class Category {
    private long id;
    private String name;
    private String color;
    private String icon;
    private String description;
    private int cardCount;
    private long createdAt;
    private float masteryPercentage;

    public Category() {
        this.createdAt = System.currentTimeMillis();
    }

    public Category(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.createdAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCardCount() { return cardCount; }
    public void setCardCount(int cardCount) { this.cardCount = cardCount; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public float getMasteryPercentage() { return masteryPercentage; }
    public void setMasteryPercentage(float masteryPercentage) { this.masteryPercentage = masteryPercentage; }
}
