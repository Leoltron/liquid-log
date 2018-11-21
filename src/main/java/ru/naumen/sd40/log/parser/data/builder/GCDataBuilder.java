package ru.naumen.sd40.log.parser.data.builder;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.GCData;

@Component
public class GCDataBuilder implements IDataBuilder<GCData>
{
    @Override
    public GCData createNew()
    {
        return new GCData();
    }
}
