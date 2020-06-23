package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErvinSzaboLibraryCrawler implements BookCrawler {

    private final ErvinSzaboLibraryBookCreator bookCreator;

    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";
    private static final String SPECIAL_CASE_OTHER_NAMES = "Egyéb nevek:";
    private static final String SPECIAL_SEPARATION_CHARACTER = "$";
    private static final String PAGE_SIZE = "10";

    private int page = 0;
    private int isbnSeventhNumber = 0;
    private String searchingISBNMainGroup = ISBN963;

    @Override
    public List<Book> getNextBooks() {
        log.info("Start crawling: " + getClass().getSimpleName());
        List<Book> books = getCrawledBooks();
        page++;

        if (books.size() == 0 && isbnSeventhNumber <= 9) {
            page = 0;
            isbnSeventhNumber++;
        }

        if (books.size() == 0 && isbnSeventhNumber > 9 && searchingISBNMainGroup.equals(ISBN963)) {
            page = 0;
            isbnSeventhNumber = 0;
            searchingISBNMainGroup = ISBN615;
        }
        return books;
    }

    private List<Book> getCrawledBooks() {
        final Map<String, String> pageBooksInformation = getSearchingBookListPageBooksInformation(
                createBookListUrl(page, searchingISBNMainGroup + isbnSeventhNumber));

        return pageBooksInformation.entrySet().stream()
                .map(bookInformationEntry -> createBookDetailsUrl(bookInformationEntry.getKey(),
                        bookInformationEntry.getValue()))
                .map(this::visitBookDetailsUrl)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Optional<Book> visitBookDetailsUrl(final String bookDetailsUrl) {
        try {
            return getBook(bookDetailsUrl);
        } catch (Exception e) {
            log.error("Exception happened while crawling book location: " + bookDetailsUrl, e);

            return Optional.empty();
        }
    }

    private String createBookListUrl(int pageNumber, String isbn) {
        return "http://saman.fszek.hu/WebPac/CorvinaWeb?pagesize="
                + PAGE_SIZE
                + "&view=short&sort=0&page="
                + pageNumber
                + "&perpage="
                + PAGE_SIZE
                + "&action=perpage&actualsearchset=FIND+ISBN+%22"
                + isbn
                + "%25%22&actualsort=0&language=&currentpage=result&text0=&index0=&whichform=simplesearchpage&showmenu=&resultview=short&recnum=&marcposition=&text0=&index0=&ccltext=&resultsize=";
    }

    private Map<String, String> getSearchingBookListPageBooksInformation(String url) {
        try {
            final Document bookListPage = Jsoup.connect(url).get();

            return getBookDetailsLinkInformation(bookListPage);
        } catch (IOException e) {
            //TODO: Use a named exception instead of a naked runtime one
            throw new RuntimeException("Unable to download the list book page! Url: " + url, e);
        }
    }

    private Map<String, String> getBookDetailsLinkInformation(Document document) {
        Map<String, String> booksInformation = new HashMap<>();

        Elements informationLines = document.select(".short_item_back script");
        for (Element line : informationLines) {
            Pattern lineNumberAndBookId = Pattern.compile("(\\d+,\\d+)");
            Matcher matcher = lineNumberAndBookId.matcher(line.toString());
            if (matcher.find()) {
                String[] information = matcher.group().split(",");
                int lineNumber = 0;
                int bookId = 1;
                booksInformation.put(information[lineNumber], information[bookId]);
            }
        }
        return booksInformation;
    }

    // TODO: Move to factory
    private String createBookDetailsUrl(String bookLineNumber, String bookId) {
        return "http://saman.fszek.hu/WebPac/CorvinaWeb?action=onelong&showtype=longlong&recnum="
                + bookId
                + "&pos="
                + bookLineNumber;
    }

    private Optional<Book> getBook(String bookDetailsUrl) {
        try {
            Document bookPage = Jsoup.connect(bookDetailsUrl).get();
            Elements bookProperties = bookPage.select(".long_key");
            Elements bookPropertiesValues = bookPage.select(".long_value");

            Map<String, String> prepareBook = new HashMap<>();
            for (int i = 0; i < bookProperties.size() - 1; i++) {
                if (bookProperties.get(i).text().equals(SPECIAL_CASE_OTHER_NAMES) && !bookPropertiesValues.get(i).select("a").isEmpty()) {
                    Elements names = bookPropertiesValues.get(i).select("a");
                    String contributorsName = getContributorsStringWithSpecialSeparationCharacter(names);
                    prepareBook.put(bookProperties.get(i).text().trim(), contributorsName);
                    break;
                }
                prepareBook.put(bookProperties.get(i).text().trim(), bookPropertiesValues.get(i).text().trim());
            }

            return bookCreator.createBook(prepareBook, SPECIAL_SEPARATION_CHARACTER);
        } catch (IOException e) {
            //TODO: Use a named exception instead of a naked runtime one
            throw new RuntimeException("Unable to download book page! Url: " + bookDetailsUrl, e);
        }
    }

    private String getContributorsStringWithSpecialSeparationCharacter(Elements names) {
        Optional<String> otherNames = names.stream().map(Element::text).reduce((s, s2) -> s + SPECIAL_SEPARATION_CHARACTER + s2);
        String otherNamesArraysToString = Arrays.toString(otherNames.stream().toArray());
        return otherNamesArraysToString.substring(1, otherNamesArraysToString.length() - 1);
    }
}
