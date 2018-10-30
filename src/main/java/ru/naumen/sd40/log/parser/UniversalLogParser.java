package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.BufferedLogParser;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.util.BufferedReaderBuilder;
import ru.naumen.sd40.log.parser.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by doki on 22.10.16.
 */
public class UniversalLogParser
{
    private final InfluxDAO influxDAO;

    public UniversalLogParser(InfluxDAO influxDAO)
    {
        this.influxDAO = influxDAO;
    }

    public void parseAndUploadLogs(ParseMode mode,
                                   String logFilePath,
                                   BufferedReaderBuilder brBuilder,
                                   String influxDbName,
                                   String timeZone,
                                   boolean printOutput)
            throws IOException, ParseException
    {
        ILogParser<Pair<Long, DataSet>> logParser = mode.getLogParser(logFilePath);
        logParser.configureTimeZone(timeZone);

        try (final LogDataWriter writer = new LogDataWriter(influxDAO, influxDbName, printOutput))
        {
            logParser.setDataConsumer(writer);
            try (BufferedReader br = brBuilder.size(mode.bufferSize).build())
            {
                new BufferedLogParser<>(logParser, br).parse();
            }
        }
    }
}
