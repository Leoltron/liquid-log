package ru.naumen.sd40.log.parser;

import com.sun.javafx.UnmodifiableArrayList;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LogParseModes
{
    private final HashMap<String, ILogParser> logParsers = new HashMap<>();
    private final HashMap<String, AbstractTimeParserBuilder> timeParsers = new HashMap<>();
    private final List<String> workingModes;

    public LogParseModes(List<ILogParser> logParsers, List<AbstractTimeParserBuilder> timeParserBuilders)
    {
        for (ILogParser logParser : logParsers)
        {
            logParser.getCompatibleModes()
                    .forEach(mode -> this.logParsers.put(mode.toLowerCase(), logParser));
        }

        for (AbstractTimeParserBuilder timeParserBuilder : timeParserBuilders)
        {
            timeParserBuilder.getCompatibleModes()
                    .forEach(mode -> this.timeParsers.put(mode.toLowerCase(), timeParserBuilder));
        }

        Set<String> availableLogParseModes = new HashSet<>(this.logParsers.keySet());
        availableLogParseModes.retainAll(this.timeParsers.keySet());

        workingModes = new UnmodifiableArrayList<>(availableLogParseModes.toArray(new String[0]), availableLogParseModes.size());
    }

    ILogParser getLogParser(String mode)
    {
        ILogParser logParser = logParsers.getOrDefault(mode.toLowerCase(), null);
        if (logParser == null)
        {
            throw new IllegalArgumentException(
                    String.format("Unknown parse mode! Available modes: %s. Requested mode: %s",
                            String.join(",", logParsers.keySet()), mode));
        }

        return logParser;
    }

    AbstractTimeParserBuilder getTimeParserBuilder(String mode)
    {
        AbstractTimeParserBuilder timeParserBuilder = timeParsers.getOrDefault(mode.toLowerCase(), null);
        if (timeParserBuilder == null)
        {
            throw new IllegalArgumentException(
                    String.format("Unknown parse mode! Available modes: %s. Requested mode: %s",
                            String.join(",", timeParsers.keySet()), mode));
        }

        return timeParserBuilder;
    }

    public List<String> getWorkingParseModes()
    {
        return workingModes;
    }
}
