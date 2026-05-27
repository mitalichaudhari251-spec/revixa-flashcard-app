package com.example.revixa.utils;

public final class Constants {
    private Constants() {}

    public static final String DATABASE_NAME      = "revixa_db";
    public static final String PREFS_NAME         = "revixa_prefs";

    // Pref keys
    public static final String KEY_DARK_MODE      = "dark_mode";
    public static final String KEY_STREAK         = "streak_count";
    public static final String KEY_LAST_STUDY_DAY = "last_study_day";
    public static final String KEY_TOTAL_XP       = "total_xp";
    public static final String KEY_ONBOARDED      = "onboarded";

    // XP values
    public static final int XP_PER_REVISION       = 10;
    public static final int XP_SESSION_COMPLETE   = 50;
    public static final int XP_STREAK_BONUS       = 100;

    // Intent keys
    public static final String EXTRA_CARD_ID      = "extra_card_id";
    public static final String EXTRA_CATEGORY_ID  = "extra_category_id";
    public static final String EXTRA_DECK_NAME    = "extra_deck_name";
    public static final String EXTRA_EDIT_MODE    = "extra_edit_mode";

    // Card types
    public static final String TYPE_DEFINITION    = "Definition";
    public static final String TYPE_FORMULA       = "Formula";
    public static final String TYPE_FACT          = "Fact";
    public static final String TYPE_THEOREM       = "Theorem";
    public static final String TYPE_CASE_STUDY    = "Case Study";
    public static final String TYPE_DIAGRAM       = "Diagram";

    // Priority
    public static final int PRIORITY_LOW          = 0;
    public static final int PRIORITY_MEDIUM       = 1;
    public static final int PRIORITY_HIGH         = 2;

    // Difficulty
    public static final int DIFFICULTY_EASY       = 0;
    public static final int DIFFICULTY_MEDIUM     = 1;
    public static final int DIFFICULTY_HARD       = 2;
}
