package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import org.apache.commons.validator.routines.ISBNValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookPropertiesValidatorTest {

    private BookPropertiesValidator bookPropertiesValidator;

    @BeforeEach
    void init() {
        bookPropertiesValidator = new BookPropertiesValidator(
                new BookISBNManager(
                        ISBNValidator.getInstance()));
    }

    @Test
    void isValidBookPropertiesTestNoteFieldContainAnotherBook() {
        String notesField = "[1.], Fejér megye, 1773-1808 / [szerk. Szaszkóné Sin Aranka] 1987 136. p. + 1 térk. (78x55 cm) 963-02-4654-6 (fűzött) : 105,- Ft\n" +
                "[2.], Pest-Pilis-Solt megye és a Kiskunság, 1773-1808 / [szerk. Szaszkóné Sin Aranka] 1988 280 p. + 1 térk. (78x55 cm) 963-7056-38-6 (fűzött) : 292,- Ft\n" +
                "[3.], Sopron megye, 1773-1808 / szerk. Borsodi Csaba 1990 194 p. + 1 térk. (78x55 cm) 963-7071-65-2 (fűzött) : 258,- Ft";
        String ISBNField = "978-963-02-4653-8";
        String seeAlso = null;

        assertThat(bookPropertiesValidator.isValidBookProperties(notesField, ISBNField, seeAlso)).isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNisNull() {
        assertThat(bookPropertiesValidator.isValidBookProperties(null, null, null)).isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneValidISBN13() {
        assertThat(bookPropertiesValidator.isValidBookProperties(null, "978-963-05-1810-9", null)).isTrue();
    }

}