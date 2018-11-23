package ru.naumen.sd40.log.parser.data.builder;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.SdngData;

@Component
public class SdngDataBuilder implements IDataBuilder<SdngData>
{
    @Override
    public SdngData createNew()
    {
        return new SdngData();
    }
}
