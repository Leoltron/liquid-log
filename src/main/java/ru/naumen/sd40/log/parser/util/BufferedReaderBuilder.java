package ru.naumen.sd40.log.parser.util;

import java.io.BufferedReader;
import java.io.Reader;

public class BufferedReaderBuilder
{
    private final Reader reader;
    private int size;

    public BufferedReaderBuilder(Reader reader)
    {
        this.reader = reader;
    }

    public BufferedReaderBuilder size(int size)
    {
        this.size = size;
        return this;
    }

    public BufferedReader build()
    {
        return new BufferedReader(reader, size);
    }
}
