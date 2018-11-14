package ru.naumen.sd40.log.parser.parsers;

public abstract class AbstractTimeParserBuilder implements IModeCompatible
{
    protected String zoneId = "GMT";

    public AbstractTimeParserBuilder setLogFileName(String logFileName)
    {
        return this;
    }

    public AbstractTimeParserBuilder setTimeZone(String zoneId)
    {
        this.zoneId = zoneId;
        return this;
    }

    public abstract ITimeParser build();

    public abstract String[] getCompatibleModes();
}
