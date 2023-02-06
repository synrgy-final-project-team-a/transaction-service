package com.synergy.transaction.config;

public class BookingDuration {
    private static final long DAILY = 1;
    private static final long WEEKLY = 7;
    private static final long MONTHLY = 30;
    private static final long QUARTER = 3 * MONTHLY;
    private static final long SEMESTER = 6 * MONTHLY;
    private static final long YEARLY = 12 * MONTHLY;

    public static long getDuration(String durationType) {
        long duration = 0;

        switch (durationType) {
            case "DAILY":
                duration = DAILY;
                break;
            case "WEEKLY":
                duration = WEEKLY;
                break;
            case "MONTHLY":
                duration = MONTHLY;
                break;
            case "QUARTER":
                duration = QUARTER;
                break;
            case "SEMESTER":
                duration = SEMESTER;
                break;
            case "YEARLY":
                duration = YEARLY;
        }

        return duration;
    }
}
