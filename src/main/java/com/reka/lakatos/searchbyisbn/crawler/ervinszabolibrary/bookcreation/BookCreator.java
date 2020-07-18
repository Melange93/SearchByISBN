package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.BookPropertiesValidator;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy.BookPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCreator {

    private final BookPropertiesValidator bookPropertiesValidator;
    private final Map<String, BookPropertyUpdatingStrategy> bookPropertyUpdatingStrategyMap;

    public Optional<Book> createBook(Map<String, String> prepareBook) {
        Book book = new Book();

        if (!bookPropertiesValidator.isValidBookProperties(
                prepareBook.get("Megjegyzések:"),
                prepareBook.get("ISBN:"),
                prepareBook.get("Lásd még:"))) {
            return Optional.empty();
        }

        for (String key : prepareBook.keySet()) {
            if (bookPropertyUpdatingStrategyMap.containsKey(key)) {
                bookPropertyUpdatingStrategyMap.get(key).updateProperty(book, prepareBook.get(key));
            }
        }

        return Optional.of(book);
    }

}
