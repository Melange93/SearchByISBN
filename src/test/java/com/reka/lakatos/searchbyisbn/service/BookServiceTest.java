package com.reka.lakatos.searchbyisbn.service;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import com.reka.lakatos.searchbyisbn.service.util.BookRegistry;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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