package ru.naumen.sd40.log.parser.parsers.top;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.TopData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopDataParser implements IDataParser<TopData>
{
    private static final Pattern CPU_AND_MEM_PATTERN = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");
    private static final Pattern LOAD_AVERAGE_PATTERN = Pattern.compile(".*load average:(.*)");

    @Override
    public void parseLine(String line, TopData data)
    {
        if (data != null)
        {
            //get loadAvgMatcher
            Matcher loadAvgMatcher = LOAD_AVERAGE_PATTERN.matcher(line);
            if (loadAvgMatcher.find())
            {
                data.addLa(Double.parseDouble(loadAvgMatcher.group(1).split(",")[0].trim()));
                return;
            }

            //get cpu and mem
            Matcher cpuAndMemMatcher = CPU_AND_MEM_PATTERN.matcher(line);
            if (cpuAndMemMatcher.find())
            {
                data.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
                data.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
            }
        }
    }
}
