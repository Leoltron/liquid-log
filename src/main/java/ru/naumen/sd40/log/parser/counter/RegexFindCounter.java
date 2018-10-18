package ru.naumen.sd40.log.parser.counter;

import java.util.regex.Pattern;

public class RegexFindCounter extends PatternMatchCounter
{
    private final Pattern pattern;

    public RegexFindCounter(Pattern pattern)
    {
        this.pattern = pattern;
    }

    public RegexFindCounter(String regex)
    {
        this(Pattern.compile(regex));
    }

    @Override
    protected boolean matches(String string)
    {
        return pattern.matcher(string).find();
    }
}
