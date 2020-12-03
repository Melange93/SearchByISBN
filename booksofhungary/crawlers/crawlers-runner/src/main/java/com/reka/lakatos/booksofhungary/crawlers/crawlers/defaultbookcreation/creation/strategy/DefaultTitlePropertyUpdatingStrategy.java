package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.strategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultTitlePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String TITLE_REGEX_NOT_CONTAINS_AUTHOR_NOTICE = "(.*?)(?=[\\/])";
    private static final String SPECIAL_COVER_TYPE_REGEX_BETWEEN_SQUARE_BRACKET = "\\[(.*?)\\]";

    @Override
    public void updateProperty(Book book, String property) {
        setTitle(book, property);
    }

    private void setTitle(Book book, String property) {
        Matcher matcher = Pattern.compile(TITLE_REGEX_NOT_CONTAINS_AUTHOR_NOTICE).matcher(property);
        if (matcher.find()) {
            String title = matcher.group()
                    .replaceAll(SPECIAL_COVER_TYPE_REGEX_BETWEEN_SQUARE_BRACKET, "")
                    .replaceAll("\\s+", " ")
                    .trim();
            book.setTitle(title);
        }
    }
}
