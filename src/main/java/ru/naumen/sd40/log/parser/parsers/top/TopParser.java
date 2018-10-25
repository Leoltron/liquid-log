package ru.naumen.sd40.log.parser.parsers.top;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.TopData;
import ru.naumen.sd40.log.parser.parsers.IDataConsumer;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;
import ru.naumen.sd40.log.parser.util.Pair;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopParser implements ILogParser<Pair<Long, DataSet>>
{
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}");

    private final IDataParser<TopData> dataParser = new TopDataParser();
    private final ITimeParser timeParser;

    private IDataConsumer<Pair<Long, DataSet>> dataConsumer;
    private Pair<Long, DataSet> currentPair;

    public TopParser(String logFileName)
    {
        Matcher matcher = DATE_TIME_PATTERN.matcher(logFileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        String dataDate = matcher.group(0).replaceAll("-", "");
        timeParser = new TopTimeParser(dataDate);
    }

    @Override
    public void parseLine(String line) throws ParseException
    {
        long time = timeParser.parseTime(line);
        if (time != 0)
        {
            if (currentPair != null && dataConsumer != null)
            {
                dataConsumer.consume(currentPair);
            }
            currentPair = new Pair<>(time, new DataSet());
        }
        else
        {
            dataParser.parseLine(line, currentPair.item2.cpuData());
        }
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
}
