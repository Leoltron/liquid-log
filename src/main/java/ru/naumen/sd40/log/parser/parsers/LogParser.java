package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.util.DateUtils;
import ru.naumen.sd40.log.parser.util.Pair;

import java.io.Closeable;
import java.text.ParseException;
import java.util.function.Function;

public abstract class LogParser<TData> implements ILogParser<Pair<Long, DataSet>>, Closeable
{
    private IDataConsumer<Pair<Long, DataSet>> dataConsumer;

    private final IDataParser<TData> dataParser;
    private final ITimeParser timeParser;
    private final Function<DataSet, TData> selector;

    private Pair<Long, DataSet> currentPair;

    protected LogParser(IDataParser<TData> dataParser, ITimeParser timeParser, Function<DataSet, TData> selector)
    {
        this.dataParser = dataParser;
        this.timeParser = timeParser;
        this.selector = selector;
    }

    @Override
    public void parseLine(String line) throws ParseException
    {
        final long time = timeParser.parseTime(line);
        if (time == 0)
        {
            return;
        }
        final long roundedTime = DateUtils.roundTo5Minutes(time);

        if (currentPair == null)
        {
            currentPair = new Pair<>(roundedTime, new DataSet());
        }
        else if (roundedTime != currentPair.item1)
        {
            if (dataConsumer != null)
            {
                dataConsumer.consume(currentPair);
            }
            currentPair = new Pair<>(roundedTime, new DataSet());
        }

        dataParser.parseLine(line, selector.apply(currentPair.item2));
    }

    @Override
    public void setDataConsumer(IDataConsumer<Pair<Long, DataSet>> dataConsumer)
    {
        this.dataConsumer = dataConsumer;
    }

    @Override
    public void configureTimeZone(String zoneId)
    {
        timeParser.setTimeZone(zoneId);
    }

    @Override
    public void close()
    {
        if (dataConsumer != null)
        {
            dataConsumer.consume(currentPair);
        }
    }
}
