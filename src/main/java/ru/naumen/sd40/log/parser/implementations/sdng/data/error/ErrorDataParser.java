package ru.naumen.sd40.log.parser.implementations.sdng.data.error;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataParser;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class ErrorDataParser implements IDataParser<ErrorData>
{
    @Override
    public void parseLine(String line, ErrorData errorData)
    {
        errorData.add(line);
    }
}
