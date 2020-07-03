package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookPropertiesValidator {

    private final BookISBNManager bookISBNManager;

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-X]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-X]{13}))";
    private static final int ISBN13_CLEAN_LENGTH = 13;
    private static final int ISBN10_CLEAN_LENGTH = 10;

    public boolean isValidBookProperties(String notesField, String ISBNField, String seeAlso) {
        if (notesField != null && containsBookAnotherBookCheckInNotes(notesField)) {
            return false;
        }

        if (seeAlso != null && seeAlso.matches("Részdokumentum")){
            return false;
        }

        if (ISBNField == null) {
            return false;
        }

        return !containsISBNMoreThanOneBookISBN(ISBNField);
    }

    private boolean containsBookAnotherBookCheckInNotes(String notesField) {
        Pattern isbnPattern = Pattern.compile(".*?" + ISBN13_REGEX + ".*" + "|" + ".*?" + ISBN10_REGEX + ".*");
        Matcher isbnMatcher = isbnPattern.matcher(notesField);
        return isbnMatcher.find();
    }

    private boolean containsISBNMoreThanOneBookISBN(String ISBNFieldValue) {
        List<String> isbNsFromISBNField = getISBNsFromISBNField(ISBNFieldValue);

        if (isbNsFromISBNField.size() == 1) {
            return false;
        }

        if (isbNsFromISBNField.size() > 2 || isbNsFromISBNField.size() == 0) {
            return true;
        }


        if (isSameISBNLength(isbNsFromISBNField, ISBN13_CLEAN_LENGTH)
                || isSameISBNLength(isbNsFromISBNField, ISBN10_CLEAN_LENGTH)) {
            return true;
        }

        return !isTheSameBook(isbNsFromISBNField.get(0), isbNsFromISBNField.get(1));
    }

    private boolean isTheSameBook(String isbn1, String isbn2) {
        isbn1 = bookISBNManager.cleanISBN(isbn1);
        isbn2 = bookISBNManager.cleanISBN(isbn2);
        if (bookISBNManager.isISBN10(isbn1)) {
            return bookISBNManager.convertISBNToISBN13(isbn1).equals(isbn2);
        }

        if (bookISBNManager.isISBN10(isbn2)) {
            return bookISBNManager.convertISBNToISBN13(isbn2).equals(isbn1);
        }
        return false;
    }

    private boolean isSameISBNLength(List<String> isbns, int isbnLength) {
        return isbns.stream()
                .map(bookISBNManager::cleanISBN)
                .mapToInt(String::length)
                .allMatch(i -> i == isbnLength);
    }

    private List<String> getISBNsFromISBNField(String ISBNFieldValue) {
        List<String> isbn13s = getISBNs(ISBNFieldValue, ISBN13_REGEX);
        String newISBNFieldValue = isbn13s.stream()
                .map(isbn13 -> ISBNFieldValue.replaceAll(isbn13, ""))
                .collect(Collectors.joining());
        return Stream.concat(isbn13s.stream(), getISBNs(newISBNFieldValue, ISBN10_REGEX).stream())
                .collect(Collectors.toList());
    }

    private List<String> getISBNs(String ISBNFieldValue, String ISBNRegex) {
        List<String> isnbs = new ArrayList<>();

        Pattern isbnPattern = Pattern.compile(ISBNRegex);
        Matcher isbnMatcher = isbnPattern.matcher(ISBNFieldValue);
        while (isbnMatcher.find()) {
            isnbs.add(isbnMatcher.group());
        }

        return isnbs;
    }
}
