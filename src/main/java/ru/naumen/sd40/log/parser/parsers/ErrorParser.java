package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.ErrorData;

/**
 * Created by doki on 22.10.16.
 */
public class ErrorParser implements IDataParser<ErrorData>
{
    @Override
    public void parseLine(String line, ErrorData errorData)
    {
        errorData.add(line);
    }
}
