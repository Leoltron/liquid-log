package ru.naumen.sd40.log.parser;

import org.junit.Before;
import org.junit.Test;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.IDataConsumer;
import ru.naumen.sd40.log.parser.parsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.LogParser;
import ru.naumen.sd40.log.parser.util.Pair;

import java.text.ParseException;

import static org.mockito.Mockito.*;

public class LogParserTests
{

    private LogParser<Long> parser;
    private ITimeParser timeParser;
    private IDataParser<Long> dataParser;
    private IDataConsumer<Pair<Long, DataSet>> consumer;

    @Before
    public void setUp()
    {
        dataParser = mock(IDataParser.class);
        timeParser = mock(ITimeParser.class);

        parser = new LogParser<Long>(dataParser, timeParser, d -> 0L)
        {
        };
        parser.setDataConsumer(consumer = mock(IDataConsumer.class));
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

        parser.parseLine(stringWithTime1);
        parser.parseLine(stringWithTime2);
        parser.parseLine(stringWithTime3);

        verify(consumer, times(2)).consume(any());
    }

    @Test
    public void mustCallConsumerAfterClose() throws ParseException
    {
        String stringWithTime1 = "data1";
        when(timeParser.parseTime(stringWithTime1)).thenReturn(300000L);

        parser.parseLine(stringWithTime1);
        parser.close();

        verify(consumer, times(1)).consume(any());
    }
}
