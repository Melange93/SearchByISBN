package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.validation;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class ISBNPropertyValidationStrategy implements PropertyValidatorStrategy {

    private static final String SUMMERY_ISBN_MARK = "ö";
    private static final String ISMN = "ISMN";
    private final PropertyValidatorStrategy ISBNValidatorStrategy;

    @Override
    public boolean validateProperty(String property) {
        if (property.contains(SUMMERY_ISBN_MARK) || property.contains(ISMN)) {
            return false;
        }
        return ISBNValidatorStrategy.validateProperty(property);
    }
}
