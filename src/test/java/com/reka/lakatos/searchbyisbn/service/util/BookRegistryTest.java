package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRegistryTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookRegistry bookRegistry;

    @Test
    void registerBookRegistryResultSuccessful() {
        Book saveBook = new Book();
        when(bookRepository.findById(saveBook.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(saveBook)).thenReturn(saveBook);

        RegistryResult result = bookRegistry.registerBook(saveBook);
        assertThat(result).isEqualTo(RegistryResult.SUCCESSFUL);
        verify(bookRepository).save(saveBook);
    }

    @Test
    void registerBookRegistryResultFailed() {
        Book saveBook = Book.builder().isbn("1").build();
        when(bookRepository.findById(saveBook.getIsbn())).thenReturn(Optional.of(saveBook));

        RegistryResult result = bookRegistry.registerBook(saveBook);
        assertThat(result).isEqualTo(RegistryResult.DUPLICATE);
        verify(bookRepository, never()).save(saveBook);
    }

    @Test
    void registerBookRegistryResultUpdateAuthor() {
        Book savedBook = Book.builder().isbn("1").title("Test title").build();
        Book newBook = Book.builder()
                .isbn("1")
                .author("Test Test")
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getAuthor()).isEqualTo("Test Test");
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }

    @Test
    void registerBookRegistryResultUpdateTitle() {
        Book savedBook = Book.builder().isbn("1").author("Test One").build();
        Book newBook = Book.builder()
                .isbn("1")
                .title("Test title")
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getTitle()).isEqualTo("Test title");
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }

    @Test
    void registerBookRegistryResultUpdateYearOfRelease() {
        Book savedBook = Book.builder().isbn("1").author("Test One").build();
        Book newBook = Book.builder()
                .isbn("1")
                .yearOfRelease("[2003]")
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getYearOfRelease()).isEqualTo("[2003]");
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }

    @Test
    void registerBookRegistryResultUpdateContributors() {
        Book savedBook = Book.builder().isbn("1").author("Test One").build();
        Book newBook = Book.builder()
                .isbn("1")
                .contributors(Sets.newHashSet(Arrays.asList("Test test1", "Test test2")))
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getContributors()).isEqualTo(Sets.newHashSet(Arrays.asList("Test test1", "Test test2")));
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }


    @Test
    void registerBookRegistryResultUpdateThickness() {
        Book savedBook = Book.builder().isbn("1").author("Test One").build();
        Book newBook = Book.builder()
                .isbn("1")
                .thickness(20.1f)
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getThickness()).isEqualTo(20.1f);
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }

    @Test
    void registerBookRegistryResultUpdatePageNumber() {
        Book savedBook = Book.builder().isbn("1").author("Test One").build();
        Book newBook = Book.builder()
                .isbn("1")
                .pageNumber(516)
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getPageNumber()).isEqualTo(516);
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }

    @Test
    void registerBookRegistryResultUpdateCoverType() {
        Book savedBook = Book.builder().isbn("1").author("Test One").build();
        Book newBook = Book.builder()
                .isbn("1")
                .coverType(CoverType.SOUND_RECORD)
                .build();

        when(bookRepository.findById(newBook.getIsbn())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);

        RegistryResult result = bookRegistry.registerBook(newBook);

        assertThat(result).isEqualTo(RegistryResult.UPDATE);
        assertThat(savedBook.getCoverType()).isEqualTo(CoverType.SOUND_RECORD);
        verify(bookRepository).save(savedBook);
        verify(bookRepository, never()).save(newBook);
    }
}