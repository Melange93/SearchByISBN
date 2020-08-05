package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

public class SpecialCoverTypeDigitalCheckerPropertyUpdatingStrategy implements PropertyUpdatingStrategy {
    @Override
    public void updateProperty(Book book, String property) {
        setCoverTypeDigital(book, property);
    }

    private void setCoverTypeDigital(Book book, String property) {
        if (book.getCoverType() == null) {
            book.setCoverType(CoverType.DIGITAL);
        }
    }
}
