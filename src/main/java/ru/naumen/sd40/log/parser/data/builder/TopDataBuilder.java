package ru.naumen.sd40.log.parser.data.builder;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.TopData;

@Component
public class TopDataBuilder implements IDataBuilder<TopData>
{
    @Override
    public TopData createNew()
    {
        return new TopData();
    }
}
