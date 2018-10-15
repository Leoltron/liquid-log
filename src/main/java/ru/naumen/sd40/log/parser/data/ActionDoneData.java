package ru.naumen.sd40.log.parser.data;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.sd40.log.parser.counter.EqualsIgnoreCaseCounter;
import ru.naumen.sd40.log.parser.counter.GetListActionCounter;
import ru.naumen.sd40.log.parser.counter.PatternMatchCounter;
import ru.naumen.sd40.log.parser.counter.RegexMatchCounter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by doki on 22.10.16.
 */
public final class ActionDoneData
{
    ArrayList<Integer> times = new ArrayList<>();
    double min;
    double mean;
    double stddev;
    double percent50;
    double percent95;
    double percent99;

    double percent999;
    double max;
    long count;

    private PatternMatchCounter addObjectActions = new EqualsIgnoreCaseCounter("addobjectaction");
    private PatternMatchCounter editObjectsActions = new EqualsIgnoreCaseCounter("editobjectaction");
    private PatternMatchCounter getListActions = new GetListActionCounter();
    private PatternMatchCounter commentActions = new RegexMatchCounter("(?i)[a-zA-Z]+comment[a-zA-Z]+");

    private PatternMatchCounter getFormActions = new RegexMatchCounter("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+Form[a-zA-Z]+");

    private PatternMatchCounter getDtObjectActions = new RegexMatchCounter("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+DtObject[a-zA-Z]+");

    private PatternMatchCounter searchActions = new RegexMatchCounter("(?i)[a-zA-Z]+search[a-zA-Z]+");

    private PatternMatchCounter getCatalogsActions = new EqualsIgnoreCaseCounter("getcatalogsaction");

    boolean nan = true;

    private HashMap<String, Integer> actions = new HashMap<>();

    public void calculate()
    {
        DescriptiveStatistics ds = new DescriptiveStatistics();
        times.forEach(ds::addValue);
        min = ds.getMin();
        mean = ds.getMean();
        stddev = ds.getStandardDeviation();
        percent50 = ds.getPercentile(50.0);
        percent95 = ds.getPercentile(95.0);
        percent99 = ds.getPercentile(99.0);
        percent999 = ds.getPercentile(99.9);
        max = ds.getMax();
        count = ds.getN();
        nan = count == 0;
    }

    public long geListActions()
    {
        return getListActions.getCount();
    }

    public HashMap<String, Integer> getActionsCounter()
    {
        return actions;
    }

    public long getAddObjectActions()
    {
        return addObjectActions.getCount();
    }

    public long getCommentActions()
    {
        return commentActions.getCount();
    }

    public long getCount()
    {
        return count;
    }

    public long getDtObjectActions()
    {
        return getDtObjectActions.getCount();
    }

    public long getEditObjectsActions()
    {
        return editObjectsActions.getCount();
    }

    public long getFormActions()
    {
        return getFormActions.getCount();
    }

    public long getCatalogsActions()
    {
        return getCatalogsActions.getCount();
    }

    public double getMax()
    {
        return max;
    }

    public double getMean()
    {
        return mean;
    }

    public double getMin()
    {
        return min;
    }

    public double getPercent50()
    {
        return percent50;
    }

    public double getPercent95()
    {
        return percent95;
    }

    public double getPercent99()
    {
        return percent99;
    }

    public double getPercent999()
    {
        return percent999;
    }

    public long getSearchActions()
    {
        return searchActions.getCount();
    }

    public double getStddev()
    {
        return stddev;
    }

    public ArrayList<Integer> getTimes()
    {
        return times;
    }

    public boolean isNan()
    {
        return nan;
    }

    public void add(String action, int t)
    {
        times.add(t);

        addObjectActions.incIfMatches(action);
        getCatalogsActions.incIfMatches(action);
        editObjectsActions.incIfMatches(action);
        commentActions.incIfMatches(action);
        getListActions.incIfMatches(action);
        getFormActions.incIfMatches(action);
        getDtObjectActions.incIfMatches(action);
        searchActions.incIfMatches(action);
    }
}
