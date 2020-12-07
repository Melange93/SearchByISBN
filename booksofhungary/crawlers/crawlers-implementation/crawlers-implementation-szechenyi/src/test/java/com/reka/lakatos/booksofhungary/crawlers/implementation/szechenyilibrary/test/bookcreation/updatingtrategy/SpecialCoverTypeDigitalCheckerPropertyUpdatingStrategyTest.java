package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.bookcreation.updatingtrategy.SpecialCoverTypeDigitalCheckerPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialCoverTypeDigitalCheckerPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy digitalCheckerPropertyUpdatingStrategy;

    @BeforeEach
    public void init() {
        digitalCheckerPropertyUpdatingStrategy = new SpecialCoverTypeDigitalCheckerPropertyUpdatingStrategy();
    }

    @Test
    void updateProperty() {
        Book testBook = Book.builder().build();
        String testProperty = "anything";
        digitalCheckerPropertyUpdatingStrategy.updateProperty(testBook, testProperty);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.DIGITAL);
    }
}