package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ActionDoneDataParser implements IDataParser<ActionDoneData>
{
    private static final Pattern DONE_REG_EX = Pattern.compile("Done\\((\\d+)\\): ?(.*?Action)");
    private static final Set<String> EXCLUDED_ACTIONS = new HashSet<>();

    static
    {
        EXCLUDED_ACTIONS.add("EventAction".toLowerCase());
    }

    @Override
    public void parseLine(String line, ActionDoneData data)
    {
        Matcher matcher = DONE_REG_EX.matcher(line);

        if (matcher.find())
        {
            String action = matcher.group(2);
            if (EXCLUDED_ACTIONS.contains(action))
            {
                return;
            }
            data.add(action, Integer.parseInt(matcher.group(1)));
        }
    }
}
