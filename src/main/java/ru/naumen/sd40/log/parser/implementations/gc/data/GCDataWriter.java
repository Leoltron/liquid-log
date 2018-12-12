package ru.naumen.sd40.log.parser.implementations.gc.data;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataWriter;

import static ru.naumen.sd40.log.parser.implementations.gc.data.GCDataType.*;

@Component
public class GCDataWriter implements IDataWriter<GCData>
{
    @Override
    public void writeFields(Point.Builder builder, long time, GCData gc, boolean printCsvData)
    {
        builder.addField(GC_TIMES, gc.getGcTimes())
                .addField(AVERAGE_GC_TIME, gc.getCalculatedAvg())
                .addField(MAX_GC_TIME, gc.getMaxGcTime());
    }
}
