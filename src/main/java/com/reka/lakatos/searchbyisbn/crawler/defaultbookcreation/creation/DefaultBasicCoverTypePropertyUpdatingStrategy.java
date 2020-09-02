package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultBasicCoverTypePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String COVER_TYPE_REGEX_IN_ISBN_FIELD = "([^0-9\\s\\(\\)\\:\\-]*)";

    @Override
    public void updateProperty(Book book, String property) {
        setBasicCoverType(book, property);
    }

    private void setBasicCoverType(Book book, final String property) {
        if (book.getCoverType() != null) {
            return;
        }

        Matcher matcher = Pattern.compile(COVER_TYPE_REGEX_IN_ISBN_FIELD).matcher(property);
        while (matcher.find()) {
            if (!matcher.group().trim().isBlank()) {
                String result = matcher.group().trim();
                CoverType.findTypeByName(result)
                        .ifPresent(book::setCoverType);
            }
        }
    }
}
