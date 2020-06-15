package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private BookRepository bookRepository;

    public Initializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Book book = Book.builder()
                .author("Robin Hobb")
                .title("Az arany Bolond")
                .subtitle("a borostyánférfi-ciklus második része")
                .authorNotice("Robin Hobb ; [ford. Gubó Luca]")
                .publisher("Delta Vision")
                .yearOfRelease("2019")
                .isbn("978-963-395-297-9")
                .build();

        bookRepository.save(book);

    }

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }
}
