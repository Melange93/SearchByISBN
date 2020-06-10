package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.PhysicalCharacteristics;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Initializer implements CommandLineRunner {

    private BookRepository bookRepository;

    public Initializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book simpleBook = new Book(
                "Test Test",
                "The test book",
                "Something",
                "Working Db",
                "RVL CODING",
                "2020",
                "000000000000",
                new ArrayList<>(),
                new PhysicalCharacteristics());

        bookRepository.save(simpleBook);
    }
}
