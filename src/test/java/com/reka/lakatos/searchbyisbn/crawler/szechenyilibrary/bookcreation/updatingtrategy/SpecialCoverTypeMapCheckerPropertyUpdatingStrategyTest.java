package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
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