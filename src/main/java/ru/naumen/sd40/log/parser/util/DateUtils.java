package ru.naumen.sd40.log.parser.util;

public class DateUtils
{
    private static final int MILLIS_IN_5_MINUTES = 5 * 60 * 1000;

    public static long roundTo5Minutes(long dateMillis)
    {
        long count = dateMillis / MILLIS_IN_5_MINUTES;
        return count * MILLIS_IN_5_MINUTES;
    }
}
