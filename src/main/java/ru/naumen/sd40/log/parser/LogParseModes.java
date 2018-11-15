package ru.naumen.sd40.log.parser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.AbstractTimeParserBuilder;
import ru.naumen.sd40.log.parser.parsers.ILogParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class LogParseModes
{
    private final ApplicationContext ctx = new AnnotationConfigApplicationContext();
    private final HashMap<String, ILogParser<Long, DataSet>> logParsers = new HashMap<>();
    private final HashMap<String, AbstractTimeParserBuilder> timeParsers = new HashMap<>();

    public LogParseModes(List<ILogParser<Long, DataSet>> logParsers, List<AbstractTimeParserBuilder> timeParserBuilders)
    {
        for (ILogParser<Long, DataSet> logParser : logParsers)
        {
            Arrays.stream(logParser.getCompatibleModes())
                    .forEach(mode -> this.logParsers.put(mode.toLowerCase(), logParser));
        }

        for (AbstractTimeParserBuilder timeParserBuilder : timeParserBuilders)
        {
            Arrays.stream(timeParserBuilder.getCompatibleModes())
                    .forEach(mode -> this.timeParsers.put(mode.toLowerCase(), timeParserBuilder));
        }
    }

    ILogParser<Long, DataSet> getLogParser(String mode)
    {
        ILogParser<Long, DataSet> logParser = logParsers.getOrDefault(mode.toLowerCase(), null);
        if (logParser == null)
        {
            throw new IllegalArgumentException(
                    "Unknown parse mode! Available modes: " + String.join(",", logParsers.keySet()) + "." +
                            " Requested mode: " + mode);
        }

        return logParser;
    }

    AbstractTimeParserBuilder getTimeParserBuilder(String mode)
    {
        AbstractTimeParserBuilder timeParser = timeParsers.getOrDefault(mode.toLowerCase(), null);
        if (timeParser == null)
        {

            throw new IllegalArgumentException(
                    "Unknown parse mode! Available modes: " + String.join(",", timeParsers.keySet()) + "." +
                            " Requested mode: " + mode);
        }

        return ctx.getBean(timeParser.getClass());
    }

    int getBufferSize(String mode)
    {
        switch (mode.toLowerCase())
        {
            case "sdng":
                return 32 * 1024 * 1024;
            default:
                return 8 * 1024;
        }
    }
}
