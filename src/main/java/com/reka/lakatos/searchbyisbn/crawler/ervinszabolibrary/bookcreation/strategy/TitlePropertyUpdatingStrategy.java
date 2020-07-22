package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitlePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        setTitle(book, property.trim());
        setSpecialCoverType(property, book);
    }

    private void setTitle(Book book, String property) {
        Matcher matcher = Pattern.compile("(.*?)\\/").matcher(property);
        if (matcher.find()) {
            book.setTitle(matcher.group().trim());
        }
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
