package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.Edition;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SizePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy sizePropertyUpdatingStrategy;
    private static final int TEST_EDITION_INDEX = 0;

    @BeforeEach
    void ini() {
        sizePropertyUpdatingStrategy = new SizePropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyCommon() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p. : ill. ; 31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getThickness()).isEqualTo(31);
        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber()).isEqualTo(152);
    }

    @Test
    void updatePropertyCommon2() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p., 31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getThickness()).isEqualTo(31);
        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber()).isEqualTo(152);
    }

    @Test
    void updatePropertyPageWithExternalSourceSign() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "91, [2] p. ; 19 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getThickness()).isEqualTo(19);
        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber()).isEqualTo(91);
    }

    @Test
    void updatePropertyPageWithExternalSourceSignAndWithAppendix() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "127, [5] p. ; 21 cm + 1 füz. (16 p.)";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getThickness()).isEqualTo(21);
        assertThat(testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber()).isEqualTo(143);
    }

}