package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;

public class DefaultNotValidPropertyValidatorStrategy implements PropertyValidatorStrategy {
    @Override
    public boolean validateProperty(String property) {
        return false;
    }
}
