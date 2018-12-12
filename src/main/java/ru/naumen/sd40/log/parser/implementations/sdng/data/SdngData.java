package ru.naumen.sd40.log.parser.implementations.sdng.data;

import ru.naumen.sd40.log.parser.IDataSet;
import ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone.ActionDoneData;
import ru.naumen.sd40.log.parser.implementations.sdng.data.error.ErrorData;

public class SdngData implements IDataSet
{
    private ActionDoneData actionDoneData = new ActionDoneData();
    private ErrorData errorData = new ErrorData();

    ActionDoneData getActionDoneData()
    {
        return actionDoneData;
    }

    ErrorData getErrorData()
    {
        return errorData;
    }
}
