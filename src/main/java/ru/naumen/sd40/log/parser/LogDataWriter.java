package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.data.*;
import ru.naumen.sd40.log.parser.parsers.IDataConsumer;
import ru.naumen.sd40.log.parser.util.Pair;

import java.io.Closeable;

public class LogDataWriter implements IDataConsumer<Pair<Long, DataSet>>, Closeable
{
    private final InfluxDAO influxDAO;
    private final BatchPoints batchPoints;
    private final boolean printCsvData = System.getProperty("NoCsv") == null;
    private final String dbName;

    public LogDataWriter(String dbName)
    {
        if (dbName == null)
        {
            throw new NullPointerException("dbName cannot be null");
        }

        this.dbName = dbName.replaceAll("-", "_");
        influxDAO = new InfluxDAO(System.getProperty("influx.host"), System.getProperty("influx.user"),
                System.getProperty("influx.password"));
        influxDAO.init();
        influxDAO.connectToDB(this.dbName);

        batchPoints = influxDAO.startBatchPoints(this.dbName);

        if (printCsvData)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
    }

    @Override
    public void consume(Pair<Long, DataSet> longDataSetPair)
    {
        DataSet dataSet = longDataSetPair.item2;
        long time = longDataSetPair.item1;

        ActionDoneData adData = dataSet.getActionsDone();
        adData.calculate();
        ErrorData errorData = dataSet.getErrorData();
        if (printCsvData)
        {
            System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", time, adData.getCount(),
                    adData.getMin(), adData.getMean(), adData.getStddev(), adData.getPercent50(), adData.getPercent95(),
                    adData.getPercent99(), adData.getPercent999(), adData.getMax(), errorData.getErrorCount()));
        }
        if (!adData.isNan())
        {
            influxDAO.storeActionsFromLog(batchPoints, dbName, time, adData, errorData);
        }

        GCData gc = dataSet.getGcData();
        if (!gc.isNan())
        {
            influxDAO.storeGc(batchPoints, dbName, time, gc);
        }

        TopData cpuData = dataSet.cpuData();
        if (!cpuData.isNan())
        {
            influxDAO.storeTop(batchPoints, dbName, time, cpuData);
        }
    }

    @Override
    public void close()
    {
        influxDAO.writeBatch(batchPoints);
    }
}
