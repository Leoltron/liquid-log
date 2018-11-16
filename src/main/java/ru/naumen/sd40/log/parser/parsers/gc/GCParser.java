package ru.naumen.sd40.log.parser.parsers.gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.GCData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;
import ru.naumen.sd40.log.parser.parsers.ParseModeNames;

@Component
public class GCParser extends LogParser<GCData>
{
    @Autowired
    public GCParser(IDataParser<GCData> dataParser)
    {
        super(dataParser, DataSet::getGcData, ParseModeNames.GC);
    }
}
