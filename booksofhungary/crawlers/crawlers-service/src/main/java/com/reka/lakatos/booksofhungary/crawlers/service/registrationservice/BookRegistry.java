package com.reka.lakatos.booksofhungary.crawlers.service.registrationservice;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Edition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.reka.lakatos.booksofhungary.crawlers.repository.BookRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookRegistry {

    private final BookRepository bookRepository;

    private static final int NEW_BOOK_EDITION_INDEX = 0;

    public RegistryResult registerBook(Book book) {
        if (!isValidEdition(book)) {
            book.setEditions(null);
        }

        Optional<Book> optionalBook = bookRepository.findById(book.getIsbn());
        if (optionalBook.isEmpty()) {
            bookRepository.save(book);
            return RegistryResult.SUCCESSFUL;
        }

        if (Objects.equals(optionalBook.get(), book)) {
            log.info("The book is already registered. ISBN: {}", book.getIsbn());
            return RegistryResult.DUPLICATE;
        }

        boolean updateResult = updateEmptyFields(optionalBook.get(), book);
        if (updateResult) {
            bookRepository.save(optionalBook.get());
            return RegistryResult.UPDATE;
        }

        return RegistryResult.NO_UPDATE;
    }

    private boolean updateEmptyFields(Book fromDb, Book newOne) {
        boolean update = false;
        if (fromDb.getEditions() != null && newOne.getEditions() != null) {
            update = updateEdition(fromDb, newOne);
        }
        if (fromDb.getAuthor() == null && newOne.getAuthor() != null) {
            fromDb.setAuthor(newOne.getAuthor());
            update = true;
        }
        if (fromDb.getTitle() == null && newOne.getTitle() != null) {
            fromDb.setTitle(newOne.getTitle());
            update = true;
        }
        if (fromDb.getEditions() == null && newOne.getEditions() != null) {
            fromDb.setEditions(newOne.getEditions());
            update = true;
        }
        if (fromDb.getCoverType() == null && newOne.getCoverType() != null) {
            fromDb.setCoverType(newOne.getCoverType());
            update = true;
        }

        return update;
    }

    private boolean updateEdition(Book fromDb, Book newOne) {
        boolean update = false;
        Optional<Edition> first = newOne.getEditions().stream().findFirst();
        if (first.isPresent()) {
            Edition newOneEdition = first.get();
            int editionIndex = findEdition(fromDb.getEditions(),
                    newOneEdition.getEditionNumber(),
                    newOneEdition.getYearOfRelease());

            if (editionIndex > -1) {
                Optional<Edition> updatedEdition =
                        updateEditionEmptyField(fromDb.getEditions().get(editionIndex), newOneEdition);

                if (updatedEdition.isPresent()) {
                    fromDb.getEditions().set(editionIndex, updatedEdition.get());
                    update = true;
                }
            }

            if (editionIndex == -2) {
                fromDb.getEditions().add(newOneEdition);
                update = true;
            }
        }
        return update;
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

        return -2;
    }

    private Optional<Edition> updateEditionEmptyField(Edition fromDb, Edition newOne) {
        boolean update = false;
        if (fromDb.getYearOfRelease() == 0 && newOne.getYearOfRelease() != 0) {
            fromDb.setYearOfRelease(newOne.getYearOfRelease());
            update = true;
        }
        if (fromDb.getContributors() == null && newOne.getContributors() != null) {
            fromDb.setContributors(newOne.getContributors());
            update = true;
        }
        if (fromDb.getThickness() == 0.0 && newOne.getThickness() != 0.0) {
            fromDb.setThickness(newOne.getThickness());
            update = true;
        }
        if (fromDb.getPageNumber() == 0 && newOne.getPageNumber() != 0) {
            fromDb.setPageNumber(newOne.getPageNumber());
            update = true;
        }

        return update ? Optional.of(fromDb) : Optional.empty();
    }

    private boolean isValidEdition(Book newBook) {
        Edition edition = newBook.getEditions().get(NEW_BOOK_EDITION_INDEX);
        return edition.getYearOfRelease() != 0
                || edition.getEditionNumber() != 0;
    }
}
