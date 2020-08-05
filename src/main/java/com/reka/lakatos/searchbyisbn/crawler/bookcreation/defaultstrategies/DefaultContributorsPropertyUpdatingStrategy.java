package com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DefaultContributorsPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;
    private static final String SPECIAL_CHARACTER_REGEX = "\\$";

    @Override
    public void updateProperty(Book book, String property) {
        setContributors(property, book);
    }

    private void setContributors(String value, Book book) {
        String[] names = value.split(SPECIAL_CHARACTER_REGEX);
        Set<String> contributors = new HashSet<>(Arrays.asList(names));
        book.getEditions().get(INDEX_OF_BASIC_EDITION).setContributors(contributors);
    }
}
