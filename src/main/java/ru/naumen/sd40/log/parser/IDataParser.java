package ru.naumen.sd40.log.parser;

public interface IDataParser<TDataContainer extends IDataSet>
{
    void parseLine(String line, TDataContainer dataContainer);
}
