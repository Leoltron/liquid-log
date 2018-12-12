package ru.naumen.sd40.log.parser.implementations.top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.*;
import ru.naumen.sd40.log.parser.implementations.top.data.TopData;

import java.text.ParseException;

@Component
public class TopParser extends LogParser<TopData>
{
    private final IDataParser<TopData> dataParser;

    @Autowired
    public TopParser(IDataParser<TopData> dataParser, IDataBuilder<TopData> dataBuilder, IDataWriter<TopData> dataWriter)
    {
        super(dataParser, dataBuilder, dataWriter, ParseModeNames.TOP);
        this.dataParser = dataParser;
    }

    @Override
    public void parseLine(String line, ITimeParser timeParser, IDataStorage<TopData> dataStorage) throws ParseException
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
            dataParser.parseLine(line, dataStorage.getData(prevTime));
        }
    }

    @Override
    public int getPreferredBufferSize()
    {
        return 32 * 1024 * 1024;
    }
}
