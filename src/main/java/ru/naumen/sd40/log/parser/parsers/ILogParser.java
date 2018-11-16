package ru.naumen.sd40.log.parser.parsers;

import java.text.ParseException;

public interface ILogParser<TKey, TData> extends IModeCompatible
{
    void parseLine(String line, ITimeParser timeParser, IDataStorage<TKey, TData> dataStorage) throws ParseException;

}
