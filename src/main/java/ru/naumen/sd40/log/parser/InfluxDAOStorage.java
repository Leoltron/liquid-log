package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.statdata.Constants;

import java.io.Closeable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class InfluxDAOStorage<TData extends IDataSet> implements IDataStorage<TData>, Closeable
{
    private final IDataBuilder<TData> dataBuilder;
    private final IDataWriter<TData> dataWriter;
    private final InfluxDAO influxDAO;
    private final BatchPoints batchPoints;
    private final boolean printCsvData;
    private final String dbName;

    private final HashMap<Long, TData> processingDataSets = new HashMap<>();

    public InfluxDAOStorage(IDataBuilder<TData> dataBuilder, IDataWriter<TData> dataWriter, InfluxDAO influxDAO, String dbName)
    {
        this(dataBuilder, dataWriter, influxDAO, dbName, System.getProperty("NoCsv") != null);
    }

    InfluxDAOStorage(IDataBuilder<TData> dataBuilder, IDataWriter<TData> dataWriter, InfluxDAO influxDAO, String dbName, boolean printCsvData)
    {
        this.dataBuilder = dataBuilder;
        this.dataWriter = dataWriter;
        this.printCsvData = printCsvData;

        if (dbName == null)
        {
            throw new NullPointerException("dbName cannot be null");
        }

        this.dbName = dbName.replaceAll("-", "_");
        this.influxDAO = influxDAO;
        this.influxDAO.init();
        this.influxDAO.connectToDB(this.dbName);

        batchPoints = this.influxDAO.startBatchPoints(this.dbName);

        if (printCsvData)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
    }

    @Override
    public void onDataChunkFinished(long time)
    {
        TData dataSet = getData(time);
        Point.Builder builder = Point.measurement(Constants.MEASUREMENT_NAME).time(time, TimeUnit.MILLISECONDS);
        dataWriter.writeFields(builder, time,dataSet, printCsvData);
        influxDAO.store(batchPoints, dbName, builder.build());
    }

    @Override
    public void close()
    {
        influxDAO.writeBatch(batchPoints);
    }

    @Override
    public TData getData(long timeMillis)
    {
        if (!this.processingDataSets.containsKey(timeMillis))
        {
            processingDataSets.put(timeMillis, dataBuilder.createNew());
        }
        return processingDataSets.get(timeMillis);
    }
}
