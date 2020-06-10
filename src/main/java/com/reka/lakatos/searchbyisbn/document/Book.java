package com.reka.lakatos.searchbyisbn.document;

import lombok.AllArgsConstructor;
import lombok.Data;
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
    private String author;
    private String title;
    private String subtitle;
    private String authorNotice;
    private String publisher;
    private String yearOfRelease;
    private String isbn;
    private List<String> contributors;
    private PhysicalCharacteristics physicalCharacteristics;

    public Book() {
        this.contributors = new ArrayList<>();
    }
}
