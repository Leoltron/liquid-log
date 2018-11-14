package ru.naumen.sd40.log.parser.parsers;

public interface IDataStorage<TKey, TData>
{
    TData getData(TKey key);

    void onDataChunkFinished(TKey relatedKey);
}
