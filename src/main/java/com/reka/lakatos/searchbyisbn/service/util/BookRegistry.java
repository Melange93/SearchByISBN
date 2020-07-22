package com.reka.lakatos.searchbyisbn.service.util;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.Edition;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
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
        if (fromDb.getEditions() != null && newOne.getEditions() != null) {
            updateEdition(fromDb, newOne);
        }
        if (fromDb.getAuthor() == null && newOne.getAuthor() != null) {
            fromDb.setAuthor(newOne.getAuthor());
        }
        if (fromDb.getTitle() == null && newOne.getTitle() != null) {
            fromDb.setTitle(newOne.getTitle());
        }
        if (fromDb.getEditions() == null && newOne.getEditions() != null) {
            fromDb.setEditions(newOne.getEditions());
        }
        if (fromDb.getCoverType() == null && newOne.getCoverType() != null) {
            fromDb.setCoverType(newOne.getCoverType());
        }
    }

    private void updateEdition(Book fromDb, Book newOne) {
        Optional<Edition> first = newOne.getEditions().stream().findFirst();
        if (first.isPresent()) {
            Edition newOneEdition = first.get();
            int editionIndex = findEdition(fromDb.getEditions(),
                    newOneEdition.getEditionNumber(),
                    newOneEdition.getYearOfRelease());

            if (editionIndex > -1) {
                Edition updatedEdition = updateEditionEmptyField(fromDb.getEditions().get(editionIndex), newOneEdition);
                fromDb.getEditions().set(editionIndex, updatedEdition);
            }
        }
    }

    private int findEdition(List<Edition> fromDbEditions, int editionNumber, int yearsOfRelease) {
        if (editionNumber == 0 && yearsOfRelease == 0) {
            return -1;
        }

        if (editionNumber != 0 && yearsOfRelease != 0) {
            Optional<Edition> found = fromDbEditions.stream()
                    .filter(edition -> edition.getEditionNumber() == editionNumber
                            && edition.getYearOfRelease() == yearsOfRelease)
                    .findFirst();
            if (found.isPresent()) {
                return fromDbEditions.indexOf(found.get());
            }
        }

        if (editionNumber != 0) {
            Optional<Edition> found = fromDbEditions.stream()
                    .filter(edition -> edition.getEditionNumber() == editionNumber)
                    .findFirst();
            if (found.isPresent()) {
                return fromDbEditions.indexOf(found.get());
            }
        }

        if (yearsOfRelease != 0) {
            Optional<Edition> found = fromDbEditions.stream()
                    .filter(edition -> edition.getYearOfRelease() == yearsOfRelease)
                    .findFirst();
            if (found.isPresent()) {
                return fromDbEditions.indexOf(found.get());
            }
        }

        return -1;
    }

    private Edition updateEditionEmptyField(Edition fromDb, Edition newOne) {
        if (fromDb.getYearOfRelease() == 0 && newOne.getYearOfRelease() != 0) {
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

        return fromDb;
    }
}
