package ru.naumen.sd40.log.parser.parsers;

public interface IDataConsumer<Data>
{
    void consume(Data data);
}
