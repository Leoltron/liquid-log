package ru.naumen.sd40.log.parser;

import java.util.List;

public abstract class AbstractTimeParserBuilder implements IModeCompatible
{
    public abstract ITimeParser build(String logFileName, String zoneId);

    public abstract List<String> getCompatibleModes();
}
