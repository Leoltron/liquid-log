package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.data.IDataSet;

public interface IDataParser<TDataContainer extends IDataSet>
{
    void parseLine(String line, TDataContainer dataContainer);
}
