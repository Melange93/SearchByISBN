package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.updatingstrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultTitlePropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTitlePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy titlePropertyUpdatingStrategy;

    @BeforeEach
    void init() {
        titlePropertyUpdatingStrategy = new DefaultTitlePropertyUpdatingStrategy();
    }

    @Test
    void updateProperty() {
        Book testBook = new Book();
        String testProperty = "Oz, a nagy varázsló [Hangfelvétel] some plus text / " +
                "L. Frank Baum ; ford. Szőllősy Klára ; Mácsai Pál előadásában";

        titlePropertyUpdatingStrategy.updateProperty(testBook, testProperty);
        assertThat(testBook.getTitle()).isEqualTo("Oz, a nagy varázsló some plus text");
    }
}