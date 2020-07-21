package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DatePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy datePropertyUpdatingStrategy;

    @BeforeEach
    void ini() {
        datePropertyUpdatingStrategy = new DatePropertyUpdatingStrategy();
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
        Book book = new Book();
        datePropertyUpdatingStrategy.updateProperty(book, property);

        String yearOfRelease = book.getYearOfRelease();
        assertThat(yearOfRelease).isEqualTo("2013");
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
        Book book = new Book();
        datePropertyUpdatingStrategy.updateProperty(book, property);

        String yearOfRelease = book.getYearOfRelease();
        assertThat(yearOfRelease).isNull();
    }


}