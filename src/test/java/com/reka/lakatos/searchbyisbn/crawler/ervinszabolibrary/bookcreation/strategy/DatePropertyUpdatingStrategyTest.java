package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy datePropertyUpdatingStrategy;

    @BeforeEach
    void ini() {
        datePropertyUpdatingStrategy = new DatePropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyCommon() {
        Book testBook = new Book();
        String testProperty = "2013";
        datePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getYearOfRelease()).isEqualTo("2013");
    }

    @Test
    void updatePropertyWithExternalSourceSign() {
        Book testBook = new Book();
        String testProperty = "[2013]";
        datePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getYearOfRelease()).isEqualTo("[2013]");
    }

}