package ru.naumen.sd40.log.parser.parsers.top;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("prototype")
public class TopTimeParserBuilder extends AbstractTimeParserBuilder
{
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}");

    private String dataDate;

    @Override
    public AbstractTimeParserBuilder setLogFileName(String logFileName)
    {
        Matcher matcher = DATE_TIME_PATTERN.matcher(logFileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        dataDate = matcher.group(0).replaceAll("-", "");

        return this;
    }

    @Override
    public ITimeParser build()
    {
        ITimeParser timeParser = new TopTimeParser(dataDate);
        timeParser.setTimeZone(zoneId);
        return timeParser;
    }

    @Override
    public String[] getCompatibleModes()
    {
        return new String[]{"top"};
    }
}
