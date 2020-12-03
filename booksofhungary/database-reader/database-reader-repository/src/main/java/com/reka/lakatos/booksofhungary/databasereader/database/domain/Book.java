package com.reka.lakatos.booksofhungary.databasereader.database.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Book")
public class Book {
    @Id
    private String isbn;
    private String author;
    private String title;
    private String publisher;
    private List<Edition> editions;
    private CoverType coverType;
}
