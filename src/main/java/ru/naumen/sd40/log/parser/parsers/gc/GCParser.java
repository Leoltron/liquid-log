package ru.naumen.sd40.log.parser.parsers.gc;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.GCData;
import ru.naumen.sd40.log.parser.parsers.LogParser;

public class GCParser extends LogParser<GCData>
{
    public GCParser()
    {
        super(new GCDataParser(), new GCTimeParser(), DataSet::getGcData);
    }
}
