package ru.naumen.sd40.log.parser.parsers.gc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.GCData;
import ru.naumen.sd40.log.parser.data.builder.IDataBuilder;
import ru.naumen.sd40.log.parser.data.writer.IDataWriter;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;
import ru.naumen.sd40.log.parser.parsers.ParseModeNames;

@Component
public class GCParser extends LogParser<GCData>
{
    @Autowired
    public GCParser(IDataParser<GCData> dataParser, IDataBuilder<GCData> dataBuilder, IDataWriter<GCData> dataWriter)
    {
        super(dataParser, dataBuilder, dataWriter, ParseModeNames.GC);
    }
}
