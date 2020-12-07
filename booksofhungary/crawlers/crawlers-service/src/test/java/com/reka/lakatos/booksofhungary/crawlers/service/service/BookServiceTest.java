package com.reka.lakatos.booksofhungary.crawlers.service.service;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.service.BookService;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.BookISBNManager;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.BookRegistry;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.RegistryResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRegistry bookRegistry;

    @Mock
    private BookISBNManager bookISBNManager;

    @InjectMocks
    private BookService bookService;

    @Test
    void saveBookSaveSuccess() {
        Book book = new Book();

        when(bookISBNManager.isValidISBN(book.getIsbn())).thenReturn(true);
        when(bookISBNManager.convertISBNToISBN13(book.getIsbn())).thenReturn("1");
        when(bookRegistry.registerBook(book)).thenReturn(RegistryResult.SUCCESSFUL);

        RegistryResult result = bookService.saveBook(book);

        assertThat(result).isNotEqualTo(RegistryResult.INVALID);
    }

    @Test
    void saveBookSaveFailed() {
        Book book = new Book();

        when(bookISBNManager.isValidISBN(book.getIsbn())).thenReturn(false);

        RegistryResult registryResult = bookService.saveBook(book);

        assertThat(registryResult).isEqualTo(RegistryResult.INVALID);
    }
}