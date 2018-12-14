package ru.naumen.sd40.log.parser.implementations.pagerender.data;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataBuilder;

@Component
public class PageRenderDataBuilder implements IDataBuilder<PageRenderData>
{
    @Override
    public PageRenderData createNew()
    {
        return new PageRenderData();
    }
}
