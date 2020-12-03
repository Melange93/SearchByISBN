package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.strategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DefaultEditionNumberPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;
    private final String editionNumberRegex;

    @Override
    public void updateProperty(Book book, String property) {
        setEditionNumber(book, property);
    }

    private void setEditionNumber(Book book, String property) {
        Matcher matcher = Pattern.compile(editionNumberRegex).matcher(property);
        if (matcher.find()) {
            String editionNumber = matcher.group().trim();
            book.getEditions().get(INDEX_OF_BASIC_EDITION)
                    .setEditionNumber(Integer.parseInt(editionNumber));
        }
    }
}
