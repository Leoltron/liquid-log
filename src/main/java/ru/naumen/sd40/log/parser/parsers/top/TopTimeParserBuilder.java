package ru.naumen.sd40.log.parser.parsers.top;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.ParseModeNames;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopTimeParserBuilder extends AbstractTimeParserBuilder
{
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}");

    @Override
    public ITimeParser build(String logFileName, String zoneId)
    {
        ITimeParser timeParser = new TopTimeParser(getDataDate(logFileName));
        timeParser.setTimeZone(zoneId);
        return timeParser;
    }

    @Override
    public List<String> getCompatibleModes()
    {
        return Collections.singletonList(ParseModeNames.TOP);
    }

    private static String getDataDate(String logFileName)
    {
        Matcher matcher = DATE_TIME_PATTERN.matcher(logFileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        return matcher.group(0).replaceAll("-", "");
    }
}
