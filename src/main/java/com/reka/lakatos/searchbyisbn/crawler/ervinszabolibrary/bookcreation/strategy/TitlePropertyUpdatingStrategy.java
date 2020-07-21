package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitlePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        book.setTitle(property.trim());
        setSpecialCoverType(property, book);
    }

    private void setSpecialCoverType(String value, Book book) {
        Pattern specialCoverTypePattern = Pattern.compile("(?![\\[])[^\\[\\.]*?(?=[\\]])");
        Matcher matcher = specialCoverTypePattern.matcher(value);
        if (matcher.find()) {
            String result = matcher.group();
            if (!result.isBlank()) {
                CoverType.findTypeByName(result.toLowerCase())
                        .ifPresent(book::setCoverType);
            }
        }
    }
}
