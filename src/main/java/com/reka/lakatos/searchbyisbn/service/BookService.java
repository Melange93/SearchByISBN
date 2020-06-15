package com.reka.lakatos.searchbyisbn.service;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import com.reka.lakatos.searchbyisbn.service.util.BookRegistry;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRegistry bookRegistry;

    public RegistryResult saveBook(Book book) {
        return bookRegistry.registBook(book);
    }




}
