package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.updatingtrategy;


import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.bookcreation.updatingtrategy.PublisherPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PublisherPropertyUpdatingStrategyTest {
    private static final int INDEX_OF_BASIC_EDITION = 0;

    private PropertyUpdatingStrategy propertyUpdatingStrategy;

    @BeforeEach
    void init() {
        propertyUpdatingStrategy = new PublisherPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyPublisher() {
        Book book = Book.builder().editions(Collections.singletonList(new Edition())).build();
        String property = "Budapest : Térkép-Center, 2012.";

        propertyUpdatingStrategy.updateProperty(book, property);

        String result = book.getPublisher();
        assertThat(result).isEqualTo("Térkép-Center");
    }

    @Test
    void updatePropertyDate() {
        Book book = Book.builder().editions(Collections.singletonList(new Edition())).build();
        String property = "Budapest : Térkép-Center, 2012.";

        propertyUpdatingStrategy.updateProperty(book, property);

        int result = book.getEditions().get(INDEX_OF_BASIC_EDITION).getYearOfRelease();
        assertThat(result).isEqualTo(2012);
    }

    @Test
    void updatePropertyInvalidDate() {
        Book book = Book.builder().editions(Collections.singletonList(new Edition())).build();
        String property = "Budapest : Térkép-Center, 2012-2018";

        propertyUpdatingStrategy.updateProperty(book, property);

        int result = book.getEditions().get(INDEX_OF_BASIC_EDITION).getYearOfRelease();
        assertThat(result).isEqualTo(0);
    }
}