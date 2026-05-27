package com.example.revixa.utils;

import android.text.TextUtils;

public final class ValidationUtils {
    private ValidationUtils() {}

    public static boolean isValidTitle(String title) {
        return !TextUtils.isEmpty(title) && title.trim().length() >= 2 && title.trim().length() <= 100;
    }

    public static boolean isValidQuestion(String question) {
        return !TextUtils.isEmpty(question) && question.trim().length() >= 5 && question.trim().length() <= 500;
    }

    public static boolean isValidAnswer(String answer) {
        return !TextUtils.isEmpty(answer) && answer.trim().length() >= 1 && answer.trim().length() <= 1000;
    }

    public static boolean isValidCategoryName(String name) {
        return !TextUtils.isEmpty(name) && name.trim().length() >= 2 && name.trim().length() <= 50;
    }

    public static String sanitize(String input) {
        if (TextUtils.isEmpty(input)) return "";
        return input.trim().replaceAll("[<>\"';&]", "");
    }
}
