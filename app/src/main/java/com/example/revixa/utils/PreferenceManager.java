package com.example.revixa.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private final SharedPreferences prefs;
    private static PreferenceManager instance;

    private PreferenceManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (instance == null) instance = new PreferenceManager(context);
        return instance;
    }

    public boolean isDarkMode() {
        return prefs.getBoolean(Constants.KEY_DARK_MODE, false);
    }

    public void setDarkMode(boolean enabled) {
        prefs.edit().putBoolean(Constants.KEY_DARK_MODE, enabled).apply();
    }

    public int getStreak() {
        return prefs.getInt(Constants.KEY_STREAK, 0);
    }

    public void setStreak(int streak) {
        prefs.edit().putInt(Constants.KEY_STREAK, streak).apply();
    }

    public long getLastStudyDay() {
        return prefs.getLong(Constants.KEY_LAST_STUDY_DAY, 0);
    }

    public void setLastStudyDay(long millis) {
        prefs.edit().putLong(Constants.KEY_LAST_STUDY_DAY, millis).apply();
    }

    public int getTotalXp() {
        return prefs.getInt(Constants.KEY_TOTAL_XP, 0);
    }

    public void addXp(int xp) {
        int current = getTotalXp();
        prefs.edit().putInt(Constants.KEY_TOTAL_XP, current + xp).apply();
    }

    public boolean isOnboarded() {
        return prefs.getBoolean(Constants.KEY_ONBOARDED, false);
    }

    public void setOnboarded(boolean value) {
        prefs.edit().putBoolean(Constants.KEY_ONBOARDED, value).apply();
    }

    /**
     * Updates streak based on last study day.
     * Returns new streak value.
     */
    public int updateStreak() {
        long lastDay   = getLastStudyDay();
        long today     = System.currentTimeMillis();
        int  streak    = getStreak();

        if (lastDay == 0) {
            streak = 1;
        } else if (DateUtils.isToday(lastDay)) {
            // already studied today – no change
        } else if (DateUtils.isYesterday(lastDay)) {
            streak++;
            addXp(Constants.XP_STREAK_BONUS);
        } else {
            streak = 1; // streak broken
        }

        setStreak(streak);
        setLastStudyDay(today);
        return streak;
    }
}
