package ru.naumen.sd40.log.parser.parsers;

import java.text.DateFormat;
import java.util.TimeZone;

public abstract class AbstractTimeParser implements ITimeParser
{
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    protected final DateFormat dateFormat;

    protected AbstractTimeParser(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
        setTimeZone(GMT);
    }

    public void setTimeZone(String zoneId)
    {
        setTimeZone(TimeZone.getTimeZone(zoneId));
    }

    public void setTimeZone(TimeZone zone)
    {
        dateFormat.setTimeZone(zone);
    }
}
