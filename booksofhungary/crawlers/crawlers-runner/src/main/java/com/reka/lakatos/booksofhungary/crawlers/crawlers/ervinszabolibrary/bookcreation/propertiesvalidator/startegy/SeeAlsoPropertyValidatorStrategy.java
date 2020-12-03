package com.reka.lakatos.booksofhungary.crawlers.crawlers.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.validator.PropertyValidatorStrategy;

import java.util.regex.Pattern;

public class SeeAlsoPropertyValidatorStrategy implements PropertyValidatorStrategy {
    @Override
    public boolean validateProperty(String property) {
        if (property == null) {
            return true;
        }

        return hasNotPattern(property.trim(), "Részdokumentum") &&
                hasNotPattern(property.trim(), "Ezt tartalmazó dokumentum");
    }

    private boolean hasNotPattern(String property, String regex) {
        return !Pattern.compile(regex).matcher(property).find();
    }
}
