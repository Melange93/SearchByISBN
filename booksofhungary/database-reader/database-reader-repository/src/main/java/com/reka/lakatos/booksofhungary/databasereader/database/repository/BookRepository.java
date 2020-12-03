package com.reka.lakatos.booksofhungary.databasereader.database.repository;

import com.reka.lakatos.booksofhungary.databasereader.database.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
