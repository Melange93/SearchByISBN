package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublisherPropertiesUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;

    @Override
    public void updateProperty(Book book, String property) {
        setPublisher(property, book);
        setDate(book, property);
        setSpecialCoverTypeMusicSheet(book, property);
    }

    private void setSpecialCoverTypeMusicSheet(Book book, String property) {
        if (book.getCoverType() != null) {
            return;
        }

        Pattern musicSheet = Pattern.compile("kotta");
        Matcher matcher = musicSheet.matcher(property);
        if (matcher.find()) {
            book.setCoverType(CoverType.SHEET_MUSIC);
        }
    }

    private void setPublisher(String value, Book book) {
        Pattern publisherPattern = Pattern.compile("[^:]*?(?=[,])");
        Matcher matcher = publisherPattern.matcher(value);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
        }
    }

    private void setDate(Book book, String property) {
        String dateRegex = "[\\d]{4}";
        Matcher matcher = Pattern.compile(dateRegex).matcher(property);
        if (isValidDate(matcher) && matcher.find()) {
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setYearOfRelease(Integer.parseInt(matcher.group()));
        }
    }

    private boolean isValidDate(Matcher matcher) {
        int counter = (int) matcher.results().count();
        matcher.reset();
        return counter == 1;
    }
}
