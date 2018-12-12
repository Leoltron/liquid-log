package ru.naumen.sd40.log.parser.implementations.gc.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataBuilder;

@Component
public class GCDataBuilder implements IDataBuilder<GCData>
{
    @Override
    public GCData createNew()
    {
        return new GCData();
    }
}
