package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.Edition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultBookCreator {

    private final Map<PropertyUpdatingStrategy, String> bookPropertyUpdatingStrategyMap;
    private final Map<String, PropertyValidatorStrategy> propertyValidatorStrategyMap;

    public Optional<Book> createBook(Map<String, String> bookProperties) {
        Book book = Book.builder()
                .editions(
                        Collections.singletonList(new Edition()))
                .build();

        if (hasInvalidateBookProperties(bookProperties)) {
            return Optional.empty();
        }

        updateBookFields(bookProperties, book);

        if (book.getIsbn() == null) {
            return Optional.empty();
        }

        return Optional.of(book);
    }

    private boolean hasInvalidateBookProperties(Map<String, String> bookProperties) {
        for (String key : bookProperties.keySet()) {
            if (propertyValidatorStrategyMap.containsKey(key)) {
                if (!propertyValidatorStrategyMap.get(key).validateProperty(bookProperties.get(key))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateBookFields(Map<String, String> bookProperties, Book book) {
        bookPropertyUpdatingStrategyMap.entrySet().stream()
                .filter(propertyUpdatingEntry ->
                        bookProperties.containsKey(propertyUpdatingEntry.getValue()))
                .forEach(propertyUpdatingEntry ->
                        propertyUpdatingEntry
                                .getKey()
                                .updateProperty(
                                        book,
                                        bookProperties.get(propertyUpdatingEntry.getValue())
                                )
                );
    }
}
