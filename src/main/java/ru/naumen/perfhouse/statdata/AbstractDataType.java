package ru.naumen.perfhouse.statdata;

import java.util.List;

public abstract class AbstractDataType implements IDataType
{
    private List<String> properties;
    private String pathPart;
    private String viewName;
    private String displayName;

    protected AbstractDataType(List<String> properties, String pathPart, String viewName, String displayName)
    {
        this.properties = properties;
        this.pathPart = pathPart;
        this.viewName = viewName;
        this.displayName = displayName;
    }

    public List<String> getTypeProperties()
    {
        return this.properties;
    }

    @Override
    public String getViewName()
    {
        return viewName;
    }

    @Override
    public String getPathPartName()
    {
        return pathPart;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }
}
