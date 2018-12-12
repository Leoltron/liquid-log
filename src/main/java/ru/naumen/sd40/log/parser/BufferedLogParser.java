package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

class BufferedLogParser<TData>
{
    private final ILogParser<TData> logParser;
    private final BufferedReader bufferedReader;

    BufferedLogParser(ILogParser<TData> logParser, BufferedReader bufferedReader)
    {
        this.logParser = logParser;
        this.bufferedReader = bufferedReader;
    }

    void parse(ITimeParser timeParser, IDataStorage<TData> dataStorage) throws IOException, ParseException
    {
        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            logParser.parseLine(line, timeParser, dataStorage);
        }
    }
}
