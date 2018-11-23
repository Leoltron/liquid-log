package ru.naumen.sd40.log.parser.data.builder;

import ru.naumen.sd40.log.parser.data.IDataSet;

public interface IDataBuilder<TData extends IDataSet>
{
    TData createNew();
}
