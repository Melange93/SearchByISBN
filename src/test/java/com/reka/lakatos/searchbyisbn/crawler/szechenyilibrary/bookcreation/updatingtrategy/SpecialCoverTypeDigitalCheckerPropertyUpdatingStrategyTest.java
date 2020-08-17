package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
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