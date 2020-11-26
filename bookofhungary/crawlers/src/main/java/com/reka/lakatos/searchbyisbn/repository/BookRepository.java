package com.reka.lakatos.searchbyisbn.repository;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

}
