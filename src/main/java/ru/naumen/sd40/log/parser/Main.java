package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.BufferedLogParser;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.parsers.gc.GCParser;
import ru.naumen.sd40.log.parser.parsers.sdng.SdngParser;
import ru.naumen.sd40.log.parser.parsers.top.TopParser;
import ru.naumen.sd40.log.parser.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by doki on 22.10.16.
 */
public class Main
{
    /**
     * @param args [0] log file path, [1] influx database name, [2] timezone (optional)
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException
    {
        checkArgs(args);

        final String logFilePath = args[0];
        final String mode = System.getProperty("parse.mode", "");

        ILogParser<Pair<Long, DataSet>> logParser = getLogParser(mode, logFilePath);
        int bufferSize = getBufferSize(mode);

        if (args.length > 2)
        {
            logParser.configureTimeZone(args[2]);
        }

        try (final LogDataWriter writer = new LogDataWriter(args[1]))
        {
            logParser.setDataConsumer(writer);
            try (BufferedReader br = new BufferedReader(new FileReader(logFilePath), bufferSize))
            {
                new BufferedLogParser<>(logParser, br).parse();
            }
        }
    }

    private static ILogParser<Pair<Long, DataSet>> getLogParser(String mode, String logFilePath)
    {
        switch (mode)
        {
            case "sdng":
                return new SdngParser();
            case "gc":
                return new GCParser();
            case "top":
                return new TopParser(logFilePath);
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Available modes: sdng, gc, top. Requested mode: " + mode);
        }
    }

    private static int getBufferSize(String mode)
    {
        switch (mode)
        {
            case "sdng":
                return 32 * 1024 * 1024;
            default:
                return 8 * 1024;
        }
    }

    private static void checkArgs(String[] args)
    {
        if (args.length < 2)
        {
            throw new IllegalArgumentException("Expected at least 2 arguments, got " + args.length);
        }
    }

}
