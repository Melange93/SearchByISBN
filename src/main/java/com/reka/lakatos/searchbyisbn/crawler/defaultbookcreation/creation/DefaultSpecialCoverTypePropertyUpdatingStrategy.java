package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultSpecialCoverTypePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String COVER_TYPE_BETWEEN_SQUARE_BRACKETS = "(?![\\[])[^\\[\\.]*?(?=[\\]])";

    @Override
    public void updateProperty(Book book, String property) {
        setSpecialCoverType(book, property);
    }

    private void setSpecialCoverType(Book book, String property) {
        Matcher matcher = Pattern.compile(COVER_TYPE_BETWEEN_SQUARE_BRACKETS).matcher(property);
        if (matcher.find()) {
            String result = matcher.group();
            if (!result.isBlank()) {
                CoverType.findTypeByName(result.toLowerCase())
                        .ifPresent(book::setCoverType);
            }
        }
    }
}
