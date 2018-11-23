package ru.naumen.sd40.log.parser.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

public class BufferedLogParser<TData>
{
    private final ILogParser<TData> logParser;
    private final BufferedReader bufferedReader;

    public BufferedLogParser(ILogParser<TData> logParser, BufferedReader bufferedReader)
    {
        this.logParser = logParser;
        this.bufferedReader = bufferedReader;
    }

    public void parse(ITimeParser timeParser, IDataStorage<TData> dataStorage) throws IOException, ParseException
    {
        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            logParser.parseLine(line, timeParser, dataStorage);
        }
    }
}
