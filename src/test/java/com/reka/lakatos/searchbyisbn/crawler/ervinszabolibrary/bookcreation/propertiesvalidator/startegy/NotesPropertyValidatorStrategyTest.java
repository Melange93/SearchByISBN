package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotesPropertyValidatorStrategyTest {

    private PropertyValidatorStrategy notePropertyValidatorStrategy;

    @BeforeEach
    void init() {
        notePropertyValidatorStrategy = new NotesPropertyValidatorStrategy();
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