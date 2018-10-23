package ru.naumen.sd40.log.parser.parsers.sdng;

import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.data.ErrorData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.util.Pair;

public class SdngDataParser implements IDataParser<Pair<ActionDoneData, ErrorData>>
{
    private final IDataParser<ActionDoneData> actionDataParser = new ActionDoneDataParser();
    private final IDataParser<ErrorData> errorDataParser = new ErrorDataParser();

    @Override
    public void parseLine(String line, Pair<ActionDoneData, ErrorData> containers)
    {
        actionDataParser.parseLine(line, containers.item1);
        errorDataParser.parseLine(line, containers.item2);
    }
}
