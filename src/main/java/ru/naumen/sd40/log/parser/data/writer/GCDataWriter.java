package ru.naumen.sd40.log.parser.data.writer;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.GCData;

import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.*;

@Component
public class GCDataWriter implements IDataWriter<GCData>
{
    @Override
    public void writeFields(Point.Builder builder, long time, GCData gc, boolean printCsvData)
    {
        builder.addField(GCTIMES, gc.getGcTimes())
                .addField(AVARAGE_GC_TIME, gc.getCalculatedAvg())
                .addField(MAX_GC_TIME, gc.getMaxGcTime());
    }
}
