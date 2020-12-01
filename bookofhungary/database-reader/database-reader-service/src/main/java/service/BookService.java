package service;

import database.domain.Book;
import database.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Optional<Book> getBookByISBN(String isbn) {
        return bookRepository.findById(isbn);
    }
}
