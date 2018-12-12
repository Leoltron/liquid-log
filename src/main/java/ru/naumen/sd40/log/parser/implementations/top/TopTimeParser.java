package ru.naumen.sd40.log.parser.implementations.top;

import ru.naumen.sd40.log.parser.AbstractTimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopTimeParser extends AbstractTimeParser
{
    private static final Pattern TIME_REGEX = Pattern.compile("^_+ (\\S+)");

    private final String dataDate;

    TopTimeParser(String dataDate)
    {
        super(new SimpleDateFormat("yyyyMMddHH:mm"));
        this.dataDate = dataDate;
    }

    @Override
    public long parseTimeInner(String line) throws ParseException
    {
        Matcher timeMatcher = TIME_REGEX.matcher(line);
        if (timeMatcher.find())
        {
            return dateFormat.parse(dataDate + timeMatcher.group(1)).getTime();
        }
        return 0;
    }
}
