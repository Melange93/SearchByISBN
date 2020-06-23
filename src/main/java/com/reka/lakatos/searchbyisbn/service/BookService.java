package com.reka.lakatos.searchbyisbn.service;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import com.reka.lakatos.searchbyisbn.service.util.BookRegistry;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRegistry bookRegistry;
    private final BookISBNManager bookISBNManager;

    public RegistryResult saveBook(Book book) {
        if (bookISBNManager.isValidISBN(book.getIsbn())) {
            String ISBN13 = bookISBNManager.convertISBNToISBN13(book.getIsbn());

            book.setIsbn(ISBN13);

            return bookRegistry.registBook(book);
        }

        return RegistryResult.FAILED;
    }
}
