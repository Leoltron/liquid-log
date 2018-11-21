package ru.naumen.sd40.log.parser.parsers;

public interface IDataStorage<TData>
{
    TData getData(long date);

    void onDataChunkFinished(long relatedDate);
}
