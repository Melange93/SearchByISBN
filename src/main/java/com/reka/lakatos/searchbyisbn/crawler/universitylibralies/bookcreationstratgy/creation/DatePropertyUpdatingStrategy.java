package com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class DatePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int DEFAULT_EDITION_INDEX = 0;

    @Override
    public void updateProperty(Book book, String property) {
        setDate(book, property);
    }

    private void setDate(Book book, String property) {
        String dateRegex= "[\\d]{4}";
        Matcher matcher = Pattern.compile(dateRegex).matcher(property);
        if (matcher.find()) {
            book.getEditions().get(DEFAULT_EDITION_INDEX).setYearOfRelease(Integer.parseInt(matcher.group()));
        }
    }
}
