package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultBasicCoverTypePropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultISBNPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultBasicCoverTypePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy basicCoverTypePropertyUpdatingStrategy;

    @BeforeEach
    void ini() {
        basicCoverTypePropertyUpdatingStrategy = new DefaultBasicCoverTypePropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyHardcoreCoverTypeInISBN() {
        Book testBook = new Book();
        String testProperty = "978-963-06-5122-6 (kötött)";
        basicCoverTypePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getCoverType()).isEqualTo(CoverType.HARDCORE);
    }

    @Test
    void updatePropertyPaperbackCoverTypeInISBN() {
        Book testBook = new Book();
        String testProperty = "978-963-06-5122-6 (fűzött)";
        basicCoverTypePropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getCoverType()).isEqualTo(CoverType.PAPERBACK);
    }
}