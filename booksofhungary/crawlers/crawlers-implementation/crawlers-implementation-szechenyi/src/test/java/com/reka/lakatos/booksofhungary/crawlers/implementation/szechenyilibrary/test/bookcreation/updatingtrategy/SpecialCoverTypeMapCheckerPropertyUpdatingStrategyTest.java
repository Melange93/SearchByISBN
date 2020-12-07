package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.bookcreation.updatingtrategy.SpecialCoverTypeMapCheckerPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialCoverTypeMapCheckerPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy propertyUpdatingStrategy;

    @BeforeEach
    public void init() {
        propertyUpdatingStrategy = new SpecialCoverTypeMapCheckerPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertySetCoverType() {
        Book testBook = Book.builder().build();
        String property = "Kosovó térképe";

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.MAP);
    }

    @Test
    void updatePropertyHaveCoverType() {
        Book testBook = Book.builder().coverType(CoverType.PAPERBACK).build();
        String property = "Kosovó térképe";

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.PAPERBACK);
    }
}