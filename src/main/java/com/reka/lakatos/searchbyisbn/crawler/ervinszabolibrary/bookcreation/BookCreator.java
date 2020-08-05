package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.Edition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCreator {

    private final Map<String, PropertyUpdatingStrategy> bookPropertyUpdatingStrategyMap;
    private final Map<String, PropertyValidatorStrategy> propertyValidatorStrategyMap;

    public Optional<Book> createBook(Map<String, String> bookProperties) {
        Book book = Book.builder()
                .editions(
                        Collections.singletonList(new Edition()))
                .build();

        for (String key : bookProperties.keySet()) {
            if (propertyValidatorStrategyMap.containsKey(key)) {
                if (!propertyValidatorStrategyMap.get(key).validateProperty(bookProperties.get(key))) {
                    return Optional.empty();
                }
            }
        }

        for (String key : bookProperties.keySet()) {
            if (bookPropertyUpdatingStrategyMap.containsKey(key)) {
                bookPropertyUpdatingStrategyMap.get(key).updateProperty(book, bookProperties.get(key));
            }
        }

        return Optional.of(book);
    }
}
