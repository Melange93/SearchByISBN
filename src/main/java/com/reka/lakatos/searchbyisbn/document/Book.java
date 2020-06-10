package com.reka.lakatos.searchbyisbn.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("Book")
@AllArgsConstructor
@Data
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
}
