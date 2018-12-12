package ru.naumen.sd40.log.parser.implementations.sdng.data.error;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.StatDataType;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class ResponseDataType extends StatDataType
{
    public static final String PERCENTILE50 = "percent50";
    public static final String PERCENTILE95 = "percent95";
    public static final String PERCENTILE99 = "percent99";
    public static final String PERCENTILE999 = "percent999";
    public static final String MAX = "max";
    public static final String COUNT = "count";
    public static final String ERRORS = "errors";
    public static final String MEAN = "mean";
    public static final String STDDEV = "stddev";

    public static final String PATH_PART = "";

    private static List<String> getProps()
    {
        return Lists.newArrayList(TIME, COUNT, ERRORS, MEAN, STDDEV, PERCENTILE50, PERCENTILE95, PERCENTILE99,
                PERCENTILE999, MAX);
    }

    public ResponseDataType()
    {
        super(getProps(), PATH_PART, "history", "Responses");
    }
}
