package ru.naumen.sd40.log.parser.parsers.top;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.data.TopData;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.IDataStorage;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;

import java.text.ParseException;

@Component
public class TopParser implements ILogParser<Long, DataSet>
{
    private final IDataParser<TopData> dataParser;
    private IDataStorage<Long, DataSet> dataStorage;

    public TopParser(IDataParser<TopData> dataParser)
    {
        this.dataParser = dataParser;
    }

    @Override
    public void parseLine(String line, ITimeParser timeParser) throws ParseException
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
    public void setDataStorage(IDataStorage<Long, DataSet> dataStorage)
    {
        this.dataStorage = dataStorage;
    }

    @Override
    public String[] getCompatibleModes()
    {
        return new String[]{"top"};
    }
}
