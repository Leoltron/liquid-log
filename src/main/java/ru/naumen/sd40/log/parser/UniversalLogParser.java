package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;
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
@Component
public class UniversalLogParser
{
    private final InfluxDAO influxDAO;
    private final LogParseModes parseModes;

    public UniversalLogParser(InfluxDAO influxDAO, LogParseModes parseModes)
    {
        this.influxDAO = influxDAO;
        this.parseModes = parseModes;
    }

    public void parseAndUploadLogs(String mode,
                                   String logFilePath,
                                   BufferedReaderBuilder brBuilder,
                                   String influxDbName,
                                   String timeZone,
                                   boolean printOutput)
            throws IOException, ParseException
    {
        ILogParser<Pair<Long, DataSet>> logParser = parseModes.getLogParser(mode);
        logParser.configureTimeZone(timeZone);

        Object[] additionalData = new Object[0];
        if (logFilePath.equalsIgnoreCase("top"))
        {
            additionalData = new Object[]{logFilePath};
        }

        try (final LogDataWriter writer = new LogDataWriter(influxDAO, influxDbName, printOutput))
        {
            logParser.setDataConsumer(writer);
            try (BufferedReader br = brBuilder.size(parseModes.getBufferSize(mode)).build())
            {
                new BufferedLogParser<>(logParser, br).parse(additionalData);
            }
        }
    }
}
