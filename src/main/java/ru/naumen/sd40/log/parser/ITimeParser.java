package ru.naumen.sd40.log.parser;

import java.text.ParseException;

public interface ITimeParser
{
    /**
     * @return Time in millis if line matches parser's pattern, 0 otherwise.
     */
    long parseTime(String line) throws ParseException;

    void setTimeZone(String zoneId);

    long getLastParsedTime();
}
