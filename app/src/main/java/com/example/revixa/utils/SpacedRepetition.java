package com.example.revixa.utils;

public class SpacedRepetition {

    // Rating constants
    public static final int RATING_HARD   = 0;
    public static final int RATING_MEDIUM = 1;
    public static final int RATING_EASY   = 2;

    public static class Result {
        public float newEaseFactor;
        public int   newInterval;
        public int   newRepetitions;
        public long  nextReviewDate;

        public Result(float ef, int interval, int reps) {
            this.newEaseFactor  = ef;
            this.newInterval    = interval;
            this.newRepetitions = reps;
            this.nextReviewDate = System.currentTimeMillis()
                    + (long) interval * 24 * 60 * 60 * 1000L;
        }
    }

    /**
     * SM-2 spaced repetition algorithm.
     * rating: 0 = Hard, 1 = Medium, 2 = Easy
     */
    public static Result calculate(float easeFactor, int interval,
                                   int repetitions, int rating) {
        float ef        = easeFactor;
        int   newInterval;
        int   newReps;

        if (rating == RATING_HARD) {
            newReps     = 0;
            newInterval = 1;
            ef          = Math.max(1.3f, ef - 0.20f);
        } else {
            // quality: Hard=3, Medium=4, Easy=5  (SM-2 scale)
            int quality = (rating == RATING_EASY) ? 5 : 4;
            ef = ef + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f));
            ef = Math.max(1.3f, ef);

            if (repetitions == 0) {
                newInterval = 1;
            } else if (repetitions == 1) {
                newInterval = 6;
            } else {
                newInterval = Math.round(interval * ef);
            }
            newReps = repetitions + 1;
        }

        return new Result(ef, newInterval, newReps);
    }

    public static String getNextReviewLabel(int intervalDays) {
        if (intervalDays <= 0)  return "Today";
        if (intervalDays == 1)  return "Tomorrow";
        if (intervalDays < 7)   return "In " + intervalDays + " days";
        if (intervalDays < 14)  return "In 1 week";
        if (intervalDays < 30)  return "In " + (intervalDays / 7) + " weeks";
        if (intervalDays < 60)  return "In 1 month";
        return "In " + (intervalDays / 30) + " months";
    }
}
