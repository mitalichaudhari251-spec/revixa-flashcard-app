package com.example.revixa.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {
    private DateUtils() {}

    private static final SimpleDateFormat SDF_DATE =
            new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
    private static final SimpleDateFormat SDF_TIME =
            new SimpleDateFormat("h:mm a", Locale.getDefault());

    public static String formatDate(long millis) {
        return SDF_DATE.format(new Date(millis));
    }

    public static String formatTime(long millis) {
        return SDF_TIME.format(new Date(millis));
    }

    public static boolean isSameDay(long millis1, long millis2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(millis1);
        c2.setTimeInMillis(millis2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isToday(long millis) {
        return isSameDay(millis, System.currentTimeMillis());
    }

    public static boolean isYesterday(long millis) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        return isSameDay(millis, yesterday.getTimeInMillis());
    }

    public static long startOfDay(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static long endOfDay(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

    public static String getRelativeTime(long millis) {
        long diff = millis - System.currentTimeMillis();
        long absDiff = Math.abs(diff);
        if (absDiff < 60_000)       return "Just now";
        if (absDiff < 3_600_000)    return (absDiff / 60_000) + " min";
        if (absDiff < 86_400_000)   return (absDiff / 3_600_000) + " hr";
        return (absDiff / 86_400_000) + " days";
    }

    public static String getDayLabel(int daysAgo) {
        String[] labels = {"S","M","T","W","T","F","S"};
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -daysAgo);
        return labels[c.get(Calendar.DAY_OF_WEEK) - 1];
    }
}
