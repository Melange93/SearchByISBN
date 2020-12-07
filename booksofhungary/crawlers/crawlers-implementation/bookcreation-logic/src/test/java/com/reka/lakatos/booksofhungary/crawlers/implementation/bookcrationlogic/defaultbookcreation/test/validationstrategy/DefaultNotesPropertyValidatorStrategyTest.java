package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.test.validationstrategy;

import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy.DefaultNotesPropertyValidatorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultNotesPropertyValidatorStrategyTest {

    private PropertyValidatorStrategy notePropertyValidatorStrategy;

    @BeforeEach
    void init() {
        notePropertyValidatorStrategy = new DefaultNotesPropertyValidatorStrategy();
    }

    @Test
    void validatePropertyContainsOtherBook() {
        String testProperty = "963-02-4654-6 (fűzött)";

        boolean result = notePropertyValidatorStrategy.validateProperty(testProperty);

        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyNull() {
        boolean result = notePropertyValidatorStrategy.validateProperty(null);
        assertThat(result).isTrue();
    }

    @Test
    void validatePropertyBlank() {
        boolean result = notePropertyValidatorStrategy.validateProperty(" ");
        assertThat(result).isTrue();
    }
}