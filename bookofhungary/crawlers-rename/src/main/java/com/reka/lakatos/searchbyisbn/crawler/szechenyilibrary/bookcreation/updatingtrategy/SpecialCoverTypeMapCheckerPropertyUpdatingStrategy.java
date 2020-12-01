package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SpecialCoverTypeMapCheckerPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final CoverType mapType = CoverType.MAP;

    @Override
    public void updateProperty(Book book, String property) {
        setCoverTypeMap(book, property);
    }

    private void setCoverTypeMap(Book book, String property) {
        String map = "térkép";
        if (book.getCoverType() == null && property.contains(map)) {
            book.setCoverType(mapType);
        }
    }
}
