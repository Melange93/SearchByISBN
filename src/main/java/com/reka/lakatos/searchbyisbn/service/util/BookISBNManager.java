package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookISBNManager {

    private static final int ISBN10_LENGTH = 10;
    private ISBNValidator isbnValidator = ISBNValidator.getInstance();

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
