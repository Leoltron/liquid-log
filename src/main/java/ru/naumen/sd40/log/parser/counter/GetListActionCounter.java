package ru.naumen.sd40.log.parser.counter;

import java.util.regex.Pattern;

public class GetListActionCounter extends PatternMatchCounter
{
    private static final Pattern PATTERN = Pattern.compile("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+List[a-zA-Z]+");

    @Override
    protected boolean matches(String string)
    {
        return !string.toLowerCase().contains("advlist")
                && PATTERN.matcher(string).matches();
    }
}
