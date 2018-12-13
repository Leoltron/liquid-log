package ru.naumen.sd40.log.parser.implementations.gc.data;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.AbstractDataType;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class GCDataType extends AbstractDataType
{
    public static final String GC_TIMES = "gcTimes";
    public static final String AVERAGE_GC_TIME = "avgGcTime";
    public static final String MAX_GC_TIME = "maxGcTime";

    public static final String PATH_PART = "gc";

    private static List<String> getProps()
    {
        return Lists.newArrayList(TIME, GC_TIMES, AVERAGE_GC_TIME, MAX_GC_TIME);
    }

    public GCDataType()
    {
        super(getProps(), PATH_PART, "gc_history", "Garbage Collection");
    }
}
