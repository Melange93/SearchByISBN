package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultDatePropertyUpdatingStrategy;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDatePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy datePropertyUpdatingStrategy;
    private static final int TEST_EDITION_INDEX = 0;

    @BeforeEach
    void ini() {
        datePropertyUpdatingStrategy = new DefaultDatePropertyUpdatingStrategy();
    }

    @Test
    void setEditionNumberNoEditionNumber() {
        String property = "Budapest : Delta Vision,";
        Book book = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        datePropertyUpdatingStrategy.updateProperty(book, property);

        int editionNumber = book.getEditions().get(TEST_EDITION_INDEX).getEditionNumber();
        assertThat(editionNumber).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2013",
            "2013-",
            "[2013]",
            "[2013]-",
            "cop. 2013",
            "cop. 2013-",
            "cop. [2013]",
            "cop. [2013]-"})
    void updatePropertyValidDates(String property) {
        Book book = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        datePropertyUpdatingStrategy.updateProperty(book, property);

        int yearOfRelease = book.getEditions().get(TEST_EDITION_INDEX).getYearOfRelease();
        assertThat(yearOfRelease).isEqualTo(2013);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2013 2014",
            "2013-2014",
            "[2013] [2014]",
            "[2013]-[2014]",
            "[2013]-2014",
            "cop. 2013-2014",
            "2. kiadás cop. 2013-2014",
            "cop. [2013]-2014",
            "cop. [2013]-[2222]"})
    void updatePropertyInvalidDates(String property) {
        Book book = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        datePropertyUpdatingStrategy.updateProperty(book, property);

        int yearOfRelease = book.getEditions().get(TEST_EDITION_INDEX).getYearOfRelease();
        assertThat(yearOfRelease).isEqualTo(0);
    }

    @Test
    void updatePropertyDate() {
        Book book = Book.builder().editions(Collections.singletonList(new Edition())).build();
        String property = "Budapest : Térkép-Center, 2012.";

        datePropertyUpdatingStrategy.updateProperty(book, property);

        int result = book.getEditions().get(TEST_EDITION_INDEX).getYearOfRelease();
        assertThat(result).isEqualTo(2012);
    }

    @Test
    void updatePropertyInvalidDate() {
        Book book = Book.builder().editions(Collections.singletonList(new Edition())).build();
        String property = "Budapest : Térkép-Center, 2012-2018";

        datePropertyUpdatingStrategy.updateProperty(book, property);

        int result = book.getEditions().get(TEST_EDITION_INDEX).getYearOfRelease();
        assertThat(result).isEqualTo(0);
    }
}
