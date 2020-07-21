package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SizePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy sizePropertyUpdatingStrategy;

    @BeforeEach
    void ini() {
        sizePropertyUpdatingStrategy = new SizePropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyCommon() {
        Book testBook = new Book();
        String testProperty = "152 p. : ill. ; 31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getThickness()).isEqualTo(31);
        assertThat(testBook.getPageNumber()).isEqualTo(152);
    }

    @Test
    void updatePropertyCommon2() {
        Book testBook = new Book();
        String testProperty = "152 p., 31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getThickness()).isEqualTo(31);
        assertThat(testBook.getPageNumber()).isEqualTo(152);
    }

    @Test
    void updatePropertyPageWithExternalSourceSign() {
        Book testBook = new Book();
        String testProperty = "91, [2] p. ; 19 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getThickness()).isEqualTo(19);
        assertThat(testBook.getPageNumber()).isEqualTo(91);
    }

    @Test
    void updatePropertyPageWithExternalSourceSignAndWithAppendix() {
        Book testBook = new Book();
        String testProperty = "127, [5] p. ; 21 cm + 1 füz. (16 p.)";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getThickness()).isEqualTo(21);
        assertThat(testBook.getPageNumber()).isEqualTo(143);
    }

}