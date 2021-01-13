package com.reka.lakatos.booksofhungary.databasereader.database.service;

import com.reka.lakatos.booksofhungary.databasereader.database.domain.Book;
import com.reka.lakatos.booksofhungary.databasereader.database.repository.BookRepository;
import com.reka.lakatos.booksofhungary.databasereader.database.service.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book getBookByISBN(String isbn) {
        Optional<Book> bookByISBN = bookRepository.findById(isbn);
        if (bookByISBN.isPresent()) {
            return bookByISBN.get();
        }
        throw new BookNotFoundException("Book not found with this ISBN number.");
    }
}
