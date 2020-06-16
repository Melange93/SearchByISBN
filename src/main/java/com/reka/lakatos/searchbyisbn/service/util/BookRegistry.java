package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class BookRegistry {

    @Autowired
    private BookRepository bookRepository;

    public RegistryResult registBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findById(book.getIsbn());
        if (optionalBook.isEmpty()) {
            bookRepository.save(book);
            return RegistryResult.SUCCESSFUL;
        }

        if (Objects.equals(optionalBook.get(), book)) {
            return RegistryResult.FAILED;
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
        if (fromDb.getSubtitle() == null && newOne.getSubtitle() != null) {
            fromDb.setSubtitle(newOne.getSubtitle());
        }
        if (fromDb.getAuthorNotice() == null && newOne.getAuthorNotice() != null) {
            fromDb.setAuthorNotice(newOne.getAuthorNotice());
        }
        if (fromDb.getYearOfRelease() == null && newOne.getYearOfRelease() != null) {
            fromDb.setYearOfRelease(newOne.getYearOfRelease());
        }
        if (fromDb.getContributors().isEmpty() && !newOne.getContributors().isEmpty()) {
            fromDb.setContributors(newOne.getContributors());
        }
        if (fromDb.getThickness() == 0.0 && newOne.getThickness() != 0.0) {
            fromDb.setThickness(newOne.getThickness());
        }
        if (fromDb.getCoverType() == null && newOne.getCoverType() != null) {
            fromDb.setCoverType(newOne.getCoverType());
        }
    }

}
