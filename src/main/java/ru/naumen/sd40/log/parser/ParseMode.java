package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.data.DataSet;
import ru.naumen.sd40.log.parser.parsers.ILogParser;
import ru.naumen.sd40.log.parser.parsers.gc.GCParser;
import ru.naumen.sd40.log.parser.parsers.sdng.SdngParser;
import ru.naumen.sd40.log.parser.parsers.top.TopParser;
import ru.naumen.sd40.log.parser.util.Pair;

public enum ParseMode
{
    SDNG(32 * 1024 * 1024),
    TOP(),
    GC();

    public final int bufferSize;

    ParseMode()
    {
        this(8 * 1024);
    }

    ParseMode(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    public ILogParser<Pair<Long, DataSet>> getLogParser(String logFilePath)
    {
        switch (this)
        {
            case SDNG:
                return new SdngParser();
            case GC:
                return new GCParser();
            case TOP:
                return new TopParser(logFilePath);
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Available modes: sdng, gc, top. Requested mode: " + this.toString());
        }
    }
}
