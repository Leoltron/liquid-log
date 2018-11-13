package ru.naumen.sd40.log.parser.parsers;

import java.text.ParseException;

public abstract class SimpleLogParser<Data> implements ILogParser<Data>
{
    @Override
    public void parseLine(String line, Object... additionalData) throws ParseException
    {
        parseLine(line);
    }

    public abstract void parseLine(String line) throws ParseException;
}
