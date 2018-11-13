package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.parsers.gc.GCParser;
import ru.naumen.sd40.log.parser.parsers.sdng.SdngParser;
import ru.naumen.sd40.log.parser.parsers.top.TopParser;
import ru.naumen.sd40.log.parser.util.Pair;

@Component
public class LogParseModes
{
    private final GCParser gcParser;
    private final SdngParser sdngParser;
    private final TopParser topParser;

    public LogParseModes(GCParser gcParser, SdngParser sdngParser, TopParser topParser)
    {
        this.gcParser = gcParser;
        this.sdngParser = sdngParser;
        this.topParser = topParser;
    }

    public ILogParser<Pair<Long, DataSet>> getLogParser(String mode)
    {
        switch (mode.toLowerCase())
        {
            case "sdng":
                return sdngParser;
            case "top":
                return topParser;
            case "gc":
                return gcParser;
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Available modes: sdng, gc, top. Requested mode: " + mode);
        }
    }

    public int getBufferSize(String mode)
    {
        switch (mode.toLowerCase())
        {
            case "sdng":
                return 32 * 1024 * 1024;
            default:
                return 8 * 1024;
        }
    }
}
