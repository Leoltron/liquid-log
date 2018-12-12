package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.util.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.TimeZone;

public abstract class AbstractTimeParser implements ITimeParser
{
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    protected final DateFormat dateFormat;
    private long lastParsedTime = -1;

    protected AbstractTimeParser(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
        setTimeZone(GMT);
    }

    public void setTimeZone(String zoneId)
    {
        setTimeZone(TimeZone.getTimeZone(zoneId));
    }

    private void setTimeZone(TimeZone zone)
    {
        dateFormat.setTimeZone(zone);
    }

    public final long parseTime(String line) throws ParseException
    {
        long parsedTime = parseTimeInner(line);
        if (parsedTime <= 0)
            return parsedTime;
        lastParsedTime = DateUtils.roundTo5Minutes(parsedTime);
        return lastParsedTime;
    }

    protected abstract long parseTimeInner(String line) throws ParseException;

    @Override
    public long getLastParsedTime()
    {
        return lastParsedTime;
    }
}
