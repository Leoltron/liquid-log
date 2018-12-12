package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.util.BufferedReaderBuilder;

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
        ILogParser logParser = parseModes.getLogParser(mode);
        brBuilder.size(logParser.getPreferredBufferSize());

        AbstractTimeParserBuilder timeParserBuilder = parseModes.getTimeParserBuilder(mode);
        ITimeParser timeParser = timeParserBuilder.build(logFilePath, timeZone);
        logParser.parseAndUploadLogs(timeParser, influxDAO, influxDbName, printOutput, brBuilder);
    }
}
