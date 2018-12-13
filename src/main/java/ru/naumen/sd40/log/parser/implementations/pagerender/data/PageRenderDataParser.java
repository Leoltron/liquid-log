package ru.naumen.sd40.log.parser.implementations.pagerender.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PageRenderDataParser implements IDataParser<PageRenderData>
{
    private static final Pattern RENDER_TIME_REGEX = Pattern.compile("render time: (\\d+)");

    @Override
    public void parseLine(String line, PageRenderData pageRenderData)
    {
        Matcher matcher = RENDER_TIME_REGEX.matcher(line);

        if (matcher.find())
        {
            pageRenderData.add(Integer.parseInt(matcher.group(1)));
        }
    }
}
