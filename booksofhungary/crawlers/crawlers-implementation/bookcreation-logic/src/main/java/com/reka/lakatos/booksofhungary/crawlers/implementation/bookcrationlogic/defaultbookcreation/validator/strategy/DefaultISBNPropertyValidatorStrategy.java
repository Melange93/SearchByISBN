package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy;

import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.isbnmanager.service.BookISBNManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DefaultISBNPropertyValidatorStrategy implements PropertyValidatorStrategy {

    private final BookISBNManager bookISBNManager;

    private static final String ISBN10_AND_ISBN13 =
            "(\\d{1,3}([- ])\\d{1,5}\\2\\d{1,7}\\2\\d{1,6}\\2(\\d|X))" +
            "|(\\d{1,5}([- ])\\d{1,7}\\5\\d{1,6}\\5(\\d|X))" +
            "|(\\d{13})" +
            "|(\\d{10})";

    @Override
    public boolean validateProperty(final String property) {
        if (property == null) {
            return false;
        }
        return !containsISBNMoreThanOneBookISBN(property);
    }

    private boolean containsISBNMoreThanOneBookISBN(final String ISBNFieldValue) {
        final List<String> isbnFromISBNField = getISBNs(ISBNFieldValue);

        if (isbnFromISBNField.size() == 1) {
            return false;
        }

        if (isbnFromISBNField.size() > 2 || isbnFromISBNField.size() == 0) {
            return true;
        }

        final String isbnOne = isbnFromISBNField.get(0);
        final String isbnTwo = isbnFromISBNField.get(1);

        if (isbnOne.length() == isbnTwo.length()) {
            return true;
        }

        return !isTheSameBook(isbnOne, isbnTwo);
    }

    private boolean isTheSameBook(final String isbn13, final String isbn10) {
        return bookISBNManager.convertISBNToISBN13(isbn10).equals(isbn13);
    }

    private List<String> getISBNs(final String ISBNFieldValue) {
        List<String> isbns = new ArrayList<>();

        Matcher isbnMatcher = Pattern.compile(ISBN10_AND_ISBN13).matcher(ISBNFieldValue);
        while (isbnMatcher.find()) {
            String isbn = isbnMatcher.group().trim();
            isbn = bookISBNManager.cleanISBN(isbn);
            if (bookISBNManager.isHungaryISBN(isbn)) {
                isbns.add(isbn);
            }
        }

        return isbns;
    }
}
