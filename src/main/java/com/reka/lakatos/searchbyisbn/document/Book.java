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
    private String id;
    private String author;
    private String title;
    private String subtitle;
    private String authorNotice;
    private String publisher;
    private String yearOfRelease;
    @Indexed
    private String isbn;
    private Set<String> contributors;
    private PhysicalCharacteristics physicalCharacteristics;
    private Set<String> booksIdsWhatContainThisBook;
    private Set<String> booksIdsWhatPartThisBook;

    public Book() {
        this.contributors = new HashSet<>();
        this.booksIdsWhatContainThisBook = new HashSet<>();
        this.booksIdsWhatPartThisBook = new HashSet<>();
    }

    public Book(String author,
                String title,
                String subtitle,
                String authorNotice,
                String publisher,
                String yearOfRelease,
                String isbn,
                Set<String> contributors,
                PhysicalCharacteristics physicalCharacteristics,
                Set<String> booksIdsWhatContainThisBook,
                Set<String> booksIdsWhatPartThisBook) {
        this.author = author;
        this.title = title;
        this.subtitle = subtitle;
        this.authorNotice = authorNotice;
        this.publisher = publisher;
        this.yearOfRelease = yearOfRelease;
        this.isbn = isbn;
        this.contributors = contributors;
        this.physicalCharacteristics = physicalCharacteristics;
        this.booksIdsWhatContainThisBook = booksIdsWhatContainThisBook;
        this.booksIdsWhatPartThisBook = booksIdsWhatPartThisBook;
    }
}
