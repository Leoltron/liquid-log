package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;

import java.text.ParseException;
import java.util.function.Function;

public abstract class LogParser<TData> implements ILogParser<Long, DataSet>
{
    private IDataStorage<Long, DataSet> dataConsumer;
    private final IDataParser<TData> dataParser;
    private final Function<DataSet, TData> selector;
    private final String[] compatibleModes;


    protected LogParser(IDataParser<TData> dataParser, Function<DataSet, TData> selector, String... compatibleModes)
    {
        this.dataParser = dataParser;
        this.selector = selector;
        this.compatibleModes = compatibleModes;
    }

    @Override
    public void parseLine(String line, ITimeParser timeParser) throws ParseException
    {
        if (dataConsumer == null)
        {
            throw new NullPointerException();
        }
        long lastParsedTime = timeParser.getLastParsedTime();
        final long time = timeParser.parseTime(line);
        if (time <= 0)
        {
            return;
        }
        DataSet dataSet = dataConsumer.getData(time);

        if (lastParsedTime > 0 && lastParsedTime != time)
        {
            dataConsumer.onDataChunkFinished(lastParsedTime);
        }

        dataParser.parseLine(line, selector.apply(dataSet));
    }

    @Override
    public void setDataStorage(IDataStorage<Long, DataSet> dataConsumer)
    {
        this.dataConsumer = dataConsumer;
    }

    @Override
    public String[] getCompatibleModes()
    {
        String[] compatibleModesCopy = new String[compatibleModes.length];
        System.arraycopy(compatibleModes, 0, compatibleModesCopy, 0, compatibleModes.length);
        return compatibleModesCopy;
    }
}
