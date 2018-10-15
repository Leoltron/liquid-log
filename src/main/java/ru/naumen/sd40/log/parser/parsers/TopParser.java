package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.TopData;
import ru.naumen.sd40.log.parser.util.DateUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopParser implements ILogParser<Map<Long, DataSet>>
{

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}");

    private final Map<Long, DataSet> data = new HashMap<>();
    private final IDataParser<TopData> dataParser = new TopDataParser();
    private final ITimeParser timeParser;
    private DataSet currentDataSet;

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
            currentDataSet = data.computeIfAbsent(DateUtils.roundTo5Minutes(time), k -> new DataSet());
            return;
        }
        if (currentDataSet != null)
        {
            dataParser.parseLine(line, currentDataSet.cpuData());
        }
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
