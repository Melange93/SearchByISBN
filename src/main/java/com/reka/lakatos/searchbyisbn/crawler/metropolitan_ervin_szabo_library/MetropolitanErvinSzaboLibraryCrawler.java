package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
@Slf4j
public class MetropolitanErvinSzaboLibraryCrawler implements BookCrawler {

    @Autowired
    private BookCreator bookCreator;

    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";
    private static final String SPECIAL_CASE_OTHER_NAMES = "Egyéb nevek:";
    private String specialSeparationCharacter = "$";
    private String pageSize = "10";
    private int page = 0;
    private int isbnSeventhNumber = 0;
    private String searchingISBNMainGroup = ISBN963;

    @Override
    public List<Book> getNextBooks() throws IOException {
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

    private List<Book> getCrawledBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        Map<String, String> pageBooksInformation = getSearchingBookListPageBooksInformation(createBookListUrl(page, searchingISBNMainGroup + isbnSeventhNumber));
        for (String bookLineNumber : pageBooksInformation.keySet()) {
            String bookDetailsUrl = getBookDetailsUrl(bookLineNumber, pageBooksInformation.get(bookLineNumber));
            Book book = getBook(bookDetailsUrl);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }

    private String createBookListUrl(int pageNumber, String isbn) {
        return "http://saman.fszek.hu/WebPac/CorvinaWeb?pagesize="
                + pageSize
                + "&view=short&sort=0&page="
                + pageNumber
                + "&perpage="
                + pageSize
                + "&action=perpage&actualsearchset=FIND+ISBN+%22"
                + isbn
                + "%25%22&actualsort=0&language=&currentpage=result&text0=&index0=&whichform=simplesearchpage&showmenu=&resultview=short&recnum=&marcposition=&text0=&index0=&ccltext=&resultsize=";
    }

    private Map<String, String> getSearchingBookListPageBooksInformation(String url) throws IOException {
        Document bookListPage = Jsoup.connect(url).get();
        return getBookDetailsLinkInformation(bookListPage);
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

    private String getBookDetailsUrl(String bookLineNumber, String bookId) {
        return "http://saman.fszek.hu/WebPac/CorvinaWeb?action=onelong&showtype=longlong&recnum="
                + bookId
                + "&pos="
                + bookLineNumber;
    }

    private Book getBook(String bookDetailsUrl) throws IOException {
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

        return bookCreator.createBook(prepareBook, specialSeparationCharacter);
    }

    private String getContributorsStringWithSpecialSeparationCharacter(Elements names) {
        Optional<String> otherNames = names.stream().map(Element::text).reduce((s, s2) -> s + specialSeparationCharacter + s2);
        String otherNamesArraysToString = Arrays.toString(otherNames.stream().toArray());
        return otherNamesArraysToString.substring(1, otherNamesArraysToString.length() - 1);
    }
}
