package ru.naumen.sd40.log.parser.implementations.pagerender;

import ru.naumen.sd40.log.parser.AbstractTimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageRenderTimeParser extends AbstractTimeParser
{
    private static final Pattern TIME_PATTERN = Pattern
            .compile("^\\d+ (\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");

    public PageRenderTimeParser()
    {
        super(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS", new Locale("ru", "RU")));
    }

    @Override
    protected long parseTimeInner(String line) throws ParseException
    {
        Matcher matcher = TIME_PATTERN.matcher(line);

        if (matcher.find())
        {
            String timeString = matcher.group(1);
            Date recDate = dateFormat.parse(timeString);
            return recDate.getTime();
        }
        return 0L;
    }
}
