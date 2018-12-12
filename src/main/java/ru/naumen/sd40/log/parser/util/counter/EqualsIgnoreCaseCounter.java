package ru.naumen.sd40.log.parser.util.counter;

public class EqualsIgnoreCaseCounter extends PatternMatchCounter
{
    private final String matchingString;

    public EqualsIgnoreCaseCounter(String matchingString)
    {
        this.matchingString = matchingString;
    }

    @Override
    protected boolean matches(String string)
    {
        return matchingString.equalsIgnoreCase(string);
    }
}
