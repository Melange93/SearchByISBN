package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.document.PhysicalCharacteristics;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class Initializer implements CommandLineRunner {

    private BookRepository bookRepository;

    public Initializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book chilrdern = Book.builder()
                .title("The children").build();
        bookRepository.save(chilrdern);

        Book simpleBook = Book.builder()
                .author("Test Test")
                .title("The test book")
                .subtitle("Something")
                .authorNotice("Working Db")
                .publisher("RVL CODING")
                .yearOfRelease("cop. 2020")
                .isbn("000000000000")
                .contributors(new HashSet<>(Arrays.asList("Me", "Me", "Robin Hobb")))
                .booksIdsWhatPartThisBook(new HashSet<>(Arrays.asList(chilrdern.getId())))
                .physicalCharacteristics(PhysicalCharacteristics.builder().coverType(CoverType.HARDCORE).build())
                .build();

        bookRepository.save(simpleBook);
        chilrdern.setBooksIdsWhatContainThisBook(new HashSet<>(Arrays.asList(simpleBook.getId())));
        bookRepository.save(chilrdern);

        System.out.println(bookRepository.findAllById(chilrdern.getBooksIdsWhatContainThisBook()));

    }
}
