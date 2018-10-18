package ru.naumen.sd40.log.parser.counter;

public abstract class PatternMatchCounter
{
    private final Counter counter = new Counter();

    public long getCount()
    {
        return counter.getCount();
    }

    protected abstract boolean matches(String string);

    public void incIfMatches(String string)
    {
        if (matches(string))
        {
            counter.increment();
        }
    }
}
