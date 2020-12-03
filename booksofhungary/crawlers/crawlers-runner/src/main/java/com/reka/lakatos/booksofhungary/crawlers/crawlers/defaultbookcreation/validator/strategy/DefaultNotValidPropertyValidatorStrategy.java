package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.validator.strategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.validator.PropertyValidatorStrategy;

public class DefaultNotValidPropertyValidatorStrategy implements PropertyValidatorStrategy {
    @Override
    public boolean validateProperty(String property) {
        return false;
    }
}
