package com.reka.lakatos.booksofhungary.isbnmanager.test;

import com.reka.lakatos.booksofhungary.isbnmanager.service.BookISBNManager;
import org.apache.commons.validator.routines.ISBNValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookISBNManagerTest {

    @Mock
    private ISBNValidator isbnValidator;

    @InjectMocks
    private BookISBNManager bookISBNManager;

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
        String validISBN = "978-963-06-5122-6";
        Mockito.when(isbnValidator.isValid("9789630651226")).thenReturn(true);
        boolean result = bookISBNManager.isValidISBN(validISBN);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void isValidISBNNotValid() {
        String notValidISBN = "978-963-00-7705-7";
        Mockito.when(isbnValidator.isValid("9789630077057")).thenReturn(false);

        boolean result = bookISBNManager.isValidISBN(notValidISBN);
        Assertions.assertThat(result).isFalse();
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
        String cleanIsbn10 = "963065122X";
        Mockito.when(isbnValidator.convertToISBN13(cleanIsbn10)).thenReturn("9789630651226");
        String resultISBN = bookISBNManager.convertISBNToISBN13(isbn10);
        Assertions.assertThat(resultISBN).isEqualTo("9789630651226");
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