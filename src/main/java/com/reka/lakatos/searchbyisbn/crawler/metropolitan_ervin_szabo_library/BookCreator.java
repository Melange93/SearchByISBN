package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BookCreator {

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-X]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-X]{13}))";

    public Book createBook(Map<String, String> prepareBook) {
        Book book = new Book();

        if (prepareBook.get("Megjegyzések:") != null && prepareBook.get("Megjegyzések:").matches(".*?" + ISBN13_REGEX + ".*" + "|" + ".*?" + ISBN10_REGEX + ".*")) {
            return null;
        }

        if (prepareBook.get("ISBN:") == null) {
            return null;
        }

        for (String key : prepareBook.keySet()) {
            switch (key) {
                case "Cím:":
                    getTitle(prepareBook.get("Cím:"), book);
                    getSubTitle(prepareBook.get("Cím:"), book);
                    getAuthorNotice(prepareBook.get("Cím:"), book);
                    break;
                case "Szerző:":
                    book.setAuthor(prepareBook.get("Szerző:").trim());
                    break;
                case "ISBN:":
                    getISBN(prepareBook.get("ISBN:"), book);
                    break;

            }
        }
        return book;
    }

    private void getTitle(String preTitle, Book book) {
        Pattern haveSubTitle = Pattern.compile("(.*?[:])");
        Matcher titleMatcher = haveSubTitle.matcher(preTitle);
        if (titleMatcher.find()) {
            book.setTitle(titleMatcher.group(1).replace(":", "").trim());
            return;
        }

        Pattern noSubTitle = Pattern.compile("(.*?[/])");
        Matcher titleMatcher2 = noSubTitle.matcher(preTitle);
        if (titleMatcher2.find()) {
            book.setTitle(titleMatcher2.group(1).replace("/", "").trim());
            return;
        }

        book.setTitle(preTitle);
    }

    private void getSubTitle(String preTitle, Book book) {
        Pattern subTitlePattern = Pattern.compile("([:].*?[/])");
        Matcher subTitleMatcher = subTitlePattern.matcher(preTitle);
        if (subTitleMatcher.find()) {
            String subTitle = subTitleMatcher
                    .group()
                    .replaceFirst(":", "")
                    .replace("/", "")
                    .trim();
            book.setSubtitle(subTitle);
        }
    }

    private void getAuthorNotice(String preTitle, Book book) {
        Pattern authorNoticePattern = Pattern.compile("([/].*)");
        Matcher authorNoticeMatcher = authorNoticePattern.matcher(preTitle);
        if (authorNoticeMatcher.find()) {
            String authorNotice = authorNoticeMatcher.group().replaceFirst("/", "").trim();
            if (!authorNotice.isBlank()) {
                book.setAuthorNotice(authorNotice);
            }
        }
    }

    private void getISBN(String preISBN, Book book) {
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

}
