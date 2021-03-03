package com.reka.lakatos.booksofhungary.databasereader.database.controller;

import com.reka.lakatos.booksofhungary.databasereader.database.domain.Book;
import com.reka.lakatos.booksofhungary.databasereader.database.domain.BookResponse;
import com.reka.lakatos.booksofhungary.databasereader.database.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @RequestMapping(method = RequestMethod.GET, value = "/searchbook")
    public List<BookResponse> getBooksByRequestParam(
            @RequestParam(name = "param") String param,
            @RequestParam(name = "input") String input
    ) {
        if (param == null || input == null) {
            return new ArrayList<>();
        }
        return mapBooksToBookResponses(bookService.getBooksByParam(param, input));
    }

    private List<BookResponse> mapBooksToBookResponses(List<Book> books) {
        return books.stream()
                .map(this::mapBookToBookResponse)
                .collect(Collectors.toList());
    }

    private BookResponse mapBookToBookResponse(Book book) {
        return BookResponse.builder()
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .title(book.getTitle())
                .build();
    }
}
