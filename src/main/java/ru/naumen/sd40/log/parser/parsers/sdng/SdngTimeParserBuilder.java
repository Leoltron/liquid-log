package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.ParseModeNames;

import java.util.Collections;
import java.util.List;

@Component
public class SdngTimeParserBuilder extends AbstractTimeParserBuilder
{
    @Override
    public ITimeParser build(String logFileName, String zoneId)
    {
        ITimeParser timeParser = new SdngTimeParser();
        timeParser.setTimeZone(zoneId);
        return timeParser;
    }

    @Override
    public List<String> getCompatibleModes()
    {
        return Collections.singletonList(ParseModeNames.SDNG);
    }
}