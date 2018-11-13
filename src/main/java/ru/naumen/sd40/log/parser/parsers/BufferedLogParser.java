package ru.naumen.sd40.log.parser.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

public class BufferedLogParser<TResultData>
{
    private final ILogParser<TResultData> logParser;
    private final BufferedReader bufferedReader;

    public BufferedLogParser(ILogParser<TResultData> logParser, BufferedReader bufferedReader)
    {
        this.logParser = logParser;
        this.bufferedReader = bufferedReader;
    }

    public void parse(Object... additionalData) throws IOException, ParseException
    {
        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            logParser.parseLine(line, additionalData);
        }
    }
}
