package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultEditionNumberPropertyUpdatingStrategy;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultEditionNumberPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy editionNumberPropertyUpdatingStrategy;
    private static final int TEST_EDITION_INDEX = 0;
    private static final String EDITION_NUMBER_REGEX = "[\\d]+(?=\\.\\skiad\\.)";

    @BeforeEach
    void ini() {
        editionNumberPropertyUpdatingStrategy = new DefaultEditionNumberPropertyUpdatingStrategy(EDITION_NUMBER_REGEX);
    }

    @Test
    void updateProperty() {
        String property = "2. kiad. Budapest : Delta Vision,";
        Book book = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        editionNumberPropertyUpdatingStrategy.updateProperty(book, property);

        int editionNumber = book.getEditions().get(TEST_EDITION_INDEX).getEditionNumber();
        assertThat(editionNumber).isEqualTo(2);
    }
}