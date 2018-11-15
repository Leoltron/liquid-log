package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.data.*;
import ru.naumen.sd40.log.parser.parsers.IDataStorage;

import java.io.Closeable;
import java.util.HashMap;

public class InfluxDAOStorage implements IDataStorage<Long, DataSet>, Closeable
{
    private final InfluxDAO influxDAO;
    private final BatchPoints batchPoints;
    private final boolean printCsvData;
    private final String dbName;

    private final HashMap<Long, DataSet> processingDataSets = new HashMap<>();

    public InfluxDAOStorage(InfluxDAO influxDAO, String dbName)
    {
        this(influxDAO, dbName, System.getProperty("NoCsv") != null);
    }

    public InfluxDAOStorage(InfluxDAO influxDAO, String dbName, boolean printCsvData)
    {
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
    public void onDataChunkFinished(Long time)
    {
        DataSet dataSet = getData(time);

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

    @Override
    public DataSet getData(Long timeMillis)
    {
        if (!this.processingDataSets.containsKey(timeMillis))
        {
            processingDataSets.put(timeMillis, new DataSet());
        }
        return processingDataSets.get(timeMillis);
    }
}
