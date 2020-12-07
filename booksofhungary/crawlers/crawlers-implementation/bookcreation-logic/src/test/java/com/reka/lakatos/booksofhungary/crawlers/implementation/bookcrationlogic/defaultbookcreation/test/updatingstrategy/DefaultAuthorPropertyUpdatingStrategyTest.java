package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultAuthorPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultAuthorPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy authorPropertyUpdatingStrategy;

    @BeforeEach
    void init() {
        authorPropertyUpdatingStrategy = new DefaultAuthorPropertyUpdatingStrategy();
    }

    @Test
    void updateProperty() {
        Book testBook = new Book();
        String testProperty = "Adenauer, Konrad (1876-1967)";
        authorPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getAuthor()).isEqualTo("Adenauer, Konrad (1876-1967)");
    }
}