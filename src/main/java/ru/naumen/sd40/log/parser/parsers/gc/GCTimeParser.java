package ru.naumen.sd40.log.parser.parsers.gc;

import ru.naumen.sd40.log.parser.parsers.AbstractTimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GCTimeParser extends AbstractTimeParser
{

    private static final Pattern PATTERN = Pattern
            .compile("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}).*");

    public GCTimeParser()
    {
        super(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Locale("ru", "RU")));
    }

    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find())
        {
            return dateFormat.parse(matcher.group(1)).getTime();
        }
        return 0L;
    }
}