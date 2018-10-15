package ru.naumen.sd40.log.parser.data;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import static ru.naumen.sd40.log.parser.util.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.util.NumberUtils.roundToTwoPlaces;

public final class GCData
{
    private DescriptiveStatistics ds = new DescriptiveStatistics();

    public double getCalculatedAvg()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMean()));
    }

    public long getGcTimes()
    {
        return ds.getN();
    }

    public double getMaxGcTime()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMax()));
    }

    public boolean isNan()
    {
        return getGcTimes() == 0;
    }

    public void addValue(double v)
    {
        ds.addValue(v);
    }
}
