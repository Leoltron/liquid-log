package ru.naumen.sd40.log.parser.implementations.sdng.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;
import ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone.ActionDoneData;
import ru.naumen.sd40.log.parser.implementations.sdng.data.error.ErrorData;

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
