package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
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
        String testISBN = "978-963-06-5122-6";
        when(bookISBNManager.cleanISBN(testISBN)).thenReturn("9789630651226");
        boolean result = isbnPropertyValidatorStrategy.validateProperty("978-963-06-5122-6");
        assertThat(result).isTrue();
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
        String testISBN = "978-963-06-5122-6 978-963-07-5122-6 963-08-5122-6";

        when(bookISBNManager.cleanISBN("978-963-06-5122-6")).thenReturn("9789630651226");
        when(bookISBNManager.cleanISBN("978-963-07-5122-6")).thenReturn("9789630751226");
        when(bookISBNManager.cleanISBN("963-08-5122-6")).thenReturn("9630851226");

        boolean result = isbnPropertyValidatorStrategy
                .validateProperty(testISBN);
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
        String testISBN = "978-963-06-5122-6 963-06-5122-X";

        when(bookISBNManager.cleanISBN("978-963-06-5122-6")).thenReturn("9789630651226");
        when(bookISBNManager.cleanISBN("963-06-5122-X")).thenReturn("963065122X");
        when(bookISBNManager.convertISBNToISBN13("963065122X")).thenReturn("9789630651226");

        boolean result = isbnPropertyValidatorStrategy
                .validateProperty(testISBN);

        assertThat(result).isTrue();
    }
}