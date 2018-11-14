package ru.naumen.sd40.log.parser.parsers;

import java.text.ParseException;

public interface ILogParser<TKey, TData> extends IModeCompatible
{
    void parseLine(String line, ITimeParser timeParser) throws ParseException;

    void setDataStorage(IDataStorage<TKey, TData> dataConsumer);

}
