package com.leandog.robogherk;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentFinder {

    private final Pattern pattern;

    public ArgumentFinder(String delimiter) {
        pattern = Pattern.compile(delimiter + ".+" + delimiter);
    }

    public String findArgument(String string) {
        List<String> arguments = new ArrayList<String>();

        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            arguments.add(removeOutterQuotes(new StringBuilder(matcher.group())));
        }

        return arguments.size() > 0 ? arguments.get(0) : "";
    }

    private String removeOutterQuotes(StringBuilder match) {
        return match.deleteCharAt(0).deleteCharAt(match.length() - 1).toString();
    }
}