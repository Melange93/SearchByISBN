package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.bookcreation.validation;

import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Map;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class ExtendISBNPropertyValidatorStrategy implements PropertyValidatorStrategy {

    private final DefaultISBNPropertyValidatorStrategy defaultISBNValidator;
    private final Map<String, Boolean> propertyContainsValueResult;

    @Override
    public boolean validateProperty(String property) {
        if (property == null || property.isBlank()) {
            return false;
        }

        for (String key : propertyContainsValueResult.keySet()) {
            if (property.contains(key)) {
                return propertyContainsValueResult.get(key);
            }
        }

        return defaultISBNValidator.validateProperty(property);
    }
}
