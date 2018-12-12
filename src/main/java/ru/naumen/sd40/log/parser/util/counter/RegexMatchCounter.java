package ru.naumen.sd40.log.parser.util.counter;

import java.util.regex.Pattern;

public class RegexMatchCounter extends PatternMatchCounter
{
    private final Pattern pattern;

    private RegexMatchCounter(Pattern pattern)
    {
        this.pattern = pattern;
    }

    public RegexMatchCounter(String regex)
    {
        this(Pattern.compile(regex));
    }

    @Override
    protected boolean matches(String string)
    {
        return pattern.matcher(string).matches();
    }
}
