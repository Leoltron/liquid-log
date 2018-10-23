package ru.naumen.sd40.log.parser.parsers.sdng;

import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.data.ErrorData;
import ru.naumen.sd40.log.parser.parsers.LogParser;
import ru.naumen.sd40.log.parser.util.Pair;

public class SdngParser extends LogParser<Pair<ActionDoneData, ErrorData>>
{
    public SdngParser()
    {
        super(new SdngDataParser(), new SdngTimeParser(), d -> new Pair<>(d.getActionsDone(), d.getErrorData()));
    }
}