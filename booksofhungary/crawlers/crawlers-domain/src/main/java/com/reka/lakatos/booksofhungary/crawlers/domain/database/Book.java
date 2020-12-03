package com.reka.lakatos.booksofhungary.crawlers.domain.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("Book")
@Data
@ToString
@Slf4j
@Builder
@AllArgsConstructor
public class Book {
    @Id
    private String isbn;
    private String author;
    private String title;
    private String publisher;
    private List<Edition> editions;
    private CoverType coverType;

    public Book() {
        this.editions = new ArrayList<>();
    }
}
