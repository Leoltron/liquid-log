package ru.naumen.perfhouse.statdata;

import java.util.List;

public interface IDataType
{
    List<String> getTypeProperties();

    String getViewName();

    String getPathPartName();

    String getDisplayName();
}
