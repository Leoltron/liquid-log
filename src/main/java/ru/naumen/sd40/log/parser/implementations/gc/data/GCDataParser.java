package ru.naumen.sd40.log.parser.implementations.gc.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GCDataParser implements IDataParser<GCData>
{
    private static final Pattern GC_EXECUTION_TIME = Pattern.compile(".*real=(.*)secs.*");

    @Override
    public void parseLine(String line, GCData data)
    {
        Matcher matcher = GC_EXECUTION_TIME.matcher(line);
        if (matcher.find())
        {
            data.addValue(Double.parseDouble(matcher.group(1).trim().replace(',', '.')));
        }
    }
}
