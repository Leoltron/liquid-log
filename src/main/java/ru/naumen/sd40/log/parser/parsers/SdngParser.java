package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.ErrorData;

import java.text.ParseException;
import java.util.Map;

public class SdngParser implements ILogParser<Map<Long, DataSet>>
{

    private ActionDoneParser actionDoneParser = new ActionDoneParser();
    private ErrorParser errorParser = new ErrorParser();

    @Override
    public void parseLine(String line) throws ParseException
    {
        actionDoneParser.parseLine(line);
        if (actionDoneParser.getLastTime() != 0)
        {
            final ErrorData errorData = getResultData().get(actionDoneParser.getLastTime()).getErrorData();
            errorParser.parseLine(line, errorData);
        }
    }

    @Override
    public Map<Long, DataSet> getResultData()
    {
        return actionDoneParser.getResultData();
    }

    @Override
    public void configureTimeZone(String zoneId)
    {
        actionDoneParser.configureTimeZone(zoneId);
    }
}
