package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy.DefaultPublisherPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PublisherPropertyUpdatingStrategyTest {

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