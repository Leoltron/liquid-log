package ru.naumen.sd40.log.parser;

import org.junit.Before;
import org.junit.Test;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.IDataStorage;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;

import java.text.ParseException;

import static org.mockito.Mockito.*;

public class LogParserTests
{

    private LogParser<Long> parser;
    private ITimeParser timeParser;
    private IDataParser<Long> dataParser;
    private IDataStorage<Long, DataSet> dataStorage;

    @Before
    public void setUp()
    {
        dataParser = mock(IDataParser.class);
        timeParser = mock(ITimeParser.class);
        dataStorage = mock(IDataStorage.class);

        parser = new LogParser<Long>(dataParser, d -> 0L)
        {
        };
    }

    @Test
    public void mustCallConsumerWhenTimeChanges() throws ParseException
    {
        String stringWithTime1 = "data1";
        String stringWithTime2 = "data2";
        String stringWithTime3 = "data3";

        when(timeParser.parseTime(stringWithTime1)).thenReturn(331256L);
        when(timeParser.parseTime(stringWithTime2)).thenReturn(600000L);
        when(timeParser.parseTime(stringWithTime3)).thenReturn(911111L);
        when(timeParser.getLastParsedTime()).thenReturn(0L, 331256L, 600000L, 911111L);

        parser.parseLine(stringWithTime1, timeParser, dataStorage);
        parser.parseLine(stringWithTime2, timeParser, dataStorage);
        parser.parseLine(stringWithTime3, timeParser, dataStorage);

        verify(dataStorage, times(2)).onDataChunkFinished(any());
    }
}
