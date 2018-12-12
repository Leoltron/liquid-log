package ru.naumen.sd40.log.parser.implementations.sdng.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataBuilder;

@Component
public class SdngDataBuilder implements IDataBuilder<SdngData>
{
    @Override
    public SdngData createNew()
    {
        return new SdngData();
    }
}
