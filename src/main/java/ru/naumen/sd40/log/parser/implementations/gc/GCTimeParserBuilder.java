package ru.naumen.sd40.log.parser.implementations.gc;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.ITimeParser;
import ru.naumen.sd40.log.parser.ParseModeNames;

import java.util.Collections;
import java.util.List;

@Component
public class GCTimeParserBuilder extends AbstractTimeParserBuilder
{
    @Override
    public ITimeParser build(String logFileName, String zoneId)
    {
        ITimeParser timeParser = new GCTimeParser();
        timeParser.setTimeZone(zoneId);
        return timeParser;
    }

    @Override
    public List<String> getCompatibleModes()
    {
        return Collections.singletonList(ParseModeNames.GC);
    }
}
