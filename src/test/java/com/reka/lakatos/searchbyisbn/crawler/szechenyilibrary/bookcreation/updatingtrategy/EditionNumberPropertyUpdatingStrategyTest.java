package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultEditionNumberPropetryUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.Edition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class EditionNumberPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy editionNumberPropertyUpdating;

    @BeforeEach
    public void init() {
        editionNumberPropertyUpdating = new DefaultEditionNumberPropetryUpdatingStrategy();
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