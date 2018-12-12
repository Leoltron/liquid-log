package ru.naumen.sd40.log.parser.implementations.sdng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.*;
import ru.naumen.sd40.log.parser.implementations.sdng.data.SdngData;

@Component
public class SdngParser extends LogParser<SdngData>
{
    @Autowired
    public SdngParser(IDataParser<SdngData> dataParser, IDataBuilder<SdngData> dataBuilder, IDataWriter<SdngData> dataWriter)
    {
        super(dataParser, dataBuilder, dataWriter, ParseModeNames.SDNG);
    }
}
