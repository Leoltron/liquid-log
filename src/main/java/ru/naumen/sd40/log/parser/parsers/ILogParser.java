package ru.naumen.sd40.log.parser.parsers;

import java.text.ParseException;

public interface ILogParser<TResultData>
{
    void parseLine(String line) throws ParseException;

    TResultData getResultData();

    void configureTimeZone(String zoneId);
}
