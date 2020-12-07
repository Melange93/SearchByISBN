package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultEditionNumberPropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEditionNumberPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy editionNumberPropertyUpdating;

    @BeforeEach
    public void init() {
        editionNumberPropertyUpdating = new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+");
    }

    @Test
    void updateProperty() {
        Book testBook = Book.builder().editions(Collections.singletonList(new Edition())).build();
        String testProperty = "2. jav. kiad.";
        editionNumberPropertyUpdating.updateProperty(testBook, testProperty);
        int editionNumberResult = testBook.getEditions().get(0).getEditionNumber();
        assertThat(editionNumberResult).isEqualTo(2);
    }
}