package com.reka.lakatos.booksofhungary.crawlers.service;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.isbnmanager.service.BookISBNManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.BookRegistry;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.RegistryResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRegistry bookRegistry;
    private final BookISBNManager bookISBNManager;

    public RegistryResult saveBook(Book book) {
        log.info("Start saving procedure. ISBN: {}", book.getIsbn());
        boolean validationResult = bookISBNManager.isValidISBN(book.getIsbn());
        if (validationResult) {
            String ISBN13 = bookISBNManager.convertISBNToISBN13(book.getIsbn());

            book.setIsbn(ISBN13);

            return bookRegistry.registerBook(book);
        }

        log.info("Failed in ISBN validation. ISBN: {}", book.getIsbn());

        return RegistryResult.INVALID;
    }
}
