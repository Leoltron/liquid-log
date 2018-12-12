package ru.naumen.sd40.log.parser.implementations.gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.*;
import ru.naumen.sd40.log.parser.implementations.gc.data.GCData;

@Component
public class GCParser extends LogParser<GCData>
{
    @Autowired
    public GCParser(IDataParser<GCData> dataParser, IDataBuilder<GCData> dataBuilder, IDataWriter<GCData> dataWriter)
    {
        super(dataParser, dataBuilder, dataWriter, ParseModeNames.GC);
    }
}
