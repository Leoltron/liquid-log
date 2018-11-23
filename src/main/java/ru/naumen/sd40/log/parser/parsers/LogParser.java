package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.InfluxDAOStorage;
import ru.naumen.sd40.log.parser.data.IDataSet;
import ru.naumen.sd40.log.parser.data.builder.IDataBuilder;
import ru.naumen.sd40.log.parser.data.writer.IDataWriter;
import ru.naumen.sd40.log.parser.util.BufferedReaderBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class LogParser<TData extends IDataSet> implements ILogParser<TData>
{
    private final IDataParser<TData> dataParser;
    private final IDataBuilder<TData> dataBuilder;
    private final IDataWriter<TData> dataWriter;
    private final List<String> compatibleModes;


    protected LogParser(IDataParser<TData> dataParser,
                        IDataBuilder<TData> dataBuilder,
                        IDataWriter<TData> dataWriter,
                        String... compatibleModes)
    {
        this.dataParser = dataParser;
        this.dataBuilder = dataBuilder;
        this.dataWriter = dataWriter;
        this.compatibleModes = Collections.unmodifiableList(Arrays.asList(compatibleModes));
    }

    @Override
    public void parseLine(String line, ITimeParser timeParser, IDataStorage<TData> dataStorage) throws ParseException
    {
        long lastParsedTime = timeParser.getLastParsedTime();
        final long time = timeParser.parseTime(line);
        if (time <= 0)
        {
            return;
        }
        TData data = dataStorage.getData(time);

        if (lastParsedTime > 0 && lastParsedTime != time)
        {
            dataStorage.onDataChunkFinished(lastParsedTime);
        }

        dataParser.parseLine(line, data);
    }

    @Override
    public List<String> getCompatibleModes()
    {
        return compatibleModes;
    }

    public void parseAndUploadLogs(ITimeParser timeParser,
                                   InfluxDAO influxDAO,
                                   String influxDbName,
                                   boolean printOutput,
                                   BufferedReaderBuilder brBuilder) throws IOException, ParseException
    {
        try (final InfluxDAOStorage<TData> writer = new InfluxDAOStorage<>(dataBuilder, dataWriter, influxDAO, influxDbName, printOutput))
        {
            try (BufferedReader br = brBuilder.build())
            {
                new BufferedLogParser<>(this, br).parse(timeParser, writer);
            }
            writer.onDataChunkFinished(timeParser.getLastParsedTime());
        }
    }
}
