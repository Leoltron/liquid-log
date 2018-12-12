package ru.naumen.sd40.log.parser;

public interface IDataBuilder<TData extends IDataSet>
{
    TData createNew();
}
