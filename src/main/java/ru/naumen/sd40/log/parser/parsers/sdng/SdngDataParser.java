package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.data.ErrorData;
import ru.naumen.sd40.log.parser.data.SdngData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;

@Component
public class SdngDataParser implements IDataParser<SdngData>
{
    private final IDataParser<ActionDoneData> actionDataParser;
    private final IDataParser<ErrorData> errorDataParser;

    public SdngDataParser(IDataParser<ActionDoneData> actionDataParser, IDataParser<ErrorData> errorDataParser)
    {
        this.actionDataParser = actionDataParser;
        this.errorDataParser = errorDataParser;
    }

    @Override
    public void parseLine(String line, SdngData sdngData)
    {
        actionDataParser.parseLine(line, sdngData.getActionDoneData());
        errorDataParser.parseLine(line, sdngData.getErrorData());
    }
}
