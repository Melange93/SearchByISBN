package com.reka.lakatos.searchbyisbn.service.util;

import org.apache.commons.validator.routines.ISBNValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookISBNManagerTest {

    @Mock
    private ISBNValidator isbnValidator;

    @InjectMocks
    private BookISBNManager bookISBNManager;

    private static final String VALID_ISBN = "978-963-06-5122-6";
    private static final String NOT_VALID_ISBN = "978-963-00-7705-7";


    @Test
    void isValidISBNNull() {
        assertThat(bookISBNManager.isValidISBN(null)).isFalse();
    }

    @Test
    void isValidISBNBlank() {
        assertThat(bookISBNManager.isValidISBN(" ")).isFalse();
    }

    @Test
    void isValidISBNValid() {
        when(isbnValidator.isValid(VALID_ISBN)).thenReturn(true);
        assertThat(bookISBNManager.isValidISBN(VALID_ISBN)).isTrue();
    }

    @Test
    void isValidISBNNotValid() {
        when(isbnValidator.isValid(NOT_VALID_ISBN)).thenReturn(false);
        assertThat(bookISBNManager.isValidISBN(NOT_VALID_ISBN)).isFalse();
    }

    @Test
    void convertISBNToISBN13InputISBN13() {
        String isbn13 = "978-963-06-5122-6";
        String cleanIsbn13 = "9789630651226";
        assertThat(bookISBNManager.convertISBNToISBN13(isbn13)).isEqualTo(cleanIsbn13);
    }

    @Test
    void convertISBNToISBN13InputISBN10() {
        String isbn10 = "963-06-5122-X";
        String cleanIsbn13 = "9789630651226";
        when(isbnValidator.convertToISBN13(isbn10)).thenReturn(cleanIsbn13);
        assertThat(bookISBNManager.convertISBNToISBN13(isbn10)).isEqualTo(cleanIsbn13);
    }

    @Test
    void cleanISBNWithIsbn10Slash() {
        String isbn10 = "963-06-5122-X";
        String cleanIsbn10 = "963065122X";
        assertThat(bookISBNManager.cleanISBN(isbn10)).isEqualTo(cleanIsbn10);
    }

    @Test
    void cleanISBNWithIsbn10Space() {
        String isbn10 = "963 06 5122 X";
        String cleanIsbn10 = "963065122X";
        assertThat(bookISBNManager.cleanISBN(isbn10)).isEqualTo(cleanIsbn10);
    }

    @Test
    void cleanISBNWithIsbn13Slash() {
        String isbn13 = "978-963-06-5122-6";
        String cleanIsbn13 = "9789630651226";
        assertThat(bookISBNManager.cleanISBN(isbn13)).isEqualTo(cleanIsbn13);
    }

    @Test
    void cleanISBNWithIsbn13Space() {
        String isbn13 = "978 963 06 5122 6";
        String cleanIsbn13 = "9789630651226";
        assertThat(bookISBNManager.cleanISBN(isbn13)).isEqualTo(cleanIsbn13);
    }

    @Test
    void isISBN10CleanISBN10() {
        assertThat(bookISBNManager.isISBN10("963065122X")).isTrue();
    }

    @Test
    void isISBN10NotCleanISBN10() {
        assertThat(bookISBNManager.isISBN10("963 06 5122 X")).isFalse();
    }

    @Test
    void isISBN10CleanISBN13() {
        assertThat(bookISBNManager.isISBN10("9789630651226")).isFalse();
    }

    @Test
    void isISBN10NotCleanISBN13() {
        assertThat(bookISBNManager.isISBN10("978 963 06 5122 6")).isFalse();
    }


}