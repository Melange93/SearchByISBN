package com.reka.lakatos.searchbyisbn.service;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private ISBNValidator isbnValidator;

    @Autowired
    private BookRepository bookRepository;

    public boolean saveBook(Book book) {
        String isbn = getCleanJustNumericalISBN(book.getIsbn());
        if (isISBNValid(isbn)) {
            if (isISBN10(isbn)) {
                isbn = isbnValidator.convertToISBN13(isbn);
            }
            isbn = getDashedISBN(isbn);
            if (isBookNotRegisted(isbn)) {
                book.setIsbn(isbn);
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }

    String getCleanJustNumericalISBN(String isbn) {
        return isbn.replaceAll("[^0-9]", "");
    }

    boolean isISBNValid(String isbn) {
        return isbnValidator.isValid(isbn);
    }

    boolean isISBN10(String isbn) {
        return isbn.length() == 10;
    }

    boolean isBookNotRegisted(String isbn) {
        return bookRepository.findBookByIsbn(isbn) == null;
    }

    String getDashedISBN(String isbn) {
        return isbn.replaceAll(".{3}", "$0-");
    }

}
