package ru.naumen.sd40.log.parser.implementations.top.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataBuilder;

@Component
public class TopDataBuilder implements IDataBuilder<TopData>
{
    @Override
    public TopData createNew()
    {
        return new TopData();
    }
}
