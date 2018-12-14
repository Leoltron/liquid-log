package ru.naumen.sd40.log.parser.implementations.pagerender.data;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataWriter;

import static ru.naumen.sd40.log.parser.implementations.pagerender.data.PageRenderDataType.*;

@Component
public class PageRenderDataWriter implements IDataWriter<PageRenderData>
{
    @Override
    public void writeFields(Point.Builder builder, long time, PageRenderData pageRenderData, boolean printCsvData)
    {
        builder.addField(COUNT, pageRenderData.getCount())
                .addField("min", pageRenderData.getMin())
                .addField(MEAN, pageRenderData.getMean())
                .addField(STDDEV, pageRenderData.getStddev())
                .addField(PERCENTILE50, pageRenderData.getPercent50())
                .addField(PERCENTILE95, pageRenderData.getPercent95())
                .addField(PERCENTILE99, pageRenderData.getPercent99())
                .addField(PERCENTILE999, pageRenderData.getPercent999())
                .addField(MAX, pageRenderData.getMax());
    }
}
