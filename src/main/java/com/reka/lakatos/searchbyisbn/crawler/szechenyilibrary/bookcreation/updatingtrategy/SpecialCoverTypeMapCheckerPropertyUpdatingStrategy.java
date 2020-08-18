package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

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
