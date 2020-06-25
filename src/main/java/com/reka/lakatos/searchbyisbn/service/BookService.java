package com.reka.lakatos.searchbyisbn.service;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import com.reka.lakatos.searchbyisbn.service.util.BookRegistry;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

            return bookRegistry.registBook(book);
        }

        log.info("Failed in ISBN validation. ISBN: {}", book.getIsbn());

        return RegistryResult.FAILED;
    }
}
