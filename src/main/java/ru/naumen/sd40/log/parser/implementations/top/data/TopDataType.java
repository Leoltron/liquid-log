package ru.naumen.sd40.log.parser.implementations.top.data;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.StatDataType;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class TopDataType extends StatDataType
{
    public static final String AVG_LA = "avgLa";
    public static final String AVG_CPU = "avgCpu";
    public static final String AVG_MEM = "avgMem";
    public static final String MAX_LA = "maxLa";
    public static final String MAX_CPU = "maxCpu";
    public static final String MAX_MEM = "maxMem";

    public static final String PATH_PART = "top";

    private static List<String> getProps()
    {
        return Lists.newArrayList(TIME, AVG_LA, AVG_CPU, AVG_MEM, MAX_LA, MAX_CPU, MAX_MEM);
    }

    public TopDataType()
    {
        super(getProps(), PATH_PART, "history_top", "Top data");
    }
}
