package controller;

import database.domain.Book;
import database.domain.CoverType;
import database.domain.Edition;
import domain.BookResponse;
import domain.CoverTypeResponse;
import domain.EditionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/searchbook")
    public BookResponse getBookByISBN(@RequestParam String isbn) {
        Optional<Book> bookByISBN = bookService.getBookByISBN(isbn);
        if (bookByISBN.isPresent()) {
            Book book = bookByISBN.get();
            List<Edition> editions = book.getEditions();
            List<EditionResponse> editionResponses = editions.stream().map(edition ->
                    EditionResponse.builder()
                            .contributors(edition.getContributors())
                            .editionNumber(edition.getEditionNumber())
                            .pageNumber(edition.getPageNumber())
                            .thickness(edition.getThickness())
                            .yearOfRelease(edition.getYearOfRelease())
                            .build()
            ).collect(Collectors.toList());

            CoverTypeResponse coverTypeResponse = CoverTypeResponse.valueOf(book.getCoverType().getHunName());


            return BookResponse.builder()
                    .isbn(book.getIsbn())
                    .author(book.getAuthor())
                    .coverTypeResponse(coverTypeResponse)
                    .editions(editionResponses)
                    .publisher(book.getPublisher())
                    .title(book.getTitle())
                    .build();
        }
        throw new RuntimeException("No such a book");
    }
}
