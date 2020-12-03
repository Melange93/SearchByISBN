package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.strategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultThicknessPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;
    private static final String THICKNESS_REGEX = "([0-9,]*[\\s]*cm)";
    private static final String CARTOGRAPHY_REGEX = "([x][0-9,]*[\\s]*cm)";

    @Override
    public void updateProperty(Book book, String property) {
        setThickness(property, book);
    }

    private void setThickness(String value, Book book) {
        if (Pattern.compile(CARTOGRAPHY_REGEX).matcher(value).find()) {
            return;
        }

        Pattern thickness = Pattern.compile(THICKNESS_REGEX);
        Matcher matcher = thickness.matcher(value);
        if (matcher.find()) {
            String coma = matcher.group().replaceAll(",", ".");
            String result = coma.replaceAll("[^0-9.]*", "");
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setThickness(Float.parseFloat(result));
        }
    }
}
