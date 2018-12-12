package ru.naumen.sd40.log.parser.implementations.top.data;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.sd40.log.parser.IDataSet;

import static ru.naumen.sd40.log.parser.util.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.util.NumberUtils.roundToTwoPlaces;

/**
 * Cpu usage data, acquired from top output
 * @author dkolmogortsev
 *
 */
public final class TopData implements IDataSet
{
    private DescriptiveStatistics laStat = new DescriptiveStatistics();
    private DescriptiveStatistics cpuStat = new DescriptiveStatistics();
    private DescriptiveStatistics memStat = new DescriptiveStatistics();

    void addLa(double la)
    {
        laStat.addValue(la);
    }

    void addCpu(double cpu)
    {
        cpuStat.addValue(cpu);
    }

    void addMem(double mem)
    {
        memStat.addValue(mem);
    }

    public boolean isNan()
    {
        return laStat.getN() == 0 && cpuStat.getN() == 0 && memStat.getN() == 0;
    }

    double getAvgLa()
    {
        return roundToTwoPlaces(getSafeDouble(laStat.getMean()));
    }

    double getAvgCpuUsage()
    {
        return roundToTwoPlaces(getSafeDouble(cpuStat.getMean()));
    }

    double getAvgMemUsage()
    {
        return roundToTwoPlaces(getSafeDouble(memStat.getMean()));
    }

    double getMaxLa()
    {
        return roundToTwoPlaces(getSafeDouble(laStat.getMax()));
    }

    double getMaxCpu()
    {
        return roundToTwoPlaces(getSafeDouble(cpuStat.getMax()));
    }

    double getMaxMem()
    {
        return roundToTwoPlaces(getSafeDouble(memStat.getMax()));
    }
}