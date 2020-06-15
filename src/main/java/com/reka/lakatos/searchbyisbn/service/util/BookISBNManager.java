package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookISBNManager {

    @Autowired
    private ISBNValidator isbnValidator;

    private static final int ISBN10_LENGTH = 10;

    public boolean isISBNValid(String isbn) {
        return isbnValidator.isValid(isbn);
    }

    public Book convertBookISBNToISBN13(Book book) {
        String isbn = book.getIsbn();

        if (isbn.isBlank()) {
            throw new NullPointerException("ISBN conversation failed because ISBN is blank in" + getClass().getSimpleName() + "class.");
        }

        isbn = getCleanISBN(isbn);

        if (isISBN10(isbn)) {
            isbn = isbnValidator.convertToISBN13(isbn);
        }

        isbn = getDashedISBN(isbn);
        book.setIsbn(isbn);
        return book;
    }

    String getCleanISBN(String isbn) {
        return isbn.replaceAll("[-\\s]", "");
    }

    boolean isISBN10(String isbn) {
        return isbn.length() == ISBN10_LENGTH;
    }

    String getDashedISBN(String isbn) {
        return isbn.replaceAll(".{3}", "$0-");
    }

}
