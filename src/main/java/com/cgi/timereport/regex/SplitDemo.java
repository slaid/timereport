package com.cgi.timereport.regex;

import java.util.regex.Pattern;

public class SplitDemo {
    private static final String REGEX = ":";
    private static final String REGEX1 = "\\d";
    private static final String INPUT = "one:two:three:four:five";
    private static final String INPUT1 = "one9two4three7four1five";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        String[] items = p.split(INPUT);
        for (String s: items)
            System.out.println(s);
        System.out.println("- - - - - - - - ");
        Pattern p1 = Pattern.compile(REGEX1);
        String[] items1 = p1.split(INPUT1);
        for (String string: items1) {
            System.out.println(string);
        }
        System.out.println("- - - - - - - - ");

    }
}
