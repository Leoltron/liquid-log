package ru.naumen.sd40.log.parser.parsers.sdng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.SdngData;
import ru.naumen.sd40.log.parser.data.builder.IDataBuilder;
import ru.naumen.sd40.log.parser.data.writer.IDataWriter;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;
import ru.naumen.sd40.log.parser.parsers.ParseModeNames;

@Component
public class SdngParser extends LogParser<SdngData>
{
    @Autowired
    public SdngParser(IDataParser<SdngData> dataParser, IDataBuilder<SdngData> dataBuilder, IDataWriter<SdngData> dataWriter)
    {
        super(dataParser, dataBuilder, dataWriter, ParseModeNames.SDNG);
    }
}
