package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.data.ErrorData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.util.Pair;

@Component
public class SdngDataParser implements IDataParser<Pair<ActionDoneData, ErrorData>>
{
    private final IDataParser<ActionDoneData> actionDataParser;
    private final IDataParser<ErrorData> errorDataParser;

    public SdngDataParser(IDataParser<ActionDoneData> actionDataParser, IDataParser<ErrorData> errorDataParser)
    {
        this.actionDataParser = actionDataParser;
        this.errorDataParser = errorDataParser;
    }

    @Override
    public void parseLine(String line, Pair<ActionDoneData, ErrorData> containers)
    {
        actionDataParser.parseLine(line, containers.item1);
        errorDataParser.parseLine(line, containers.item2);
    }
}
