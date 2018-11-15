package ru.naumen.sd40.log.parser.parsers.top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.TopData;
import ru.naumen.sd40.log.parser.parsers.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Component
public class TopParser implements ILogParser<Long, DataSet>
{
    private final IDataParser<TopData> dataParser;

    @Autowired
    public TopParser(IDataParser<TopData> dataParser)
    {
        this.dataParser = dataParser;
    }

    @Override
    public void parseLine(String line, ITimeParser timeParser, IDataStorage<Long, DataSet> dataStorage) throws ParseException
    {
        long prevTime = timeParser.getLastParsedTime();
        long time = timeParser.parseTime(line);
        if (time != 0)
        {
            if (prevTime > 0)
            {
                dataStorage.onDataChunkFinished(prevTime);
            }
        } else if (prevTime > 0)
        {
            dataParser.parseLine(line, dataStorage.getData(prevTime).cpuData());
        }
    }

    @Override
    public List<String> getCompatibleModes()
    {
        return Collections.singletonList(ParseModeNames.TOP);
    }
}
