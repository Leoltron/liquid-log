package ru.naumen.sd40.log.parser;

import org.influxdb.dto.Point;

public interface IDataWriter<TData extends IDataSet>
{
    void writeFields(Point.Builder builder, long time, TData data, boolean printCsvData);
}
