package ru.naumen.sd40.log.parser.implementations.gc.data;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.sd40.log.parser.IDataSet;

import static ru.naumen.sd40.log.parser.util.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.util.NumberUtils.roundToTwoPlaces;

public final class GCData implements IDataSet
{
    private DescriptiveStatistics ds = new DescriptiveStatistics();

    double getCalculatedAvg()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMean()));
    }

    long getGcTimes()
    {
        return ds.getN();
    }

    double getMaxGcTime()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMax()));
    }

    public boolean isNan()
    {
        return getGcTimes() == 0;
    }

    void addValue(double v)
    {
        ds.addValue(v);
    }
}
