package com.cgi.timereport.regex;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTestHarness {
    public static void main(String[] args) throws Exception {

        // Pattern Examples
        Pattern pattern1 = Pattern.compile("[brc]at");
        // Negation Pattern
        Pattern pattern2 = Pattern.compile("[^brc]at");
        // Range Pattern
        Pattern pattern3 = Pattern.compile("[a-c]at");
        Pattern pattern4 = Pattern.compile("foo[1-5]");
        Pattern pattern5 = Pattern.compile("foo[^1-5]");
        // Unions
        Pattern pattern6 = Pattern.compile("[0-4[6-8]]");
        // Intersections
        Pattern pattern7 = Pattern.compile("[0-9&&[345]]");
        // Subtraction
        Pattern pattern8 = Pattern.compile("[0-9&&[^345]]");

        // Predefined Character Classes
        // .    Any character
        // \d   A digit: [0-9]
        // \D   A non-digit: [^0-9]
        // \s   A whitespace character: [ \t\n\x0B\f\r]
        // \S   A non-whitespace character: [^\s]
        // \w   A word character. [a-zA-Z_0-9]
        // \W   A non-word character. [^\w]


        // - - - - - - Quantifiers - - - - - - -
        // Once or not at all
        Pattern pattern9 = Pattern.compile("a?");
        // Zero or more times
        Pattern pattern10 = Pattern.compile("a*");
        // One or more times
        Pattern pattern11 = Pattern.compile("a+");

        // - - - Capturing groups and character classes with Quantifiers - - -
        Pattern pattern12 = Pattern.compile("(dog){3}");
        Pattern pattern13 = Pattern.compile("[abc]{3}");

        // Greedy Quantifier
        Pattern pattern14 = Pattern.compile(".*foo");

        // Reluctant Quantifier
        Pattern pattern15 = Pattern.compile(".*?foo");

        // Possessive Quantifier
        Pattern pattern16 = Pattern.compile(".*+foo");

        // - - - - - - Boundary Matchers - - - - - - -
        // ^    The beginning of a line
        // $    The end of a line
        // \b   A word boundary
        // \B   A non-word boundary
        // \A   The beginning of the input
        // \G   The end of the previous match
        // \Z   The end of the input but for the terminator, if any
        // \z   The end of the input


        // Embedded Flag Expressions
        // Pattern.CANON_EQ     None
        // Pattern.CASE_INSENSITIVE     (?i)
        // Pattern.COMMENTS             (?x)
        // Pattern.MULTILINE            (?m)
        // Pattern.DOTALL               (?s)
        // Pattern.LITERAL              None
        // Pattern.UNICODE_CASE         (?u)
        // Pattern.UNIX_LINES           (?d)


        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your regex: ");
            String regex = scanner.nextLine();
            Pattern pattern = Pattern.compile(regex);

            System.out.println("Enter input string to Search: ");
            Matcher matcher = pattern.matcher(scanner.nextLine());

            // Pattern pattern = Pattern.compile(console.readLine("%nEnter your regex: "));
            // Matcher matcher = pattern.matcher(console.readLine("Enter input string to search: "));
            boolean found = false;
            while (matcher.find()) {
                System.out.format("I found the text" + " \"%s\" starting at " + "index %d and ending at index %d.%n",
                        matcher.group(), matcher.start(), matcher.end());
                found = true;
            }
            if (!found) {
                System.out.format("No match found.%n");
            }
        }
    }
}
