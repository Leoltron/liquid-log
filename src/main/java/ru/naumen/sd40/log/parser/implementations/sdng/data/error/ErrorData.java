package ru.naumen.sd40.log.parser.implementations.sdng.data.error;

import ru.naumen.sd40.log.parser.IDataSet;
import ru.naumen.sd40.log.parser.util.counter.PatternMatchCounter;
import ru.naumen.sd40.log.parser.util.counter.RegexFindCounter;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public final class ErrorData implements IDataSet
{
    private static final Pattern WARN_REG_EX = Pattern.compile("^\\d+ \\[.+?] \\(.+?\\) WARN");
    private static final Pattern ERROR_REG_EX = Pattern.compile("^\\d+ \\[.+?] \\(.+?\\) ERROR");
    private static final Pattern FATAL_REG_EX = Pattern.compile("^\\d+ \\[.+?] \\(.+?\\) FATAL");

    private PatternMatchCounter warnCount = new RegexFindCounter(WARN_REG_EX);
    private PatternMatchCounter errorCount = new RegexFindCounter(ERROR_REG_EX);
    private PatternMatchCounter fatalCount = new RegexFindCounter(FATAL_REG_EX);

    void add(String line)
    {
        warnCount.incIfMatches(line);
        errorCount.incIfMatches(line);
        fatalCount.incIfMatches(line);
    }

    public long getWarnCount()
    {
        return warnCount.getCount();
    }

    public long getErrorCount()
    {
        return errorCount.getCount();
    }

    public long getFatalCount()
    {
        return fatalCount.getCount();
    }

}
