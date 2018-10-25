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
public class UniversalLogParser
{

    public void parseAndUploadLogs(String mode, String logFilePath, String influxDbName, String timeZone)
            throws IOException, ParseException
    {
        ILogParser<Pair<Long, DataSet>> logParser = getLogParser(mode, logFilePath);
        int bufferSize = getBufferSize(mode);
        logParser.configureTimeZone(timeZone);

        try (final LogDataWriter writer = new LogDataWriter(influxDbName))
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

}
