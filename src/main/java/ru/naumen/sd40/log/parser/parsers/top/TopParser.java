package ru.naumen.sd40.log.parser.parsers.top;

import org.springframework.stereotype.Component;
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

@Component
public class TopParser implements ILogParser<Pair<Long, DataSet>>
{
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}");

    private final IDataParser<TopData> dataParser = new TopDataParser();

    private IDataConsumer<Pair<Long, DataSet>> dataConsumer;
    private Pair<Long, DataSet> currentPair;
    private String zoneId;

    @Override
    public void parseLine(String line, Object... additionalData) throws ParseException
    {
        if (additionalData.length == 0 || !(additionalData[0] instanceof String))
        {
            throw new ParseException("Expected additionalData to have a log file name as first element", 0);
        }

        ITimeParser timeParser = getTimeParser((String) additionalData[0]);
        timeParser.setTimeZone(zoneId);

        long time = timeParser.parseTime(line);
        if (time != 0)
        {
            if (currentPair != null && dataConsumer != null)
            {
                dataConsumer.consume(currentPair);
            }
            currentPair = new Pair<>(time, new DataSet());
        } else
        {
            dataParser.parseLine(line, currentPair.item2.cpuData());
        }
    }

    private ITimeParser getTimeParser(String logFileName)
    {
        Matcher matcher = DATE_TIME_PATTERN.matcher(logFileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        String dataDate = matcher.group(0).replaceAll("-", "");
        return new TopTimeParser(dataDate);
    }

    @Override
    public void setDataConsumer(IDataConsumer<Pair<Long, DataSet>> dataConsumer)
    {
        this.dataConsumer = dataConsumer;
    }

    @Override
    public void configureTimeZone(String zoneId)
    {
        this.zoneId = zoneId;
    }
}
