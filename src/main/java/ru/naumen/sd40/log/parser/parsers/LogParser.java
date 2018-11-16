package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class LogParser<TData> implements ILogParser<Long, DataSet>
{
    private final IDataParser<TData> dataParser;
    private final Function<DataSet, TData> selector;
    private final List<String> compatibleModes;


    protected LogParser(IDataParser<TData> dataParser, Function<DataSet, TData> selector, String... compatibleModes)
    {
        this.dataParser = dataParser;
        this.selector = selector;
        this.compatibleModes = Collections.unmodifiableList(Arrays.asList(compatibleModes));
    }

    @Override
    public void parseLine(String line, ITimeParser timeParser, IDataStorage<Long, DataSet> dataStorage) throws ParseException
    {
        long lastParsedTime = timeParser.getLastParsedTime();
        final long time = timeParser.parseTime(line);
        if (time <= 0)
        {
            return;
        }
        DataSet dataSet = dataStorage.getData(time);

        if (lastParsedTime > 0 && lastParsedTime != time)
        {
            dataStorage.onDataChunkFinished(lastParsedTime);
        }

        dataParser.parseLine(line, selector.apply(dataSet));
    }

    @Override
    public List<String> getCompatibleModes()
    {
        return compatibleModes;
    }
}
