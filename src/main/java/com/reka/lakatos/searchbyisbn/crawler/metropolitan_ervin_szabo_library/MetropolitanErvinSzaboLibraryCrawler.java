package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConditionalOnProperty(name = "ervinszabo")
@Component
public class MetropolitanErvinSzaboLibraryCrawler implements BookCrawler {
    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";

    private BookCreator bookCreator = new BookCreator();

    private final String pageSize = "50";
    private int page = 0;
    private int isbnSeventhNumber = 0;
    private String searchingISBNMainGroup = ISBN963;


    @Override
    public List<Book> getNextBooks() throws IOException {
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
        Map<String, String> pageBooksInformation = getPageBooksInformation(createBookListUrl(page, searchingISBNMainGroup + isbnSeventhNumber));
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

    private Map<String, String> getPageBooksInformation(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return getBookDetailsLinkInformation(document);
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
        Document document1 = Jsoup.connect(bookDetailsUrl).get();
        Elements bookProperties = document1.select(".long_key");
        Elements bookPropertiesValues = document1.select(".long_value");

        Map<String, String> prepareBook = new HashMap<>();
        for (int i = 0; i < bookProperties.size() - 1; i++) {
            prepareBook.put(bookProperties.get(i).text().trim(), bookPropertiesValues.get(i).text().trim());
        }

        return bookCreator.createBook(prepareBook);
    }
}
