package ru.naumen.sd40.log.parser.implementations.pagerender;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.*;
import ru.naumen.sd40.log.parser.implementations.pagerender.data.PageRenderData;

@Component
public class PageRenderParser extends LogParser<PageRenderData>
{
    public PageRenderParser(IDataParser<PageRenderData> dataParser, IDataBuilder<PageRenderData> dataBuilder, IDataWriter<PageRenderData> dataWriter)
    {
        super(dataParser, dataBuilder, dataWriter, ParseModeNames.PAGE_RENDER);
    }
}
