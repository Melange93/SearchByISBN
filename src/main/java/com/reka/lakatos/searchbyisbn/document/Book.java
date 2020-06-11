package com.reka.lakatos.searchbyisbn.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String id;
    private String author;
    private String title;
    private String subtitle;
    private String authorNotice;
    private String publisher;
    private String yearOfRelease;
    @Indexed(unique = true)
    private String isbn;
    private List<String> contributors;
    private PhysicalCharacteristics physicalCharacteristics;

    public Book() {
        this.contributors = new ArrayList<>();
    }

    public Book(String author,
                String title,
                String subtitle,
                String authorNotice,
                String publisher,
                String yearOfRelease,
                String isbn,
                List<String> contributors,
                PhysicalCharacteristics physicalCharacteristics) {
        this.author = author;
        this.title = title;
        this.subtitle = subtitle;
        this.authorNotice = authorNotice;
        this.publisher = publisher;
        this.yearOfRelease = yearOfRelease;
        this.isbn = isbn;
        this.contributors = contributors;
        this.physicalCharacteristics = physicalCharacteristics;
    }
}
