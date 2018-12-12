package ru.naumen.sd40.log.parser;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.mockito.Mockito.*;

@SuppressWarnings({"unchecked", "FieldCanBeLocal"})
public class LogParserTests
{
    private static class DataSet implements IDataSet
    {

    }

    private static class DataSetBuilder implements IDataBuilder<DataSet>{

        @Override
        public DataSet createNew()
        {
            return new DataSet();
        }
    }

    private LogParser<DataSet> parser;
    private ITimeParser timeParser;
    private IDataParser<DataSet> dataParser;
    private IDataBuilder<DataSet> dataBuilder;
    private IDataWriter<DataSet> dataWriter;
    private IDataStorage<DataSet> dataStorage;

    @Before
    public void setUp()
    {
        dataParser = mock(IDataParser.class);
        dataBuilder = new DataSetBuilder();
        dataWriter = mock(IDataWriter.class);
        timeParser = mock(ITimeParser.class);
        dataStorage = mock(IDataStorage.class);

        parser = new LogParser<DataSet>(dataParser, dataBuilder, dataWriter)
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
