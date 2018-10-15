package ru.naumen.sd40.log.parser.data;

/**
 * Created by doki on 22.10.16.
 */
public final class DataSet
{
    private ActionDoneData actionsDone = new ActionDoneData();
    private ErrorData errorData = new ErrorData();
    private GCData gcData = new GCData();
    private TopData cpuData = new TopData();

    public ActionDoneData getActionsDone()
    {
        return actionsDone;
    }

    public ErrorData getErrorData()
    {
        return errorData;
    }

    public GCData getGcData()
    {
        return gcData;
    }

    public TopData cpuData()
    {
        return cpuData;
    }
}
