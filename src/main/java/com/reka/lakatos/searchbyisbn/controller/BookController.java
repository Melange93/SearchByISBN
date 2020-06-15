package com.reka.lakatos.searchbyisbn.controller;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.BookService;
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

    /*
    @PostMapping("/save")
    public ResponseEntity<String> saveABook(@RequestBody Book book) {
        boolean result = bookService.saveBook(book);
        if (result) {
            return new ResponseEntity<>("Save was successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("The book is already registered with this ISBN code", HttpStatus.INTERNAL_SERVER_ERROR);
    }

     */

}
