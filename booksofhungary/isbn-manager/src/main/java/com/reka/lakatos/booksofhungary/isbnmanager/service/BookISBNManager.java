package com.reka.lakatos.booksofhungary.isbnmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookISBNManager {

    private static final String[] HUNGARY_ISBN_STARTING_NUMBERS = {"978963", "978615", "963"};
    private static final int ISBN10_LENGTH = 10;

    private final ISBNValidator isbnValidator;

    public boolean isValidISBN(final String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }

        return isbnValidator.isValid(cleanISBN(isbn));
    }

    public String convertISBNToISBN13(final String isbn) {
        final String cleanISBN = cleanISBN(isbn);

        return isISBN10(cleanISBN) ? isbnValidator.convertToISBN13(cleanISBN) : cleanISBN;
    }

    public String cleanISBN(String isbn) {
        return isbn.replaceAll("[-\\s]*", "");
    }

    public boolean isISBN10(String isbn) {
        return isbn.length() == ISBN10_LENGTH;
    }

    public boolean isHungaryISBN(String ISBN) {
        for (String startingNumber : HUNGARY_ISBN_STARTING_NUMBERS) {
            if (ISBN.startsWith(startingNumber)) {
                return true;
            }
        }
        return false;
    }
}
