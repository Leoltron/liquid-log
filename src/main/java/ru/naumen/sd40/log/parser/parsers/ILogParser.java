package ru.naumen.sd40.log.parser.parsers;

import java.text.ParseException;

public interface ILogParser<Data>
{
    void parseLine(String line, Object... additionalData) throws ParseException;

    void setDataConsumer(IDataConsumer<Data> dataConsumer);

    void configureTimeZone(String zoneId);
}
