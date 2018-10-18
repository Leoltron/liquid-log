package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.GCData;

public class GCParser extends LogParser<GCData>
{
    public GCParser()
    {
        super(new GCDataParser(), new GCTimeParser(), DataSet::getGcData);
    }
}
