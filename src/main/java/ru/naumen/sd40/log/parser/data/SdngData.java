package ru.naumen.sd40.log.parser.data;

public class SdngData implements IDataSet
{
    private ActionDoneData actionDoneData = new ActionDoneData();
    private ErrorData errorData = new ErrorData();

    public ActionDoneData getActionDoneData()
    {
        return actionDoneData;
    }

    public ErrorData getErrorData()
    {
        return errorData;
    }
}
