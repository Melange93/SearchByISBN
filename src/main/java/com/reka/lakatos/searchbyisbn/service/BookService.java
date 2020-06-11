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
        String isbn = book.getIsbn().replaceAll("[^0-9]", "");
        if (isbnValidator.isValid(isbn)) {
            if (isbn.length() == 10) {
                isbn = isbnValidator.convertToISBN13(isbn);
            }
            book.setIsbn(isbn);
            saveBook(bookRepository.save(book));
            return true;
        }
        return false;
    }

}
