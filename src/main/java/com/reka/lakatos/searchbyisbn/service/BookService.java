package com.reka.lakatos.searchbyisbn.service;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import com.reka.lakatos.searchbyisbn.service.util.BookRegistry;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRegistry bookRegistry;

    @Autowired
    private BookISBNManager bookISBNManager;

    public RegistryResult saveBook(Book book) {
        if (bookISBNManager.isISBNValid(book.getIsbn())) {
            Book toISBN13 = bookISBNManager.convertBookISBNToISBN13(book);
            return bookRegistry.registBook(toISBN13);
        }
        return  RegistryResult.ERROR;
    }




}
