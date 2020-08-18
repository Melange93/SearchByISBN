package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ISBNPropertyValidatorStrategyTest {

    @Mock
    private BookISBNManager bookISBNManager;

    private PropertyValidatorStrategy isbnPropertyValidatorStrategy;

    @BeforeEach
    void init() {
        isbnPropertyValidatorStrategy = new DefaultISBNPropertyValidatorStrategy(bookISBNManager);
    }

    @Test
    void validatePropertyValidOne() {
        assertThat(isbnPropertyValidatorStrategy.validateProperty("978-963-06-5122-6")).isTrue();
    }

    @Test
    void validatePropertyNull() {
        assertThat(isbnPropertyValidatorStrategy.validateProperty(null)).isFalse();
    }

    @Test
    void validatePropertyBlank() {
        assertThat(isbnPropertyValidatorStrategy.validateProperty(" ")).isFalse();
    }

    @Test
    void validatePropertyTwoISBN13() {

        when(bookISBNManager.cleanISBN("978-963-06-5122-6")).thenReturn("9789630651226");
        when(bookISBNManager.cleanISBN("978-963-07-5122-6")).thenReturn("9789630751226");

        boolean result = isbnPropertyValidatorStrategy
                .validateProperty("978-963-06-5122-6 978-963-07-5122-6");
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyTwoISBN13OneISBN10() {
        boolean result = isbnPropertyValidatorStrategy
                .validateProperty("978-963-06-5122-6 978-963-07-5122-6 963-08-5122-6");
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyTwoISBN10() {

        when(bookISBNManager.cleanISBN("963-06-5122-6")).thenReturn("9630651226");
        when(bookISBNManager.cleanISBN("963-07-5122-6")).thenReturn("9630751226");

        boolean result = isbnPropertyValidatorStrategy.validateProperty("963-06-5122-6 963-07-5122-6");
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyOneISBN13OneISBN10ButDifferentBook() {
        String ISBN13 = "978-963-06-5122-6";
        String cleanISBN13 = "9789630651226";

        String ISBN10 = "963-08-0985-0";
        String cleanISBN10 = "9630809850";

        when(bookISBNManager.cleanISBN(ISBN13)).thenReturn(cleanISBN13);
        when(bookISBNManager.cleanISBN(ISBN10)).thenReturn(cleanISBN10);
        when(bookISBNManager.convertISBNToISBN13(cleanISBN10)).thenReturn("978-963-08-0985-6");

        boolean result = isbnPropertyValidatorStrategy
                .validateProperty(ISBN13 + " " + ISBN10);

        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyOneISBN13OneISBN10SameBook() {
        String sameBookISBN13 = "978-963-06-5122-6";
        String cleanISBN13 = "9789630651226";

        String sameBookISBN10 = "963-06-5122-X";
        String cleanISBN10 = "963065122X";

        when(bookISBNManager.cleanISBN(sameBookISBN13)).thenReturn(cleanISBN13);
        when(bookISBNManager.cleanISBN(sameBookISBN10)).thenReturn(cleanISBN10);
        when(bookISBNManager.convertISBNToISBN13(cleanISBN10)).thenReturn(cleanISBN13);

        boolean result = isbnPropertyValidatorStrategy
                .validateProperty(sameBookISBN10 + " " + sameBookISBN13);

        assertThat(result).isTrue();
    }
}