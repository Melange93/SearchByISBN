package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookRegistry {

    private final BookRepository bookRepository;

    public RegistryResult registerBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findById(book.getIsbn());
        if (optionalBook.isEmpty()) {
            bookRepository.save(book);
            return RegistryResult.SUCCESSFUL;
        }

        if (Objects.equals(optionalBook.get(), book)) {
            log.info("The book is already registered. ISBN: {}", book.getIsbn());
            return RegistryResult.DUPLICATE;
        }

        updateEmptyFields(optionalBook.get(), book);
        bookRepository.save(optionalBook.get());
        return RegistryResult.UPDATE;

    }

    private void updateEmptyFields(Book fromDb, Book newOne) {
        if (fromDb.getAuthor() == null && newOne.getAuthor() != null) {
            fromDb.setAuthor(newOne.getAuthor());
        }
        if (fromDb.getTitle() == null && newOne.getTitle() != null) {
            fromDb.setTitle(newOne.getTitle());
        }
        if (fromDb.getYearOfRelease() == null && newOne.getYearOfRelease() != null) {
            fromDb.setYearOfRelease(newOne.getYearOfRelease());
        }
        if (fromDb.getContributors() == null && newOne.getContributors() != null) {
            fromDb.setContributors(newOne.getContributors());
        }
        if (fromDb.getThickness() == 0.0 && newOne.getThickness() != 0.0) {
            fromDb.setThickness(newOne.getThickness());
        }
        if (fromDb.getPageNumber() == 0 && newOne.getPageNumber() != 0) {
            fromDb.setPageNumber(newOne.getPageNumber());
        }
        if (fromDb.getCoverType() == null && newOne.getCoverType() != null) {
            fromDb.setCoverType(newOne.getCoverType());
        }
    }

}
