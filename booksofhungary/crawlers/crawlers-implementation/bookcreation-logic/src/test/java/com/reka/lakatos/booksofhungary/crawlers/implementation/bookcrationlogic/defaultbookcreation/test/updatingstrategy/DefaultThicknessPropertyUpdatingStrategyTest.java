package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultThicknessPropertyUpdatingStrategy;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultThicknessPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy sizePropertyUpdatingStrategy;
    private static final int TEST_EDITION_INDEX = 0;

    @BeforeEach
    void ini() {
        sizePropertyUpdatingStrategy = new DefaultThicknessPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyCommon() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p. : ill. ; 31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        float thicknessResult = testBook.getEditions().get(TEST_EDITION_INDEX).getThickness();
        assertThat(thicknessResult).isEqualTo(31);
    }

    @Test
    void updatePropertyCommonWithMiddleText() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p., 31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        float thicknessResult = testBook.getEditions().get(TEST_EDITION_INDEX).getThickness();
        assertThat(thicknessResult).isEqualTo(31);
    }

    @Test
    void updatePropertyCartography() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "152 p., 81x31 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        float thicknessResult = testBook.getEditions().get(TEST_EDITION_INDEX).getThickness();
        assertThat(thicknessResult).isEqualTo(0);
    }

    @Test
    void updatePropertyPageWithExternalSourceSign() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "91, [2] p. ; 19 cm";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        float thicknessResult = testBook.getEditions().get(TEST_EDITION_INDEX).getThickness();
        assertThat(thicknessResult).isEqualTo(19.0f);
    }

    @Test
    void updatePropertyPageWithExternalSourceSignAndWithAppendix() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "127, [5] p. ; 21 cm + 1 füz. (16 p.)";
        sizePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        float thicknessResult = testBook.getEditions().get(TEST_EDITION_INDEX).getThickness();
        assertThat(thicknessResult).isEqualTo(21);
    }

}