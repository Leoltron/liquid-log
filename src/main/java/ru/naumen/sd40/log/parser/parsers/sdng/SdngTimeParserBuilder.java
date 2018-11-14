package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;

@Component
@Scope("prototype")
public class SdngTimeParserBuilder extends AbstractTimeParserBuilder
{
    @Override
    public ITimeParser build()
    {
        ITimeParser timeParser = new SdngTimeParser();
        timeParser.setTimeZone(zoneId);
        return timeParser;
    }

    @Override
    public String[] getCompatibleModes()
    {
        return new String[]{"cdng"};
    }
}
