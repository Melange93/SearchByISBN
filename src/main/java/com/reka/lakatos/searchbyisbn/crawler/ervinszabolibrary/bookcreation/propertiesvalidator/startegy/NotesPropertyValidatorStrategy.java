package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotesPropertyValidatorStrategy implements PropertyValidatorStrategy {

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-X]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-X]{13}))";

    @Override
    public boolean validateProperty(String property) {
        return property == null || !isNotesContainsAnotherBook(property);
    }

    private boolean isNotesContainsAnotherBook(String notesField) {
        Pattern isbnPattern = Pattern.compile(".*?" + ISBN13_REGEX + ".*" + "|" + ".*?" + ISBN10_REGEX + ".*");
        Matcher isbnMatcher = isbnPattern.matcher(notesField);
        return isbnMatcher.find();
    }
}
