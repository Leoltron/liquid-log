package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.util.BufferedReaderBuilder;

import java.io.IOException;
import java.text.ParseException;

public interface ILogParser<TData> extends IModeCompatible
{
    void parseLine(String line, ITimeParser timeParser, IDataStorage<TData> dataStorage) throws ParseException;

    void parseAndUploadLogs(ITimeParser timeParser,
                            InfluxDAO influxDAO,
                            String influxDbName,
                            boolean printOutput,
                            BufferedReaderBuilder brBuilder) throws IOException, ParseException;

    int getPreferredBufferSize();
}
