package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy;

public class SeeAlsoPropertyValidatorStrategy implements PropertyValidatorStrategy {
    @Override
    public boolean validateProperty(String property) {
        return property == null ||
                !property.trim().contains("Részdokumentum") ||
                !property.trim().contains("Ezt tartalmazó dokumentum");
    }
}
