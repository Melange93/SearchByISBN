package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy authorPropertyUpdatingStrategy;

    @BeforeEach
    void init() {
        authorPropertyUpdatingStrategy = new AuthorPropertyUpdatingStrategy();
    }

    @Test
    void updateProperty() {
        Book testBook = new Book();
        String testProperty = "Adenauer, Konrad (1876-1967)";
        authorPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getAuthor()).isEqualTo("Adenauer, Konrad (1876-1967)");
    }
}