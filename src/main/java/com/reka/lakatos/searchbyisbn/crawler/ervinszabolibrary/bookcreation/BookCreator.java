package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCreator {

    private final Map<String, PropertyUpdatingStrategy> bookPropertyUpdatingStrategyMap;
    private final Map<String, PropertyValidatorStrategy> propertyValidatorStrategyMap;

    public Optional<Book> createBook(Map<String, String> prepareBook) {
        Book book = new Book();

        for (String key : prepareBook.keySet()) {
            if (propertyValidatorStrategyMap.containsKey(key)) {
                boolean result = propertyValidatorStrategyMap.get(key).validateProperty(prepareBook.get(key));
                if (!result) {
                    return Optional.empty();
                }
            }
        }

        for (String key : prepareBook.keySet()) {
            if (bookPropertyUpdatingStrategyMap.containsKey(key)) {
                bookPropertyUpdatingStrategyMap.get(key).updateProperty(book, prepareBook.get(key));
            }
        }

        return Optional.of(book);
    }
}
