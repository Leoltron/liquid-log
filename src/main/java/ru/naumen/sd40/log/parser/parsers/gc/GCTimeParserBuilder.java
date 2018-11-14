package ru.naumen.sd40.log.parser.parsers.gc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;

@Component
@Scope("prototype")
public class GCTimeParserBuilder extends AbstractTimeParserBuilder
{
    @Override
    public ITimeParser build()
    {
        ITimeParser timeParser = new GCTimeParser();
        timeParser.setTimeZone(zoneId);
        return timeParser;
    }

    @Override
    public String[] getCompatibleModes()
    {
        return new String[]{"gc"};
    }
}
