package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.ActionDoneData;
import ru.naumen.sd40.log.parser.data.DataSet;

public class ActionDoneParser extends LogParser<ActionDoneData>
{
    public ActionDoneParser()
    {
        super(new ActionDoneDataParser(), new TimeParser(), DataSet::getActionsDone);
    }
}
