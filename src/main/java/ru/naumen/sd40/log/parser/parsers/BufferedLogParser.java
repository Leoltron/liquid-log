package ru.naumen.sd40.log.parser.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

public class BufferedLogParser<TKey, TData>
{
    private final ILogParser<TKey, TData> logParser;
    private final BufferedReader bufferedReader;

    public BufferedLogParser(ILogParser<TKey, TData> logParser, BufferedReader bufferedReader)
    {
        this.logParser = logParser;
        this.bufferedReader = bufferedReader;
    }

    public void parse(ITimeParser timeParser, IDataStorage<TKey, TData> dataStorage) throws IOException, ParseException
    {
        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            logParser.parseLine(line, timeParser, dataStorage);
        }
    }
}
