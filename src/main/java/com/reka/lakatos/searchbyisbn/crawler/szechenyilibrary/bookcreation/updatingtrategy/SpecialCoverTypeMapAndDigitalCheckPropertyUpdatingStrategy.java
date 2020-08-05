package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SpecialCoverTypeMapAndDigitalCheckPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private final Map<String, CoverType> coverTypeConverter;

    @Override
    public void updateProperty(Book book, String property) {
        setSpecialCoveType(book, property);
    }

    private void setSpecialCoveType(Book book, String property) {
        if (book.getCoverType() != null) {
            return;
        }

        for (String coverType : coverTypeConverter.keySet()) {
            if (property.contains(coverType)) {
                book.setCoverType(coverTypeConverter.get(coverType));
                return;
            }
        }
    }
}
