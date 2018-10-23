package ru.naumen.sd40.log.parser.parsers.sdng;

import ru.naumen.sd40.log.parser.data.ErrorData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;

/**
 * Created by doki on 22.10.16.
 */
public class ErrorDataParser implements IDataParser<ErrorData>
{
    @Override
    public void parseLine(String line, ErrorData errorData)
    {
        errorData.add(line);
    }
}
