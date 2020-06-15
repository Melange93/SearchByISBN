package com.reka.lakatos.searchbyisbn.controller;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.BookService;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<RegistryResult> saveABook(@RequestBody Book book) {
        RegistryResult result = bookService.saveBook(book);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
