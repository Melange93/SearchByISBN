package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultPublisherPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPublisherPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy publisherPropertyUpdatingStrategy;

    @BeforeEach
    void init() {
        publisherPropertyUpdatingStrategy = new DefaultPublisherPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyCommon() {
        Book testBook = new Book();
        String property = "Budapest : Európa,";
        publisherPropertyUpdatingStrategy.updateProperty(testBook, property);

        assertThat(testBook.getPublisher()).isEqualTo("Európa");
    }

    @Test
    void updatePropertyWithExternalSourceSign() {
        Book testBook = new Book();
        String property = "[Budapest] : Diario Lapk.,";
        publisherPropertyUpdatingStrategy.updateProperty(testBook, property);

        assertThat(testBook.getPublisher()).isEqualTo("Diario Lapk.");
    }

    @Test
    void updatePropertyReissueWithAnotherPublisher() {
        Book testBook = new Book();
        String property = "2. átd. kiad. [Budapest] : Kossuth : Editorial,";
        publisherPropertyUpdatingStrategy.updateProperty(testBook, property);

        assertThat(testBook.getPublisher()).isEqualTo("Editorial");
    }
}