package ru.naumen.sd40.log.parser.implementations.top.data;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataWriter;

import static ru.naumen.sd40.log.parser.implementations.top.data.TopDataType.*;

@Component
public class TopDataWriter implements IDataWriter<TopData>
{
    @Override
    public void writeFields(Point.Builder builder, long time, TopData data, boolean printCsvData)
    {
        builder.addField(AVG_LA, data.getAvgLa())
                .addField(AVG_CPU, data.getAvgCpuUsage())
                .addField(AVG_MEM, data.getAvgMemUsage())
                .addField(MAX_LA, data.getMaxLa())
                .addField(MAX_CPU, data.getMaxCpu())
                .addField(MAX_MEM, data.getMaxMem());
    }
}
