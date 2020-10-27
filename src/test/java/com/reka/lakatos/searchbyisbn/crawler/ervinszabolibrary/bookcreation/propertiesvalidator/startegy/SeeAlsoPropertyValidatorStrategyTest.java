package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeeAlsoPropertyValidatorStrategyTest {

    private PropertyValidatorStrategy seeAlsoPropertyValidatorStrategy;

    @BeforeEach
    private void init() {
        seeAlsoPropertyValidatorStrategy = new SeeAlsoPropertyValidatorStrategy();
    }

    @Test
    void validatePropertyNull() {
        boolean result = seeAlsoPropertyValidatorStrategy.validateProperty(null);
        assertThat(result).isTrue();
    }

    @Test
    void validatePropertyIsPartDocumentary() {
        boolean result = seeAlsoPropertyValidatorStrategy.validateProperty("Részdokumentum");
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyIsADocumentContainThisBook() {
        boolean result = seeAlsoPropertyValidatorStrategy.validateProperty("Ezt tartalmazó dokumentum");
        assertThat(result).isFalse();
    }

}