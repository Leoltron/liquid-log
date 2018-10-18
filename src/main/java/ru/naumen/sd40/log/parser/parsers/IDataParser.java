package ru.naumen.sd40.log.parser.parsers;

public interface IDataParser<TDataContainer>
{
    void parseLine(String line, TDataContainer dataContainer);
}
