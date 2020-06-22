package com.reka.lakatos.searchbyisbn.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

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
    private String yearOfRelease;
    private Set<String> contributors;
    private float thickness;
    private CoverType coverType;

    public Book() {
        this.contributors = new HashSet<>();
    }
}
