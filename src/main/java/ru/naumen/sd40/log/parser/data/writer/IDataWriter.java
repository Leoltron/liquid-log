package ru.naumen.sd40.log.parser.data.writer;

import org.influxdb.dto.Point;
import ru.naumen.sd40.log.parser.data.IDataSet;

public interface IDataWriter<TData extends IDataSet>
{
    void writeFields(Point.Builder builder, long time, TData data, boolean printCsvData);
}
