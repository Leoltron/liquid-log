package ru.naumen.sd40.log.parser.parsers.sdng;

import ru.naumen.sd40.log.parser.parsers.AbstractTimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
public class SdngTimeParser extends AbstractTimeParser
{
    private static final Pattern TIME_PATTERN = Pattern
            .compile("^\\d+ \\[.*?\\] \\((\\d{2} .{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3})\\)");

    public SdngTimeParser()
    {
        super(new SimpleDateFormat("dd MMM yyyy HH:mm:ss,SSS", new Locale("ru", "RU")));
    }

    public long parseTime(String line) throws ParseException
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
