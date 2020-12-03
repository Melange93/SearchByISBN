package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
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
