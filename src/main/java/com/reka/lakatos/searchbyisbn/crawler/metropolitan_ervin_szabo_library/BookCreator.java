package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BookCreator {

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-X]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-X]{13}))";

    public Optional<Book> createBook(Map<String, String> prepareBook, String specialSeparationCharacter) {
        Book book = new Book();

        if (prepareBook.get("Megjegyzések:") != null && prepareBook.get("Megjegyzések:").matches(".*?" + ISBN13_REGEX + ".*" + "|" + ".*?" + ISBN10_REGEX + ".*")) {
            return Optional.empty();
        }

        if (prepareBook.get("ISBN:") == null) {
            return Optional.empty();
        }

        for (String key : prepareBook.keySet()) {
            switch (key) {
                case "Cím:":
                    book.setTitle(prepareBook.get(key).trim());
                    break;
                case "Szerző:":
                    book.setAuthor(prepareBook.get(key).trim());
                    break;
                case "ISBN:":
                    setISBN(prepareBook.get(key), book);
                    setBasicCoverType(prepareBook.get(key), book);
                    break;
                case "Megjelenés:":
                    setPublisher(prepareBook.get(key), book);
                    break;
                case "Dátum:":
                    book.setYearOfRelease(prepareBook.get(key).trim());
                    break;
                case "Terjedelem:":
                    setThickness(prepareBook.get(key), book);
                    setPageNumber(prepareBook.get(key), book);
                    break;
                case "Egyéb nevek:":
                    setContributors(prepareBook.get(key), book, specialSeparationCharacter);
                    break;
            }
        }

        return Optional.of(book);
    }

    private void setISBN(String preISBN, Book book) {
        Pattern isbn13Pattern = Pattern.compile(ISBN13_REGEX);
        Matcher isbn13Matcher = isbn13Pattern.matcher(preISBN);
        if (isbn13Matcher.find()) {
            book.setIsbn(isbn13Matcher.group());
            return;
        }

        Pattern isbn10Pattern = Pattern.compile(ISBN10_REGEX);
        Matcher isbn10Matcher = isbn10Pattern.matcher(preISBN);
        if (isbn10Matcher.find()) {
            book.setIsbn(isbn10Matcher.group());
        }
    }

    private void setThickness(String value, Book book) {
        Pattern thickness = Pattern.compile("([0-9,]*[\\s]*cm)");
        Matcher matcher = thickness.matcher(value);
        if (matcher.find()) {
            String result = matcher.group().replaceAll("[^0-9]*", "");
            book.setThickness(Float.parseFloat(result));
        }
    }

    private void setBasicCoverType(String value, Book book) {
        Pattern coverTypePattern = Pattern.compile("([^0-9\\s\\(\\)\\:\\-]*)");
        Matcher matcher = coverTypePattern.matcher(value);
        while (matcher.find()) {
            if (!matcher.group().trim().isBlank()) {
                String result = matcher.group().trim();
                book.setCoverType(CoverType.findTypeByName(result));
            }
        }
    }

    private void setContributors(String value, Book book, String specialSeparationCharacter) {
        String[] names = value.split(specialSeparationCharacter);
        Set<String> contributors = new HashSet<>(Arrays.asList(names));
        book.setContributors(contributors);
    }

    private void setPublisher(String value, Book book) {
        Pattern publisherPattern = Pattern.compile("(?<=[:])[^:]*?(?=[,])");
        Matcher matcher = publisherPattern.matcher(value);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
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
        book.setPageNumber(pageCounter);
    }
}
