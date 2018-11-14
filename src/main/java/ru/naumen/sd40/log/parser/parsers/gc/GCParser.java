package ru.naumen.sd40.log.parser.parsers.gc;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.GCData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;

@Component
public class GCParser extends LogParser<GCData>
{
    public GCParser(IDataParser<GCData> dataParser)
    {
        super(dataParser, DataSet::getGcData, "gc");
    }
}
