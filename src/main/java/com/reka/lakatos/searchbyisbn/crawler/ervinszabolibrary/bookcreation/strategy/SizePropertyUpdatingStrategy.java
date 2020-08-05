package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SizePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;

    @Override
    public void updateProperty(Book book, String property) {
        setThickness(property, book);
        setPageNumber(property, book);
    }

    private void setThickness(String value, Book book) {
        String thicknessRegex = "([0-9,]*[\\s]*cm)";
        String cartographyRegex = "([^x][0-9,]*[\\s]*cm)";

        if (Pattern.compile(cartographyRegex).matcher(value).find()) {
            return;
        }

        Pattern thickness = Pattern.compile(thicknessRegex);
        Matcher matcher = thickness.matcher(value);
        if (matcher.find()) {
            String coma = matcher.group().replaceAll(",", ".");
            String result = coma.replaceAll("[^0-9.]*", "");
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setThickness(Float.parseFloat(result));
        }
    }

    private void setPageNumber(String value, Book book) {
        Pattern pageNumberPattern = Pattern.compile("([0-9]*?(?=\\s*?p\\.))|([0-9]*?(?=[\\[\\s,]*?[0-9][\\]\\s]*?\\s*?p\\.))");
        Matcher matcher = pageNumberPattern.matcher(value);
        int pageCounter = 0;
        while (matcher.find()) {
            if (!matcher.group().isBlank()) {
                pageCounter += Integer.parseInt(matcher.group());
            }
        }
        book.getEditions().get(INDEX_OF_BASIC_EDITION).setPageNumber(pageCounter);
    }
}
