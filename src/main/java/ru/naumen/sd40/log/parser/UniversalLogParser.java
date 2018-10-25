package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.BufferedLogParser;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
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

    public void parseAndUploadLogs(ParseMode mode, String logFilePath, String influxDbName, String timeZone)
            throws IOException, ParseException
    {
        ILogParser<Pair<Long, DataSet>> logParser = mode.getLogParser(logFilePath);
        logParser.configureTimeZone(timeZone);

        try (final LogDataWriter writer = new LogDataWriter(influxDbName))
        {
            logParser.setDataConsumer(writer);
            try (BufferedReader br = new BufferedReader(new FileReader(logFilePath), mode.bufferSize))
            {
                new BufferedLogParser<>(logParser, br).parse();
            }
        }
    }
}
