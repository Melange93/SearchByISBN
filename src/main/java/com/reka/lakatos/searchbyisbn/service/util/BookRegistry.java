package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookRegistry {

    @Autowired
    private BookRepository bookRepository;

    public RegistryResult registBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findById(book.getIsbn());
        if (optionalBook.isEmpty()) {
            bookRepository.save(book);
            return RegistryResult.SUCCESSFUL;
        }

        if (book.equals(optionalBook.get())) {
            return RegistryResult.ERROR;
        }

        return RegistryResult.UPDATE;

    }

}
