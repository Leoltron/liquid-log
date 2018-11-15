package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.BufferedLogParser;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;
import ru.naumen.sd40.log.parser.util.BufferedReaderBuilder;

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
        ILogParser<Long, DataSet> logParser = parseModes.getLogParser(mode);
        AbstractTimeParserBuilder timeParserBuilder = parseModes.getTimeParserBuilder(mode);

        timeParserBuilder.setLogFileName(logFilePath);
        timeParserBuilder.setTimeZone(timeZone);

        ITimeParser timeParser = timeParserBuilder.build();

        try (final InfluxDAOStorage writer = new InfluxDAOStorage(influxDAO, influxDbName, printOutput))
        {
            try (BufferedReader br = brBuilder.size(parseModes.getBufferSize(mode)).build())
            {
                new BufferedLogParser<>(logParser, br).parse(timeParser, writer);
            }
            writer.onDataChunkFinished(timeParser.getLastParsedTime());
        }
    }
}
