package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.util.DateUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class LogParser<TData> implements ILogParser<Map<Long, DataSet>>
{
    private final Map<Long, DataSet> data = new HashMap<>();
    private final IDataParser<TData> dataParser;
    private final ITimeParser timeParser;
    private final Function<DataSet, TData> selector;
    private long lastTime = -1;

    protected LogParser(IDataParser<TData> dataParser, ITimeParser timeParser, Function<DataSet, TData> selector)
    {
        this.dataParser = dataParser;
        this.timeParser = timeParser;
        this.selector = selector;
    }

    @Override
    public void parseLine(String line) throws ParseException
    {
        lastTime = timeParser.parseTime(line);
        if (lastTime == 0)
        {
            return;
        }
        lastTime = DateUtils.roundTo5Minutes(lastTime);

        DataSet dataSet = data.computeIfAbsent(lastTime, k -> new DataSet());
        dataParser.parseLine(line, selector.apply(dataSet));
    }

    public long getLastTime()
    {
        return lastTime;
    }

    @Override
    public Map<Long, DataSet> getResultData()
    {
        return data;
    }

    @Override
    public void configureTimeZone(String zoneId)
    {
        timeParser.setTimeZone(zoneId);
    }
}
