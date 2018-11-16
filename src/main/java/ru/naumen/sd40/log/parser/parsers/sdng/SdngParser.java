package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.data.ErrorData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;
import ru.naumen.sd40.log.parser.parsers.ParseModeNames;
import ru.naumen.sd40.log.parser.util.Pair;

@Component
public class SdngParser extends LogParser<Pair<ActionDoneData, ErrorData>>
{
    @Autowired
    public SdngParser(IDataParser<Pair<ActionDoneData, ErrorData>> dataParser)
    {
        super(dataParser, d -> new Pair<>(d.getActionsDone(), d.getErrorData()), ParseModeNames.SDNG);
    }
}
