package com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;

public class DefaultNotValidPropertyValidatorStrategy implements PropertyValidatorStrategy {
    @Override
    public boolean validateProperty(String property) {
        return false;
    }
}
