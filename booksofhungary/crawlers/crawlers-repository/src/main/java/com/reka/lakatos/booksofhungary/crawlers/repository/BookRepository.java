package com.reka.lakatos.booksofhungary.crawlers.repository;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

}
