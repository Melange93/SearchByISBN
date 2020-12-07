package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultPageNumberPropertyUpdatingStrategy;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPageNumberPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy pageNumberPropertyUpdatingStrategy;
    private static final int TEST_EDITION_INDEX = 0;

    @BeforeEach
    void ini() {
        pageNumberPropertyUpdatingStrategy = new DefaultPageNumberPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyCommon() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p. : ill. ; 31 cm";
        pageNumberPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        int pageNumberResult = testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber();
        assertThat(pageNumberResult).isEqualTo(152);
    }

    @Test
    void updatePropertyCommonWithMiddleText() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p., 31 cm";
        pageNumberPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        int pageNumberResult = testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber();
        assertThat(pageNumberResult).isEqualTo(152);
    }

    @Test
    void updatePropertyCartography() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p., 81x31 cm";
        pageNumberPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        int pageNumberResult = testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber();
        assertThat(pageNumberResult).isEqualTo(152);
    }

    @Test
    void updatePropertyPageWithExternalSourceSign() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "91, [2] p. ; 19 cm";
        pageNumberPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        int pageNumberResult = testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber();
        assertThat(pageNumberResult).isEqualTo(91);
    }

    @Test
    void updatePropertyPageWithExternalSourceSignAndWithAppendix() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "127, [5] p. ; 21 cm + 1 füz. (16 p.)";
        pageNumberPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        int pageNumberResult = testBook.getEditions().get(TEST_EDITION_INDEX).getPageNumber();
        assertThat(pageNumberResult).isEqualTo(143);
    }
}