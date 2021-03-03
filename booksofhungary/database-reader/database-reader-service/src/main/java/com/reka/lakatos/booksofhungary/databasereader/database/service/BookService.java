package com.reka.lakatos.booksofhungary.databasereader.database.service;

import com.reka.lakatos.booksofhungary.databasereader.database.domain.Book;
import com.reka.lakatos.booksofhungary.databasereader.database.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;

    public List<Book> getBooksByParam(String param, String input) {
        Query titleQuery = new Query()
                .addCriteria(
                        Criteria.where(param)
                                .regex(Pattern.compile((input), Pattern.CASE_INSENSITIVE))
                )
                .limit(2000);
        return mongoTemplate.find(titleQuery, Book.class);
    }
}
