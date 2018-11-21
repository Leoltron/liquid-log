package ru.naumen.sd40.log.parser.data.writer;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.TopData;

import static ru.naumen.perfhouse.statdata.Constants.Top.*;

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
